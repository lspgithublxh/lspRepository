package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AC2 {

	
	private Map<String, Object> config = new HashMap<>();
	private static AC2 inst = new AC2();
	public static AC2 getInstance() {
		return inst;
	}
	
	/**
	 * 从服务端获取数据
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月10日
	 * @Package com.bj58.fang.ArBpCc
	 * @return Object
	 */
	public Object readData(String url) {//必须认为
		//
//		try {
//			Socket socket = new Socket("localhost", 12334);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}//连接到服务器
		
		return null;
	}
	
	/**
	 * 读取服务实体方法
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月10日
	 * @Package com.bj58.fang.ArBpCc
	 * @return SDEntity
	 */
	public SDEntity getService(String servName) {
		//请求、获取服务线
//		String servName = "abc";
		SDEntity ser = null;
		System.out.println("AC2 start to get service entity");
		try {
			if(config.containsKey(servName)) {
				return (SDEntity) config.get(servName);
			}
			Socket socket = new Socket("localhost", 12444);
//			Map<String, Object> config = new HashMap<>();
			config.put("serviceName", servName);
			Object lock = new Object();
			config.put("mainLock", lock);
			new ReadHT(socket.getInputStream(), 1, socket).config(config).start();
			new WriteHT(socket.getOutputStream(), 4, socket).config(config).start();
			synchronized (lock) {
				lock.wait();
			}
			ser = (SDEntity) config.get(servName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ser;
	}
	
	public void readHandle(InputStream in, Socket sock, int type, ReadHT reader) {
		switch (type) {
		case 1://表示从注册中心已经读取到服务信息在context里
			Object lock = reader.context.get("mainLock");
			synchronized (lock) {
				lock.notify();
			}
			//回写数据
			break;
		default:
			break;
		}
	}
	
	public void writeHandle(OutputStream out, Socket comSoc, int type, WriteHT writer) {//实际真正调用
		//实际写什么这里决定
		switch (type) {
		case 1://取服务
			String serName = (String) writer.context.get("serviceName");
			writer.writeStr("req:service|" + serName);
			break;
		case 2://调用服务返回数据
			String request = (String) writer.context.get("request");
			String para = (String) writer.context.get("para");
			writer.writeStr(request);

			//直接写会失败---方法的选择  ---先str后data就不会失败
			//休息一会儿，进行发送--否则缓冲了，除非也读取长度
//			byte[] front = new byte[1024];
//			byte[] content = request.getBytes();
//			for(int i = 0; i < content.length; i++) {//都是正数
//				front[i] = content[i];
//			}
//			for(int i = content.length; i < 1024; i++) {
//				front[i] = 0;
//			}
//			System.out.println("start --- send method data to server---:");
//			writer.writeArray(front, 0, front.length);
//			String para = (String) writer.context.get("para");
//			System.out.println("start --- send method data to server---:");
			byte[] data = para.getBytes();
			for(int i = 0; i < data.length; i+= 1024) {
				int end = i + 1024;
				byte[] buf = new byte[1024];
				if(i + 1024 > data.length) {
					end = data.length;
				}
				for(int j = i; j < end; j++) {
					buf[j - i] = data[j];
				}
				writer.writeArray(buf, 0, 1024);//发送多余的一些，
			}
			break;
		default:
			break;
		}
	}
}
