package com.bj58.im.client.ClientTest.BaseClient;

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
		new Client_PBetter().startClient();
	}
	
	
	public void startClient() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		new ReadThread(in).start();
		WriteThread writeT = new WriteThread(out);
		writeT.start();
		
		//add : 自己的model
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ServerSocket serv;
				try {
					int port = 11568;
					serv = new ServerSocket(port);// TODO  11567 11345
					new Thread(new Runnable() {
						@Override
						public void run() {
							writeT.writeNow("client-server:" + port);
						}
						
					}).start();
					while(true) {
						Socket s = serv.accept();
						new ReadThread(s.getInputStream()).start();
						new WriteThread(s.getOutputStream()).start();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		//后者启动移动一个socket连接已经启动的客户端 TODO
//		Socket s_to_client = new Socket("localhost", 11345);
//		OutputStream out_toClient = s_to_client.getOutputStream();
//		InputStream in_toClient = s_to_client.getInputStream();
//		new ReadThread(in_toClient).start();
//		new WriteThread(out_toClient).start();
	}
	
	FileOutputStream outf = null;
	public Client_PBetter() {
		
	}
	
	Map<String, String> onlineMap = new HashMap<String, String>();
	
public class ReadThread extends Thread{
		
		public InputStream in = null;

		public ReadThread(InputStream in) {
			super();
			this.in = in;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			
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
						outf.close();
						if(line.startsWith("online:")) {
							String[] ipPo = line.split(";");
							onlineMap.put(ipPo[0], ipPo[1]);//qq - ip-port上线
							System.out.println("now , onlineList:" + onlineMap);
						}else if(line.startsWith("onlinelist:")) {
							String[] ipPo = line.split("%");
							for(String zh : ipPo) {
								String[] nip = zh.split(";");
								onlineMap.put(nip[0], nip[1]);
							}
							System.out.println("now , onlineList:" + onlineMap);
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
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
//				if("1".equals(line)) {
//					//搜索D:下的一幅图片，传给另一端
//					String file = "D:\\";
//					File file2 = new File(file);
//					List<String> result = new LinkedList<String>();
//					try {
//						Files.walkFileTree(Paths.get(file), new FindJavaVisitor(result));
//					}catch (java.nio.file.AccessDeniedException e) {
//					}
//					catch (IOException e) {
//						e.printStackTrace();
//					}
//						
//					line = result.subList(0, 100).toString();
//				}else if("2".equals(line)) {
//					//搜索D:下的文件一个，传给另一端
//					
//				}
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
