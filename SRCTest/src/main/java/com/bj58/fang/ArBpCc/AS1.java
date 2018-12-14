package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动侦听
 * 回调处理
 * 发送注册
 * @ClassName:AS1
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月7日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class AS1 {

	public static void main(String[] args) {
		
	}
	
	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月7日
	 * @Package com.bj58.fang.ArBpCc
	 * @return void
	 */
	public void start() {
		try {
			//1.起
			ServerSocket server = new ServerSocket(12334);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Socket toclient = server.accept();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			//2.发送
			Socket toreg = new Socket("localhost", 12444);
			new WriteHT(toreg.getOutputStream(), 3, toreg).config("").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readHandle(InputStream in, Socket sock, int type, ReadHT reader) {
		switch (type) {
		case 1:
			//取参数，执行方法调用
			Object param = reader.context.get("para");
			Object inter = reader.context.get("interName");
			Object method = reader.context.get("methodName");
			String data = methodExecute(inter, method, param);
			//写回数据
			Map<String, Object> context = new HashMap<>();
			context.put("data", data);
			context.put("status", "200");
			context.put("message", "");
			try {
				new WriteHT(sock.getOutputStream(), 6, sock).config(context).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private String methodExecute(Object inter, Object method, Object param) {
		System.out.println("service call success!!");
		return "data";
	}

	public void writeHandle(OutputStream out, Socket comSoc, int i, WriteHT writeHT) {
		System.out.println("server write callback");
	}
}
