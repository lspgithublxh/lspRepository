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
 * socket�ɱ�̣����㣺Э��Ҫ��
 * 1.�ͻ�������������������ݺ��ȹر�������ٵ��������������ݣ����Է��������Ҫ���������ж������ݣ�Ȼ���ȹر��������ٵ������д������
 * 2.һ�ζ�����DataInputStream��readUTF()������һ��д����DataOutputStream��writeUTF()����.���������ö�д�̷߳��룬���Խ�������һ���һ��д��������ǹؼ���ͬ�������첽�Ķ�����ͬ����
 * (�����Ͳ�����1.��һ�������л���)
 * 3.ͬ�����⣬�ҷ����͵�ʱ�򣬱���ȷ���������ڼ��Ѿ�׼�����˶���
 * @author lishaoping
 * 2017��5��31������7:33:30
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
	 * 2017��5��31������9:58:17
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
					outp.writeUTF("hello world");//дʱ�Է�Ӧ��׼�����ˡ�
					outp.flush();//�ز�����
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
	 * 2017��5��31������9:53:48
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
