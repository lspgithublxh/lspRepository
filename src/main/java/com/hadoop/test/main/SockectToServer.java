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
 * socket�ɱ�̣����㣺Э��Ҫ��
 * 1.�ͻ�������������������ݺ��ȹر�������ٵ��������������ݣ����Է��������Ҫ���������ж������ݣ�Ȼ���ȹر��������ٵ������д������
 * 2.һ�ζ�����DataInputStream��readUTF()������һ��д����DataOutputStream��writeUTF()����.���������ö�д�̷߳��룬���Խ�������һ���һ��д��������ǹؼ���ͬ�������첽�Ķ�����ͬ����
 * (�����Ͳ�����1.��һ�������л���)
 * @author lishaoping
 * 2017��5��31������7:33:30
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
