package com.li.shao.ping.KeyListBase.datastructure.geneutil;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * server
 *
 * @author lishaoping
 * @date 2019年12月12日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil
 */
public class ServiceServerUtil {

	private Map<String, byte[]> receivedMap;
	private Map<InputStream, List<String>> inputStreamMap;
	
	public void startServer() {
		//
		try {
			receivedMap = Maps.newHashMap();
			inputStreamMap = Maps.newHashMap();
			ServerSocket server = new ServerSocket(12345);
			while(true) {
				System.out.println("wait at 12345");
				Socket socket = server.accept();
				System.out.println("accept ");
				SimpleThreadPoolUtil.pool.addTask(()->{
					try {
						InputStream in = socket.getInputStream();
						OutputStream out = socket.getOutputStream();
						DataInputStream input = new DataInputStream(in);
						inputStreamMap.put(in, Lists.newArrayList());
						SimpleThreadPoolUtil.pool.addTask(()->{
							SimpleThreadPoolUtil.pool.addTask(()->{
								try {
									formatRead(in);
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
							while(true) {
								synchronized (in) {//TODO
									try {
										List<String> userList = inputStreamMap.get(in);
										userList.forEach(key ->{
											byte[] val = receivedMap.get(key);
											System.out.println("consumer:" + key + ", " + new String(val));
											//返回客户端数据
											formSend("callback data".getBytes(), key, out);
											receivedMap.remove(key);
										});
										userList.clear();
//										receivedMap.forEach((key, val)->{
//											System.out.println("consumer:" + key + ", " + new String(val));
//											//返回客户端数据
//											formSend("callback data".getBytes(), key, out);
//										});
										in.wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
//						byte[] data = new byte[1024];
//						while(true) {
//							int len = input.read(data);
//							System.out.println("received head:" + new String(data,0, len));
//							//开始写数据
//							
//						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void formatRead(InputStream in) throws IOException {
		DataInputStream input = new DataInputStream(in);
		byte[] cache = new byte[1024];//第一个，前4个是块的个数
		ByteArrayOutputStream innerCache = new ByteArrayOutputStream();
		boolean first = true;
		int num = 0;
		String user = "";
		while(true) {
			int len = input.read(cache);
			System.out.println("received:");
			if(first) {//计算块个数：
				num = ((cache[0] & 0xff) << 24) + ((cache[1] & 0xff) << 16) + ((cache[2] & 0xff) << 8) + cache[3];
				if(num > 0) {
					first = false;
				}
				//计算响应谁的64个字节
				user = new String(cache, 4, 68);
				continue;
			}
			innerCache.write(cache, 0, len);
			if(--num == 0) {//读取完毕，放到用户区域
				first = true;
				List<String> userList = inputStreamMap.get(in);
				userList.add(user.trim());
				receivedMap.put(user.trim(), cache);
				synchronized (in) {
					in.notify();
				}
			}
		}
	}
	
	public void formSend(byte[] data, String user, OutputStream out) {//以后byte[]可以换成list<byte[]> 或者byte[][]
		byte[] syn = user.getBytes();
		int blockLen = data.length / 1024 + (data.length % 1024 > 0 ? 1:0);
		byte[] header = new byte[1024];
		header[0] = (byte)(blockLen >> 24);
		header[1] = (byte)(blockLen >> 16);
		header[2] = (byte)(blockLen >> 8);
		header[3] = (byte)(blockLen >> 0);
		//增加64字节user
		int index = 4;
		for(byte s : syn) {
			header[index++] = s;
			if(index > 67) {//截取
				break;
			}
		}
		
		try {
			//发送头数据
			out.write(header);
			out.flush();//发送一个结束符？
			//发送有效数据
			int startPos = 0;
			int endPos = 1024 > data.length ? data.length : 1024;
			byte[] buffer = new byte[1024];
			int j = startPos;
			for(int i = 0; i < 1024; j++,i++) {
				if(j >= endPos) {
					//临时数据也开始刷
					out.write(buffer, 0, i);
					out.flush();
					break;
				}
				buffer[i] = data[j]; 
				if(i == 1023) {
					i = -1;
					//开始刷数据,完整
					out.write(buffer);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new ServiceServerUtil().startServer();
	}
}
