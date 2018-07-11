package com.bj58.im.client.ClientTest.UI5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;

/**
 * 一旦连接另外一个客户端，需要在console输入两次，第一次是给client的，第二次是给server的。
 * 2.上线时建立client列表； 静候时建立client列表。。。。。此时都唯独缺乏WriteThread   ..... 只有主动连接和被动接受时 才增加WriteThread
 *  3.时序问题
 * @ClassName:Client_P
 * @Description:
 * @Author lishaoping
 * @Date 2018年6月12日
 * @Version V1.0
 * @Package com.bj58.im.client.ClientTest.BaseClient
 */
public class Client_PBetter {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println(args[0]);
		new Client_PBetter().startClient(args[0]);
	}
	
	Main2 ui;
	
	public Client_PBetter(Main2 ui) {
		this.ui = ui;
	}
	
//	Map<String, Object[]> configMap = new HashMap<String, Object[]>();
	//全局锁
	Object mainlock = new Object();
	Object sublock = new Object();
	String[] content = {""};
	BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>(1000);
	
	Socket server_socket = null;
	/**
	 * 线程之间通信--线程同步：来让一个线程指定执行之后，执行另一个线程中的代码---再切换回来---如此来回！！！
	 * 问题：notify() 之后的wait() 和另一个线程notify()的先后顺序？--能保证是先wait()再notify()吗
	 * 消息队列方式--更可靠
	 * @param 
	 * @author lishaoping
	 * @Date 2018年6月15日
	 * @Package com.bj58.im.client.ClientTest.UI
	 * @return void
	 */
	public void startClient(String arg0) throws UnknownHostException, IOException {
		
		server_socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = server_socket.getOutputStream();
		InputStream in = server_socket.getInputStream();
		new ReadThread(in, server_socket, "127.0.0.1:10000", "127.0.0.1:" + arg0).start();
		WriteThread writeT = new WriteThread(out);
		writeT.start();
		
		//add : 自己的model
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket serv;
				try {
					int port = Integer.valueOf(arg0);
					serv = new ServerSocket(port);// TODO  11567 11345
					new Thread(new Runnable() {
						@Override
						public void run() {//应当传递一个json，最好，这样还可以传递头像、姓名、等更基本的信息
							writeT.writeNow("client-server|" + "127.0.0.1" + "|" + port);
						}
						
					}).start();
					while(true) {
						Socket s = serv.accept();
						new ReadThread(s.getInputStream(), s, "--", "127.0.0.1:" + arg0).start();
						WriteThread wt = new WriteThread(s.getOutputStream());
						//本名
//						String name = "Tom";
						System.out.println("accept_1");
						ui.cmdHandleCenter(s.getRemoteSocketAddress().toString(), 
								"accept_1", new Object[] {new WriteThread(s.getOutputStream())});
//						ui.config.put(s.getRemoteSocketAddress().toString(), new WriteThread(s.getOutputStream()));
						//向ui发起建立新pane方法，并写入内容...目前向老pane写
						wt.start();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		//后者启动移动一个socket连接已经启动的客户端 TODO
	}
	
	public void offline() {
		try {
			WriteThread writeT = new WriteThread(server_socket.getOutputStream());
			writeT.writeNow("close_window|127.0.0.1:" + ui.config.get("Self").get("serverPort"));//server_socket.getLocalSocketAddress().toString()不是最简单的
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	FileOutputStream outf = null;
	public Client_PBetter() {
		
	}
	
//	Map<String, String> onlineMap = new HashMap<String, String>();
	
//	Map<String, String> keyportMap = new HashMap<String, String>();
	
public class ReadThread extends Thread{
		
		public InputStream in = null;
		Socket soc = null;
		String duifangname = "";//对方server的ip-port
		String zijiname = "";
		public ReadThread(InputStream in, Socket soc, String duifangname, String zijiname) {
			super();
			this.in = in;
			this.soc = soc;
			this.duifangname = duifangname;
			this.zijiname = zijiname;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			boolean writeOk = false;
			boolean servWriteOk = false;
				try {
					while(true) {
						try {
							outf = new FileOutputStream("D:\\project\\my_project\\ClientTest\\src\\main"
									+ "\\java\\com\\bj58\\im\\client\\ClientTest\\BaseClient\\BClient.txt", true);// TODO 
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						String line = dataIn.readUTF();
						System.out.println("====ting-----server:" + line);
						outf.write(("server:" + line + "\r\n").getBytes());
						outf.flush();
//						outf.close();
						if(line.startsWith("online:")) {//服务端发送来的，但是无法知道client的server信息
//							String[] ipPo = line.split(";");
//							onlineMap.put(ipPo[0].substring(ipPo[0].lastIndexOf(":") + 1), ipPo[1]);//qq - ip-port上线
//							System.out.println("now , onlineList:" + onlineMap);
						}else if(line.startsWith("onlinelist|")) {//只有刚上线的client才会收到，服务端发来的，每个client上线会发送server ipport给服务端，
							System.out.println(line);
							String[] clientArr = line.split("\\|");
							for(int i = 1; i < clientArr.length; i++) {
								Map<String, Object> config = new HashMap<String, Object>();
								String userIPPort = clientArr[i];
								String[] uip = userIPPort.split("-");
								config.put("serverIP", uip[0]);
								config.put("serverPort", uip[1]);
								config.put("Pane", ui.config.get("Self").get("Pane"));
								config.put("YPoint", ui.config.get("Self").get("YPoint"));
								ui.config.put(uip[0] + ":" + uip[1], config);
							}
							System.out.println(ui.config);
							System.out.println("-----onlinelist-----" + clientArr[1]);
//							String[] ipPo = line.split("%");
//							for(String zh : ipPo) {
//								String[] nip = zh.split(";");
//								onlineMap.put(nip[0].substring(nip[0].lastIndexOf(":") + 1), nip[1]);
//							}
//							System.out.println("now , onlineList:" + onlineMap);
						}else if(line.startsWith("client-server|")) {//服务端广播转发来的，则应该作为主动方向新起客户端发起连接－－－或者有这种能力
							String[] keyPort = line.split("\\|");
							Map<String, Object> config = new HashMap<String, Object>();
							config.put("serverIP", keyPort[1]);
							config.put("serverPort", keyPort[2]);
							config.put("Pane", ui.config.get("Self").get("Pane"));
							config.put("YPoint", ui.config.get("Self").get("YPoint"));
							ui.config.put(keyPort[1] + ":" + keyPort[2], config);
//							keyportMap.put(keyPort[0].substring(1), keyPort[1]);
//							System.out.println("now, client-server:" + keyportMap);
							//成功发现端口，可以尝试连接客户端了！！
							Socket so = new Socket(keyPort[1], Integer.valueOf(keyPort[2]));
							new ReadThread(so.getInputStream(), so, keyPort[1] + ":" + keyPort[2], zijiname).start();
							WriteThread wth = new WriteThread(so.getOutputStream());
							wth.writeNow("client to client:" + zijiname);
							wth.start();
							//主动发送方 -add
							String name = "Hink";
							System.out.println("clientServerPort_1");
//							duifangname = keyPort[1] + ":" + keyPort[2];//此时duifangname是服务端
							ui.cmdHandleCenter(keyPort[1] + ":" + keyPort[2], 
									"clientServerPort_1", new Object[] {new WriteThread(so.getOutputStream())});
//							configMap.put(name, new Object[] {new WriteThread(so.getOutputStream())});
							//向ui发起建立新pane方法，并写入内容...目前向老pane写
//							ui.writeRightTextMessage("Tom", name);
//							try {
//								messageQueue.put(name);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}//看会不会有消息遗漏
//							Platform.runLater(new Runnable() {
//								@Override
//								public void run() {
//									ui.writeRightTextMessage("Tom", name);
//								}
//							});
							servWriteOk = true;
						}else if(line.startsWith("client to client:")) {
							//此时应该调用写线程进行回复
							System.out.println("em, ok, good." + duifangname);
							String[] ipPort = line.split("\\:");
							//此时duifangname必然是--
							duifangname = ipPort[1] + ":" + ipPort[2];
							//同时真正知道上线了--因为知道对方server ip-port了 TODO TODO
//							Map<String, Object> config = new HashMap<String, Object>();
//							config.put("serverIP", ipPort[1]);
//							config.put("serverPort", ipPort[2]);
//							config.put("Pane", ui.config.get("Self").get("Pane"));
//							config.put("YPoint", ui.config.get("Self").get("YPoint"));
//							ui.config.put(ipPort[1] + ":" + ipPort[2], config);
							//通知真正上线了
							ui.cmdHandleCenter(duifangname, 
									"clientToMe_1", new Object[] {new WriteThread(soc.getOutputStream())});
//							String name = line.substring(line.indexOf("\\.") + 1);
							//先不接受信息
//							configMap.put(name, new Object[] {new WriteThread(soc.getOutputStream())});
							//向ui发起建立新pane方法，并写入内容...目前向老pane写
//							ui.writeRightTextMessage("Tom", name);
//							Platform.runLater(new Runnable() {
//								@Override
//								public void run() {
//									System.out.println("runfirst");
//									ui.writeRightTextMessage("Tom", name);
//									
//								}
//							});
//							try {
//								messageQueue.put(name);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
							new WriteThread(soc.getOutputStream()).writeNow("good ,i received: my friend..");
							writeOk = true;
						}else if(line.startsWith("close_window|")){
							System.out.println(line);
							String[] dt = line.split("\\|");
							//移除socket和移除头像：和清除相应内容
							ui.cmdHandleCenter(duifangname, "closeWindow_1", new Object[] {dt[1]});
						}else {
							System.out.println("---the client:" + duifangname);
							if(ui.config.containsKey(duifangname)) {
								System.out.println("readClient_1");
								ui.cmdHandleCenter(duifangname, 
										"readClient_1", new Object[] {line});
//								ui.writeRightTextMessage("Tom", line);//发送的消息都写入
//								Platform.runLater(new Runnable() {
//									@Override
//									public void run() {
//										System.out.println("runSecond");
//										ui.writeRightTextMessage(soc.getRemoteSocketAddress().toString(), line);
//									}
//								});
//								try {
//									messageQueue.put(line);
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
							}
							
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
public class WriteThread_fast extends Thread{
	
	OutputStream out;
	String line;

	public WriteThread_fast(OutputStream out, String line) {
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
		
		public void writeNow(String line) {
			DataOutputStream outData = new DataOutputStream(out);
			try {
				outData.writeUTF(line);
				outData.flush();
				System.out.println("client:" + line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			DataOutputStream outData = new DataOutputStream(out);
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
//				
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
	
	class FindJavaVisitor extends SimpleFileVisitor<Path>{
		private List<String> result;
        public FindJavaVisitor(List<String> result){
            this.result = result;
        }
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
        	if(file.toString().contains("$")) {
       		 return FileVisitResult.CONTINUE;
        	}
            if(file.toString().endsWith(".png") || file.toString().endsWith("jpg")){//view.php
                result.add(file.toFile().getAbsolutePath());
            }
            return FileVisitResult.CONTINUE;
        }
	}
}
