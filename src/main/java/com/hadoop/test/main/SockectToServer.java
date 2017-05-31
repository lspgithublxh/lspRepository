/**
 * 
 */
package com.hadoop.test.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * socket吧编程，两点：协议要求：
 * 1.客户端先用输出流发送数据后先关闭输出流再到输入流读出数据，所以服务端首先要从输入流中读出数据，然后先关闭输入流再到输出流写入数据
 * 2.一次读完用DataInputStream的readUTF()方法，一次写完用DataOutputStream的writeUTF()方法.。。且利用读写线程分离，可以交互，即一遍读一遍写。。这个是关键不同，即是异步的而不是同步的
 * (这样就不用像1.中一样来回切换了)
 * 3.同步问题，我方发送的时候，必须确保他方正在即已经准备好了读。
 * @author lishaoping
 * 2017年5月31日下午7:33:30
 * SockectToServer
 */
public class SockectToServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			
			while(true){
				Socket socket = server.accept();
				socketMethod(socket);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author lishaoping
	 * 2017年5月31日下午9:58:17
	 *SockectToServer.java
	 * @param socket
	 * @throws IOException
	 */
	private static void socketMethod(final Socket socket) throws IOException {
		new Thread(){
			@Override
			public void run() {
				super.run();
				InputStream in;
				try {
					in = socket.getInputStream();
					OutputStream out = socket.getOutputStream();
					DataInputStream inp = new DataInputStream(in);
					DataOutputStream outp = new DataOutputStream(out);
//							clientSendFirst(inp, outp);
					read(inp);
					outp.writeUTF("hello world");//写时对方应当准备好了。
					outp.flush();//必不可少
					Scanner scan = new Scanner(System.in);
					String line = null;
					while((line = scan.nextLine()) != null){
						outp.writeUTF(line);
						outp.flush();
						System.out.println("write ok");
					}
//						outp.write("helloWorld".getBytes());
//						outp.flush();
						
//						currentThread().sleep(1000);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
	}

	private static void read(final DataInputStream inp){
		new Thread(){
			@Override
			public void run() {
				super.run();
				String line = null;
				try {
					while((line = inp.readUTF()) != null){
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/**
	 * @author lishaoping
	 * 2017年5月31日下午9:53:48
	 *SockectToServer.java
	 * @param inp
	 * @param outp
	 * @throws IOException
	 */
	private static void clientSendFirst(DataInputStream inp, DataOutputStream outp) throws IOException {
		String line = "";
		while((line = inp.readUTF()) != null){
			System.out.println(" server read: " + line);
			outp.writeUTF("hello world!");
		}
	}
}
