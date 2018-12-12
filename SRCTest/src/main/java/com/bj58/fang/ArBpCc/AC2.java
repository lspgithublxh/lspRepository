package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class AC2 {

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
		try {
			Socket socket = new Socket("localhost", 12444);
			Map<String, Object> config = new HashMap<>();
			config.put("serviceName", servName);
			Object lock = new Object();
			config.put("mainLock", lock);
			new WriteHT(socket.getOutputStream(), 4, socket).config(config).start();
			new ReadHT(socket.getInputStream(), 1, socket).config(config).start();
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
			break;
		default:
			break;
		}
	}
	
	public void writeHandle(OutputStream out, Socket comSoc, int type, WriteHT writer) {//实际真正调用
		//实际写什么这里决定
		switch (type) {
		case 1://
			String serName = (String) writer.context.get("serviceName");
			writer.writeStr("req:service|" + serName);
			break;

		default:
			break;
		}
	}
}
