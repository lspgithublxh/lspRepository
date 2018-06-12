package com.bj58.im.client.ClientTest.BaseClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server_P {
	public static void main(String[] args) throws IOException {
		new Server_P().startServer();
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
			
				try {
					while(true) {
						String line = dataIn.readUTF();
						System.out.println("client:" + line);
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
