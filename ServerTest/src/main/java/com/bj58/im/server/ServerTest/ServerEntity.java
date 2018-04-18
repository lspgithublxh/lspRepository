package com.bj58.im.server.ServerTest;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ServerEntity {

	public static void main(String[] args) throws IOException {
		new ServerEntity().startServer();
	}
	
	public void startServer() throws IOException {
		ServerSocket socket = new ServerSocket(10000);
		System.out.println("start listen at 10000");
		while(true) {
			Socket s = socket.accept();
			OutputStream out = s.getOutputStream();
			InputStream in = s.getInputStream();
			new ReadThread(in).start();
			new WriteThread(out).start();
			
		}
	}
	
	public class ReadThread extends Thread{
		
		public InputStream in = null;

		public ReadThread(InputStream in) {
			super();
			this.in = in;
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
