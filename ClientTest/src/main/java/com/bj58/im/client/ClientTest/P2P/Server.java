package com.bj58.im.client.ClientTest.P2P;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		try {
			new Server().test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Map<String, String> haoIp = new HashMap<String, String>();

	private void test() throws IOException {
		ServerSocket socket = new ServerSocket(19999);
		System.out.println("start listen at 19999");
		int i = 0;
		while(true) {
			Socket s = socket.accept();
			System.out.println(s.getReuseAddress());
			System.out.println(s.getInetAddress().getHostAddress() + ":" + s.getPort() + "," + s.getLocalPort());
			haoIp.put(i++ + "", s.getInetAddress().getHostAddress() + ":" + s.getPort() + "," + s.getLocalPort());
			OutputStream out = s.getOutputStream();
			InputStream in = s.getInputStream();
			new ReadThread(in, out).start();
			new WriteThread(out).start();
			
		}
	}
	
public class ReadThread extends Thread{
		
		public InputStream in = null;
		OutputStream out;

		public ReadThread(InputStream in, OutputStream out) {
			super();
			this.in = in;
			this.out = out;
		}
		
		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			BufferedInputStream bin = new BufferedInputStream(dataIn);
			String flag = null;
			String fileName = null;
			try {
				while(true) {
					//1.两次flush,会读两次
					if("img".equals(flag)) {
						byte[] g = new byte[1024];
						int l = 0;
						File file = new File("D:\\socket\\" + fileName);
						if(!file.exists()) {
							file.createNewFile();
						}
						System.out.println("create file:" + file.getAbsolutePath());
						FileOutputStream inS = new FileOutputStream(file);
						
						while((l = bin.read(g)) > 0) {//缓冲, 读流的数量上变化
							inS.write(g, 0, l);
							System.out.println("read in" + bin.available() + "--" + l + "---"+ bin.available() + "---" );//管道方式
							if(bin.available() == 0) {
//								System.out.println(new String(g));
								break;
							}
						}
						
//						String line = dataIn.readUTF();
//						System.out.println(line);
						inS.flush();
						inS.close();
						System.out.println("file flush ok.");
						flag = null;
						fileName = null;
					}else {
						String line = dataIn.readUTF();
						if(line.startsWith("start_img*_*")) {
							flag = "img";
							fileName = line.substring(12);
						}
						System.out.println("client:" + line);
						DataOutputStream outData = new DataOutputStream(out);
						outData.writeUTF(haoIp.get(line) == null ? "default" : haoIp.get(line));
						outData.flush();
					}
					
				}
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
	
}
