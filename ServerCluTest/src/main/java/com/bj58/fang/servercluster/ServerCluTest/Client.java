package com.bj58.fang.servercluster.ServerCluTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	
	public static void main(String[] args) {
		//请求一个token试试
		try {
			final Socket ss = new Socket("10.8.18.214", 10456);
//			final Socket ss = new Socket("10.8.9.59", 10456);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						DataInputStream ins = new DataInputStream(ss.getInputStream());
						System.out.println("heheh");
						System.out.println("  " + ins.readUTF());
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("end");
					}
				}
			}).start();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						DataOutputStream out = new DataOutputStream(ss.getOutputStream());
						System.out.println("hehhexx");
						out.writeUTF("cget|token|clientId|clientSecret");
						out.flush();
						System.out.println("hehhexx2");
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("end");
						
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
