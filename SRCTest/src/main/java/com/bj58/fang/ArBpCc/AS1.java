package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
}
