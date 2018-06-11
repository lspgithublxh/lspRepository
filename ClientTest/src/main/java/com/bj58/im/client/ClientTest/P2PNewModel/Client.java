package com.bj58.im.client.ClientTest.P2PNewModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.bj58.im.client.ClientTest.ReadCallBack;

public class Client {

	
	public static void main(String[] args) {
		test();
	}
	
private static void test() {
		try {
			Client c = new Client();
			System.out.println("get a port");
			Scanner scc = new Scanner(System.in);
			String port = scc.nextLine();
			System.out.println("get port : " + port);
			int listenPort = Integer.valueOf(port);
			Socket s = new Socket("localhost", 11123);
			c.new WriteThread(s.getOutputStream(), listenPort).start();
			c.new ReadThread(s.getInputStream(), listenPort).start();
			ServerSocket server = new ServerSocket(listenPort);
			while(true) {
				Socket ss = server.accept();
				System.out.println("hahahahahhah ss ");
				c.new WriteThread(ss.getOutputStream(), listenPort).start();
				c.new ReadThread(ss.getInputStream(), listenPort).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

Map<String, Socket> numSocket = new ConcurrentHashMap<String, Socket>();

public class WriteThread extends Thread{
		
		OutputStream out;

		int listenPort;
		public WriteThread(OutputStream out, int listenPort) {
			super();
			this.out = out;
			this.listenPort = listenPort;
		}
		
		@Override
		public void run() {
			
			DataOutputStream outData = new DataOutputStream(out);
			try {
				outData.writeUTF("hello");
				outData.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			int i = 0;
			while(true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String line = "";
				if(i++ == 0) {
					line = "woo:127.0.0.1-" + listenPort;
						
				}else {
					line = "hello";
				}
				try {
					outData.writeUTF(line);
					outData.flush();
					System.out.println("client:" + line);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
public class ReadThread extends Thread{
	
	public InputStream in = null;

	public ReadCallBack callback = null;
	int listenPort;
	
	public ReadThread(InputStream in, int listenPort) {
		super();
		this.in = in;
		this.listenPort = listenPort;
	}


	@Override
	public void run() {
		DataInputStream dataIn = new DataInputStream(in);
		
			try {
				while(true) {
					String line = dataIn.readUTF();
					System.out.println("server:" + line);
					if(line.startsWith("online:")) {
						System.out.println("online :" + line);
					}else if(line.startsWith("listen:")) {
						//建立链接
						String[] ipport = line.substring(line.indexOf(":") + 1).split("-");
						Socket sx = new Socket(ipport[0], Integer.valueOf(ipport[1]));
						new WriteThread(sx.getOutputStream(), listenPort).start();
						new ReadThread(sx.getInputStream(), listenPort).start();
					}else if(line.startsWith("offline:")) {
						System.out.println("offline:" + line);
					}
//					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
}
