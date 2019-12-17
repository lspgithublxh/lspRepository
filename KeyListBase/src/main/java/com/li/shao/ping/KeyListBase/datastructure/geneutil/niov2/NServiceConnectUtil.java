package com.li.shao.ping.KeyListBase.datastructure.geneutil.niov2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * 单次通信
 *
 * @author lishaoping
 * @date 2019年12月17日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil.niov2
 */
@Slf4j
public class NServiceConnectUtil {

	public void connect() {
		try {
			AtomicInteger sendCount = new AtomicInteger(0);
			SocketChannel socket = SocketChannel.open();
			socket.configureBlocking(false);
			Selector selector = Selector.open();
			socket.register(selector, SelectionKey.OP_CONNECT);
			socket.connect(new InetSocketAddress("127.0.0.1", 8080));
				while(true) {
					int count = selector.select();//客户端会一直轮询，返回；
					if(count > 0) {
						log.info("select get:" + count);//selectedKeys()必须正确-
						Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
						while(iterator.hasNext()) {
							SelectionKey key = iterator.next();
							iterator.remove();
							if(key.isConnectable()) {
								SocketChannel channel = (SocketChannel) key.channel();
								if(channel.isConnectionPending()) {
									channel.finishConnect();
								}
								channel.configureBlocking(false);//是否必须？
								channel.register(selector, SelectionKey.OP_READ);
								ByteBuffer data = ByteBuffer.wrap("hello,server".getBytes());
//								data.flip();//无需
								log.info("send data:");
								while(data.hasRemaining()) {
									channel.write(data);
								}
							}else if(key.isReadable()) {
								SocketChannel channel = (SocketChannel) key.channel();
//								channel.configureBlocking(false);
//								channel.register(selector, SelectionKey.OP_WRITE);//
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								int len = channel.read(buffer);
								byte[] data = new byte[1024];
								while(len > 0) {
									buffer.flip();
									buffer.get(data, 0, len);
									log.info("read:" + new String(data));
									buffer.compact();
									len = channel.read(buffer);
								}
								//下面加了则无限通信;不注册写则isWriable不能用::想写就写模式最好
//								ByteBuffer buf = ByteBuffer.wrap("hello,server".getBytes());
//								log.info("write data:");
//								while(buf.hasRemaining()) {
//									channel.write(buf);
//								}
							}else if(key.isWritable()) {
//								if(sendCount.incrementAndGet() > 2) {
//									continue;
//								}
								SocketChannel channel = (SocketChannel) key.channel();
								channel.configureBlocking(false);
								channel.register(selector, SelectionKey.OP_READ);
								ByteBuffer data = ByteBuffer.wrap("hello,server".getBytes());
//								data.flip();//无需
								log.info("write data:");
								while(data.hasRemaining()) {
									channel.write(data);
								}
							}
							
						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		NServiceConnectUtil util = new NServiceConnectUtil();
		util.connect();
	}
}
