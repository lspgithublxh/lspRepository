package com.li.shao.ping.KeyListBase.datastructure.geneutil.niov4;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author lishaoping
 * @date 2019年12月18日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil.niov3
 */
@Slf4j
public class NServiceServerPoolUtil4 {

	private Map<String, DataEntry> receivedMap;//每个访问连接，以及收到的数据
	private ServerSelector4 serverSelector;
	public Map<SocketChannel, String[]> channelNameMap;
	public AtomicInteger countReceived = new AtomicInteger(0);
	public AtomicInteger countSend = new AtomicInteger(0);
	
	private SimpleThreadPoolUtil tpool = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	private SimpleThreadPoolUtil tpool2 = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	
	public NServiceServerPoolUtil4() {
		this.serverSelector = new ServerSelector4(this);
		this.receivedMap = Maps.newConcurrentMap();
		this.channelNameMap = Maps.newConcurrentMap();
	}
	
	public void start() {
		this.serverSelector.startSelector();
	}
	
	public void acceptSocketChannel(SocketChannel channel) {//等待收到连接
		//开始读
		tpool.addTask(()->{
			readFromChannel(channel);
		});
		//监听读到而回写
		tpool2.addTask(()->{
			writeToChannel(channel);
		});
	}
	
	private void writeToChannel(SocketChannel channel) {
		try {
			String[] names = channelNameMap.get(channel);
			String writePointName = names[1];
			while(true) {
				synchronized (writePointName.intern()) {//
					if(!receivedMap.containsKey(writePointName) || 
							receivedMap.get(writePointName).dataMap.isEmpty()) {
						writePointName.intern().wait();
					}
					DataEntry dataEntry = receivedMap.get(writePointName);
					//批量写回数据--对收到的所有客户端返回
					log.info("receivedMapSize:" + receivedMap.size() + dataEntry.dataMap.size());
					Set<String> keySet = dataEntry.dataMap.keySet();
					for(String key : keySet) {
						byte[] received = dataEntry.dataMap.get(key);
						String user = key;
						//开始返回调用数据
						countSend.incrementAndGet();
						log.info("received data:" + new String(received) + ",user:" + user);
						byte[] data = "hello,client".getBytes();
						byte[] syn = user.getBytes();
						int blockLen = data.length / 1024 + (data.length % 1024 > 0 ? 1 : 0);
						byte[] header = new byte[1024];
						header[0] = (byte) (blockLen >> 24);
						header[1] = (byte) (blockLen >> 16);
						header[2] = (byte) (blockLen >> 8);
						header[3] = (byte) (blockLen >> 0);
						//增加64字节user
						int index = 4;
						for (byte s : syn) {
							header[index++] = s;
							if (index > 67) {//截取
								break;
							}
						}
						justSend(channel, header, 0, header.length);
						//发送正式数据
						//发送有效数据
						int startPos = 0;
						int endPos = 1024 > data.length ? data.length : 1024;
						byte[] buffer = new byte[1024];
						int j = startPos;
						for (int i = 0; i < 1024; j++, i++) {
							if (j >= endPos) {
								//临时数据也开始刷
								justSend(channel, buffer, 0, i);
								break;
							}
							buffer[i] = data[j];
							if (i == 1023) {
								i = -1;
								//开始刷数据,完整
								justSend(channel, buffer, 0, buffer.length);
							}
						}
					}
//					receivedMap.keySet().stream().forEach(key ->{
//						
//					});
//					receivedMap.forEach((user, received) -> {
//						
//					});
					dataEntry.dataMap.clear();
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void justSend(SocketChannel channel, byte[] header, int offset, int len) {
		
		ByteBuffer buf = ByteBuffer.wrap(header, offset, len);//"hello,server".getBytes()
		try {
			while (buf.hasRemaining()) {
				channel.write(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Data
	@Accessors(chain = true)
	class DataEntry{
		Map<String, byte[]> dataMap = Maps.newConcurrentMap();
	}
	
	private void readFromChannel(SocketChannel channel) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		String[] names = channelNameMap.get(channel);
		String name = names[0];
		String writePointName = names[1];
		try {
			synchronized (name.intern()) {//等待selector激活
				Boolean has = serverSelector.userReadableMap.get(name);
				if(has == null || !has) {
					name.intern().wait();
				}
				serverSelector.userReadableMap.remove(name);
			}
			byte[] cache = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean first = true;
			int num = 0;
			String user = "";
			while(true) {
				int count = 1;
				while(count > 0) {
					count = channel.read(buffer);
					if(count == 0) {
						break;
					}
					buffer.flip();
					buffer.get(cache, 0, count);
					if(first) {//第一次
						num = ((cache[0] & 0xff) << 24) + ((cache[1] & 0xff) << 16) + ((cache[2] & 0xff) << 8) + cache[3];
						if(num > 0) {
							first = false;
						}
						//计算响应谁的64个字节
						user = new String(cache, 4, 68);
						buffer.clear();
						continue;
					}
					out.write(cache, 0, count);
					if(--num == 0) {//读取完毕，放到用户区域
						log.info("received: " + user.trim());
						countReceived.incrementAndGet();
						first = true;
						synchronized (writePointName.intern()) {//激发调用方返回
							DataEntry dataEntry = receivedMap.get(writePointName);
							if(dataEntry == null) {
								dataEntry = new DataEntry();
								receivedMap.put(writePointName, dataEntry);
							}
							dataEntry.dataMap.put(user.trim(), out.toByteArray());
							writePointName.intern().notifyAll();
							out.reset();
						}
						buffer.clear();
//						break;//
					}
					
				}
				synchronized (name.intern()) {//等待下次selector激活
					Boolean has = serverSelector.userReadableMap.get(name);
					if(has == null || !has) {
						name.intern().wait();
					}
					serverSelector.userReadableMap.remove(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			NServiceServerPoolUtil4 util = new NServiceServerPoolUtil4();
			new Thread(()->{
				try {
					Thread.sleep(40000);
					System.out.println("received:" + util.countReceived.get());
					System.out.println("send" + util.countSend.get());
					System.out.println("readable-count:" + util.serverSelector.countReadable.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start() ;
			util.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
