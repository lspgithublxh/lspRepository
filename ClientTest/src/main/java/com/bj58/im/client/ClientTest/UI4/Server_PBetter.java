package com.bj58.im.client.ClientTest.UI4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

import com.bj58.im.client.ClientTest.OldP.P2PNewModel.Server.WriteThread2;

public class Server_PBetter {
	public static void main(String[] args) throws IOException {
		new Server_PBetter().startServer();
	}
	Map<String, Socket> numSocket = new ConcurrentHashMap<String, Socket>();
	
	Map<String, Map<String, Object>> clientMap = new ConcurrentHashMap<String, Map<String, Object>>();
	
	Map<String, String> guanxiMap = new HashMap<String, String>();
	
	public void startServer() throws IOException {
		ServerSocket socket = new ServerSocket(10000);
		System.out.println("start listen at 10000");
//		zhouqiclear();
		
		while(true) {
			Socket s = socket.accept();
			//1.上线通知：
			String hasOnline = "onlinelist:";
			//上线通知意义不大，---关键是发送了服务端port之后进行的通知
			for(String num : numSocket.keySet()) {
				Socket ns = numSocket.get(num);
//				System.out.println("用" +ns.toString() +  "发送内容:online:" + s.toString());
//				new WriteThread2(ns.getOutputStream(), "online:" + s.toString()).start();
//				ns.getOutputStream().write(("online:" + s.toString()).getBytes());
//				ns.getOutputStream().flush();
				WriteThread wr2 = new WriteThread(ns.getOutputStream());
//				wr2.start();
				wr2.writeNow("-----------上线通知--------");
				String remote = s.getRemoteSocketAddress().toString();
				String local = ns.getRemoteSocketAddress().toString();
				wr2.writeNow("online:" + remote.substring(remote.indexOf(":")) + ";" + s.getRemoteSocketAddress().toString().substring(1));
				hasOnline += local.substring(remote.indexOf(":")) + ";" + local.substring(1) + "%";
			}
			//已经上线的通知：
			
			//2.加入列表,可以和上面线程同步// TODO 
			numSocket.put("client" + s.getRemoteSocketAddress(), s);
//			Map<String, Object> config = new HashMap<String, Object>();
//			config.put("socket", s);
//			clientMap.put(s.getRemoteSocketAddress().toString(), config);
			OutputStream out = s.getOutputStream();
			
			WriteThread wr = new WriteThread(out);
			wr.start();
			wr.writeNow("-----------hello--------");
			String online = "";
			for(String key : clientMap.keySet()) {
				Map<String, Object> param = clientMap.get(key);
				online += param.get("serverIP") + "-" + param.get("serverPort") + "|";
			}
			if(!"".equals(online)) {
				wr.writeNow("onlinelist|" + online.substring(0, online.length() - 1));
			}
			InputStream in = s.getInputStream();
			new ReadThread(in, s).start();
			
			//这里证明，一个outputstream只能被绑定一次----且直接写发不出去 TODO
//			s.getOutputStream().write("xxxxx".getBytes());
//			s.getOutputStream().flush();
//			new WriteThread2(s.getOutputStream(), "server:you online:" + s.toString()).start();
			
		}
	}

	private void zhouqiclear() {
		//0.周期遍历---检查socket状态，下线通知-
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					while(true) {
						Thread.sleep(1000);
						List<String> li = new ArrayList<>();
						for(String num : numSocket.keySet()) {
							Socket ns = numSocket.get(num);
							if(ns == null || ns.isClosed() ) {//|| !ns.getKeepAlive()
								System.out.println("offline:" + ns);
								System.out.println(ns + ";" + ns.isClosed() );//+ ";" + ns.getKeepAlive()
								li.add(num);
							}
						}
						for(String cnum : li) {
							numSocket.remove(cnum);
						}
						for(String cnum : li) {
							for(String num : numSocket.keySet()) {
								Socket ns = numSocket.get(num);
								if(ns != null && !ns.isClosed() && ns.getKeepAlive()) {
									System.out.println("send offline");
//									new WriteThread2(ns.getOutputStream(), "offline:" + cnum).start();
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
		}).start();
	}
	
//	Map<String, String> portMap = new HashMap<String, String>();
	
	public class ReadThread extends Thread{
		
		public InputStream in = null;

		Socket s = null;
		public ReadThread(InputStream in, Socket s) {
			super();
			this.in = in;
			this.s = s;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			
				try {
					while(true) {
						String line = dataIn.readUTF();
						System.out.println("client:" + line);
						if(line.startsWith("client-server|")) {//新上线的client都会发过来
							String[] dt = line.split("\\|");
							Map<String, Object> config = new HashMap<String, Object>();
							clientMap.put(line.split("\\|")[1] + ":" + line.split("\\|")[2], config);
							config.put("serverPort", line.split("\\|")[2]);
							config.put("serverIP", line.split("\\|")[1]);
//							guanxiMap.put(s.getRemoteSocketAddress().toString(), line.split("\\|")[1] + ":" + line.split("\\|")[2]);
//							portMap.put("client" + s.getRemoteSocketAddress(), line.split("|")[3]);
//							System.out.println("portMap now : " + portMap);
							//所有的其他ns发送：
							for(String num : numSocket.keySet()) {
								Socket ns = numSocket.get(num);
								if(ns != s) {
									WriteThread wr2 = new WriteThread(ns.getOutputStream());
									String remote = s.getRemoteSocketAddress().toString();//占用的端口作为他的key
									wr2.writeNow(line);
//									wr2.writeNow("client-server:" + remote.substring(remote.indexOf(":")) + ";" + line.split(":")[1]);
//									System.out.println("client-server port is :" + line.split(":")[1]);
								}
							}
						}else if(line.startsWith("close_window|")) {
							System.out.println(line);
							System.out.println(numSocket);
							String[] dt = line.split("\\|");
//							String offline_ipS = guanxiMap.get(dt[1]);
							for(String num : numSocket.keySet()) {
								Socket ns = numSocket.get(num);
								if(ns != s) {
									WriteThread wr2 = new WriteThread(ns.getOutputStream());
									wr2.writeNow(line);
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
//	public class WriteThread2 extends Thread{
//		OutputStream out;
//		String line;
//		
//		public WriteThread2(OutputStream out, String line) {
//			super();
//			this.out = out;
//			this.line = line;
//		}
//		
//		@Override
//		public void run() {
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			System.out.println("有新机器上线，发送给已上线机器:" + line);
//			DataOutputStream outData = new DataOutputStream(out);
//			try {
//				outData.write(line.getBytes());
//				outData.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	
	public class WriteThread extends Thread{
		
		OutputStream out;

		public WriteThread(OutputStream out) {
			super();
			this.out = out;
		}
		
		DataOutputStream outData = null;
		
		@Override
		public void run() {
			outData = new DataOutputStream(out);
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				System.out.println(outData);
				try {
					outData.writeUTF("whtaaaaaaaaaaaaaaaa");//line == null || "".equals(line) ? "默认":line
					outData.flush();
					System.out.println("server:" + line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void writeNow(String line) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			outData = new DataOutputStream(out);
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
