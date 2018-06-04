package com.bj58.im.client.ClientTest.P2P;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.bj58.im.client.ClientTest.ReadCallBack;

public class ClientEntity {

	public static void main(String[] args) {
		try {
			new ClientEntity(19999).test();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	int serverPort;

	public ClientEntity(int serverPort) {
		super();
		this.serverPort = serverPort;
	}



	private void test() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", serverPort);
		System.out.println("start connect to localhost, 19999 ;current port:" + socket.getLocalPort());
		socket.setReuseAddress(true);
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		
		new ReadThread(in).start();
		new WriteThread(out).start();
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
			try {
				outData.writeUTF("hello");
				outData.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String line = scanner.nextLine();
				
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
	
	String targetIpport = "";

public class ReadThread extends Thread{
		
		public InputStream in = null;

		public ReadCallBack callback = null;
		
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
						if(line.startsWith("127.0.0.1:")) {
							System.out.println("start client to client: " + line);
							new ClientEntity(Integer.valueOf(line.substring(line.indexOf(":") + 1))).test();
						}
//						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
