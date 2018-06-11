package com.bj58.im.client.ClientTest.P2PNewModel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	public static void main(String[] args) {
		test();
	}
	Map<String, Socket> numSocket = new ConcurrentHashMap<String, Socket>();
	
	private static void test() {
		Server server = new Server();
		System.out.println("start server:");
		int host = 1;
		//0.周期遍历---检查socket状态，下线通知-
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					while(true) {
						Thread.sleep(1000);
						List<String> li = new ArrayList<>();
						for(String num : server.numSocket.keySet()) {
							Socket ns = server.numSocket.get(num);
							if(ns == null || ns.isClosed() || !ns.getKeepAlive()) {
								System.out.println("offline:" + ns);
								li.add(num);
							}
						}
						for(String cnum : li) {
							server.numSocket.remove(cnum);
						}
						for(String cnum : li) {
							for(String num : server.numSocket.keySet()) {
								Socket ns = server.numSocket.get(num);
								if(ns != null && !ns.isClosed() && ns.getKeepAlive()) {
									System.out.println("send offline");
									server.new WriteThread2(ns.getOutputStream(), "offline:" + cnum).start();
								}
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();;
		try {
			ServerSocket sev = new ServerSocket(11123);
			while(true) {
				Socket s = sev.accept();
				System.out.println("online:" + s.getRemoteSocketAddress());
				//1.上线通知：
				for(String num : server.numSocket.keySet()) {
					Socket ns = server.numSocket.get(num);
					System.out.println("send online:");
					server.new WriteThread2(ns.getOutputStream(), "online:" + s.toString()).start();
				}
				//2.加入列表,可以和上面线程同步// TODO 
				server.numSocket.put("client" + host++, s);
				//3.读
				server.new ReadThread(s, server);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public class WriteThread2 extends Thread{
		OutputStream out;
		String line;
		
		public WriteThread2(OutputStream out, String line) {
			super();
			this.out = out;
			this.line = line;
		}
		
		@Override
		public void run() {
			DataOutputStream outData = new DataOutputStream(out);
			try {
				outData.write(line.getBytes());
				outData.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class WriteThread extends Thread{
		
		OutputStream out;

		public WriteThread(OutputStream out) {
			super();
			this.out = out;
		}
		
		@Override
		public void run() {
			DataOutputStream outData = new DataOutputStream(out);
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				
				try {
					outData.writeUTF(line);
					outData.flush();
					System.out.println("server:" + line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
Map<String, String> haoIp = new HashMap<String, String>();
	
public class ReadThread extends Thread{
		
		public InputStream in = null;
		OutputStream out;
		Socket s ;
		Server server;

		public ReadThread(Socket s, Server ser) {
			super();
			this.s = s;
			this.server = ser;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn;
			try {
				dataIn = new DataInputStream(s.getInputStream());
				BufferedInputStream bin = new BufferedInputStream(dataIn);
				String flag = null;
				String fileName = null;
				while(true) {
					//1.两次flush,会读两次
					String line = dataIn.readUTF();
					if(line.startsWith("woo:")) {
						String online = line.substring(line.indexOf(":") + 1);
						for(String num : server.numSocket.keySet()) {
							Socket ns = server.numSocket.get(num);
							if(!s.getRemoteSocketAddress().toString().equals(ns.getRemoteSocketAddress().toString())) {
								server.new WriteThread2(ns.getOutputStream(), "listen:" + online).start();
								//上线通知
								System.out.println("真正通知上线机器所在的listen port");
							}
						}
					}else {
						System.out.println("read:" + line);
					}
					
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
