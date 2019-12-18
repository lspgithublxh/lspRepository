package com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author lishaoping
 * @date 2019年12月18日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3
 */
@Slf4j
public class ServerSelector {

	private NServiceServerPoolUtil util;
	
	public ServerSelector(NServiceServerPoolUtil util) {
		this.util = util;
	}

	public void startSelector() {
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress("127.0.0.1", 10023));
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);//同时可以传递附加对象attach
			AtomicInteger linkCount = new AtomicInteger(0);
			log.info("start selector");
			while (true) {
				int count = selector.select();//至少一个通道selected才返回
				log.info("select get:" + count);
				if (count > 0) {
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = keys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();//不再监听
						if(key.isAcceptable()) {
							SelectableChannel serverChannel = key.channel();
							ServerSocketChannel sc = (ServerSocketChannel) serverChannel;
							SocketChannel clientN = sc.accept();
							clientN.configureBlocking(false);
							clientN.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));//附加一个对象
							util.channelNameMap.put(clientN, new String[] {"server_channel_r" + linkCount.incrementAndGet(),
									"server_channel_w" + linkCount.incrementAndGet()});
							//启动监听
							util.acceptSocketChannel(clientN);
						}else if(key.isReadable()) {//必然只是socketChannel
							SocketChannel clientN = (SocketChannel) key.channel();
							//触发可以读了
							String[] names = util.channelNameMap.get(clientN);
							String name = names[0];
							synchronized (name.intern()) {//激活
								name.intern().notify();
							}
						}
						
					}
				}

			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
