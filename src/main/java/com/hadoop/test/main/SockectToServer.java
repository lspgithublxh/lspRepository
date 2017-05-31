/**
 * 
 */
package com.hadoop.test.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socket吧编程，两点：协议要求：
 * 1.客户端先用输出流发送数据后先关闭输出流再到输入流读出数据，所以服务端首先要从输入流中读出数据，然后先关闭输入流再到输出流写入数据
 * 2.一次读完用DataInputStream的readUTF()方法，一次写完用DataOutputStream的writeUTF()方法.。。且利用读写线程分离，可以交互，即一遍读一遍写。。这个是关键不同，即是异步的而不是同步的
 * (这样就不用像1.中一样来回切换了)
 * @author lishaoping
 * 2017年5月31日下午7:33:30
 * SockectToServer
 */
public class SockectToServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			Socket socket = server.accept();
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			DataInputStream inp = new DataInputStream(in);
			DataOutputStream outp = new DataOutputStream(out);
			String line = "";
			while((line = inp.readUTF()) != null){
				System.out.println(" server read: " + line);
				outp.writeUTF("hello world!");
			}
			
			
//			out.write("hello world".getBytes());
//			out.flush();
//			System.out.println("connect :" + System.currentTimeMillis());
//			socket.shutdownInput();
//			while(true){
				
//				OutputStream out = socket.getOutputStream();
//				out.write("hello world".getBytes());
//				out.flush();
//				System.out.println("connect :" + System.currentTimeMillis());
//				InputStream in = socket.getInputStream();
//				BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
//				String line = null;
//				if((line = buffer.readLine()) != null){
//					System.out.println(line);
//				}
//				Thread.currentThread().sleep(5000);
//			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
