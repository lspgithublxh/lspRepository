package com.bj58.im.client.ClientTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ClientEntity {

	public static void main(String[] args) throws UnknownHostException, IOException {
		new ClientEntity().startClient();
	}
	
	public void startClient() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		new ReadThread(in).start();
		new WriteThread(out).start();
		
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
			
				try {
					while(true) {
						String line = dataIn.readUTF();
						System.out.println("server:" + line);
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
				
				if("1".equals(line)) {
					//搜索D:下的一幅图片，传给另一端
					line = transFile(outData);
				}else if("2".equals(line)) {
					//搜索D:下的文件一个，传给另一端
					
				}
				try {
					if(line != null) {
						outData.writeUTF(line);
						outData.flush();
						System.out.println("client:" + line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private String transFile(DataOutputStream outData) {
			String line;
			String file = "D:\\";
			File file2 = new File(file);
			List<String> result = new LinkedList<String>();
			try {
				Files.walkFileTree(Paths.get(file), new FindJavaVisitor(result));
			}catch (java.nio.file.AccessDeniedException e) {
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			//顺便传递一张图
			File transFile = new File(result.get(0));
			if(transFile.exists()) {
				byte[] b = new byte[1024];
				try {
					FileInputStream inS = new FileInputStream(transFile);
					int l = 0;
					//先发文本
					outData.writeUTF(result.subList(0, 100).toString());
					outData.flush();
					//再发图片
					outData.writeUTF("start_img*_*" + result.get(0).substring(result.get(0).lastIndexOf("\\") + 1));
					outData.flush();
					System.out.println("trans file:");
					while((l = inS.read(b)) > 0) {
						outData.write(b, 0, l);
					}
//					outData.writeUTF("hello");
					outData.flush();
					System.out.println("trans file ok");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			return null;
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
