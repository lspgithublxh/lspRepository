package com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3.NServcieConnectPoolUtil.Worker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientSelector {

	private Selector selector;
	public Map<SocketChannel, Boolean> linkMap;
	public NServcieConnectPoolUtil pool;
	
	public ClientSelector(NServcieConnectPoolUtil pool) {
		try {
			this.selector = Selector.open();
			linkMap = Maps.newConcurrentMap();
			this.pool = pool;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void register(SocketChannel channel) {
		try {
			channel.register(selector, SelectionKey.OP_CONNECT);
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		}
	}
	
	public void selector() {
		try {
			while (true) {
				int count = selector.select();
				if (count > 0) {
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
							//激发发送数据--或者什么也不先做；只是连接确定
							linkMap.put(channel, true);
							Worker worker = pool.socketWorkerMap.get(channel);
							synchronized (worker.getName().intern()) {
								worker.getName().notify();
							}
						}else if(key.isReadable()) {
							SocketChannel channel = (SocketChannel) key.channel();
							Worker worker = pool.socketWorkerMap.get(channel);
							synchronized (worker.getName().intern()) {
								worker.getName().notify();
							}//激发开始读数据
						}
						
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
