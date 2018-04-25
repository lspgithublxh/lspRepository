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

/**
 * 正确的模型，是对外提供一个被调用的写接口--接收写入本client---！！！对方有需要可以调用这个接口来写入；；同时接收一个外部的读接口，！！！网络有数据就调用他的接口传给他
 * @ClassName:ClientEntity
 * @Description:
 * @Author lishaoping
 * @Date 2018年4月24日
 * @Version V1.0
 * @Package com.bj58.im.client.ClientTest
 */
public class ClientEntity {

	public static void main(String[] args) throws UnknownHostException, IOException {
		new ClientEntity().startClient();
	}
	
	public WriteCallBack openAClientModel(ReadCallBack rc) throws IOException {
		Socket socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		
		new ReadThread(in, rc).start();
		WriteCallBack wc = new WriteCallBack() {
//			String line = null;
//			Object lock = new Object();
			@Override
			public void setLine(String input) {
				this.line = input;
				synchronized (lock) {
					lock.notify();
				}
				//如果再加一个锁，实现同步的通信,可以避免消息漏发---即后面的消息将前面的消息覆盖了。所以可能还需要用消息队列。。使用消息队列，那么可以避免这个漏发
			}

//			@Override
//			public String getLine() {
//				String line_c = line;
//				line = null;
//				return line_c;
//			}
//
//			@Override
//			public Object getLock() {
//				return lock;
//			}
			};
			
		new WriteThread(out, wc, wc.getLock()).start();
		return wc;
	}
	
	/**
	 * 此模型不对
	 * @param 
	 * @author lishaoping
	 * @Date 2018年4月24日
	 * @Package com.bj58.im.client.ClientTest
	 * @return void
	 */
//	public void openAClient(ReadCallBack rc, WriteCallBack wc) throws UnknownHostException, IOException {
//		Socket socket = new Socket("localhost", 10000);
//		System.out.println("start connect to localhost, 10000");
//		
//		OutputStream out = socket.getOutputStream();
//		InputStream in = socket.getInputStream();
//		
//		new ReadThread(in, rc).start();
//		new WriteThread(out, wc).start();
//	}
	
	public void startClient() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 10000);
		System.out.println("start connect to localhost, 10000");
		
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		new ReadThread(in, new ReadCallBack() {
			@Override
			public String callback(String input) {
//				System.out.println(input);
				return null;
			}})  .start();
		
		
		WriteCallBack wc = new WriteCallBack() {//写出抽象类可以避免锁的亲自实现----即模板模式
			String line = null;
			Object lock = new Object();
			@Override
			public void setLine(String input) {
				this.line = input;
				synchronized (lock) {
					lock.notify();
				}
			}

			@Override
			public String getLine() {
				String line_c = line;
				line = null;
				return line_c;
			}

			@Override
			public Object getLock() {
				return lock;
			}};
			
		new WriteThread(out, wc, wc.getLock()).start();
		//把主线程，当作调用线程
		while(true) {
			Scanner scanner = new Scanner(System.in);
			String line = scanner.nextLine();
			wc.setLine(line);
			
		}
	}
	
public class ReadThread extends Thread{
		
		public InputStream in = null;

		public ReadCallBack callback = null;
		
		public ReadThread(InputStream in, ReadCallBack callback) {
			super();
			this.in = in;
			this.callback = callback;
		}


		@Override
		public void run() {
			DataInputStream dataIn = new DataInputStream(in);
			
				try {
					while(true) {
						String line = dataIn.readUTF();
						System.out.println("server:" + line);
						callback.callback(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public class WriteThread extends Thread{
		
		OutputStream out;
		public WriteCallBack callback = null;
		Object lock;
		
		public WriteThread(OutputStream out, WriteCallBack callback, Object lock) {
			super();
			this.out = out;
			this.callback = callback;
			this.lock = lock;
		}
		
		@Override
		public void run() {
			DataOutputStream outData = new DataOutputStream(out);
			while(true) {
//				Scanner scanner = new Scanner(System.in);
//				String line = scanner.nextLine();
				try {
					synchronized (lock) {
						lock.wait();
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String line = callback.getLine();
				
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
