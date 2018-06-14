package com.bj58.im.client.ClientTest.UI;

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
import java.util.Scanner;

/**
 * 一旦连接另外一个客户端，需要在console输入两次，第一次是给client的，第二次是给server的。
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
	
	Map<String, Object[]> configMap = new HashMap<String, Object[]>();
	
	public void startClient(String arg0) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		new ReadThread(in, socket).start();
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
						public void run() {
							writeT.writeNow("client-server:" + port);
						}
						
					}).start();
					while(true) {
						Socket s = serv.accept();
						new ReadThread(s.getInputStream(), s).start();
						WriteThread wt = new WriteThread(s.getOutputStream());
						//本名
						String name = "Jetty";
						configMap.put(name, new Object[] {new WriteThread(s.getOutputStream())});
						//向ui发起建立新pane方法，并写入内容...目前向老pane写
						ui.writeRightTextMessage("Tom", name);
						wt.start();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		//后者启动移动一个socket连接已经启动的客户端 TODO
	}
	
	FileOutputStream outf = null;
	public Client_PBetter() {
		
	}
	
	Map<String, String> onlineMap = new HashMap<String, String>();
	
	Map<String, String> keyportMap = new HashMap<String, String>();
	
public class ReadThread extends Thread{
		
		public InputStream in = null;
		Socket soc = null;
		public ReadThread(InputStream in, Socket soc) {
			super();
			this.in = in;
			this.soc = soc;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			boolean writeOk = false;
				try {
					while(true) {
						try {
							outf = new FileOutputStream("D:\\project\\my_project\\ClientTest\\src\\main"
									+ "\\java\\com\\bj58\\im\\client\\ClientTest\\BaseClient\\BClient.txt", true);// TODO 
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						String line = dataIn.readUTF();
						System.out.println("server:" + line);
						outf.write(("server:" + line + "\r\n").getBytes());
						outf.flush();
//						outf.close();
						if(line.startsWith("online:")) {
							String[] ipPo = line.split(";");
							onlineMap.put(ipPo[0].substring(ipPo[0].lastIndexOf(":") + 1), ipPo[1]);//qq - ip-port上线
							System.out.println("now , onlineList:" + onlineMap);
						}else if(line.startsWith("onlinelist:")) {
							String[] ipPo = line.split("%");
							for(String zh : ipPo) {
								String[] nip = zh.split(";");
								onlineMap.put(nip[0].substring(nip[0].lastIndexOf(":") + 1), nip[1]);
							}
							System.out.println("now , onlineList:" + onlineMap);
						}else if(line.startsWith("client-server:")) {//服务端 发来的，则应该作为主动方向新起客户端发起连接－－－或者有这种能力
							String[] keyPort = line.substring(line.indexOf(":") + 1).split(";");		
							keyportMap.put(keyPort[0].substring(1), keyPort[1]);
							System.out.println("now, client-server:" + keyportMap);
							//成功发现端口，可以尝试连接客户端了！！
							Socket so = new Socket("localhost", Integer.valueOf(keyPort[1]));
							new ReadThread(so.getInputStream(), so).start();
							WriteThread wth = new WriteThread(so.getOutputStream());
							wth.writeNow("client to client: I am you friend.Tom" + wth.hashCode());
							wth.start();
						}else if(line.startsWith("client to client:")) {
							//此时应该调用写线程进行回复
							System.out.println("em, ok, good.");
							String name = line.substring(line.indexOf("\\.") + 1);
							configMap.put(name, new Object[] {new WriteThread(soc.getOutputStream())});
							//向ui发起建立新pane方法，并写入内容...目前向老pane写
							ui.writeRightTextMessage("Tom", name);
							new WriteThread(soc.getOutputStream()).writeNow("good ,i received: my friend..");
							writeOk = true;
						}else {
							if(writeOk) {
								ui.writeRightTextMessage("Tom", line);//发送的消息都写入
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
