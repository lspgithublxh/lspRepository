/**
 * 
 */
package com.hadoop.test.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author lishaoping
 * 2017年5月31日下午9:09:59
 * ClientSocket
 */
public class ClientSocket {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 9999);
			
			OutputStream out = socket.getOutputStream();
			
			InputStream in = socket.getInputStream();
			
			DataInputStream inp = new DataInputStream(in);
			DataOutputStream outp = new DataOutputStream(out);
			read(inp);
			String line = "";
			Scanner scan = new Scanner(System.in);
			while((line = scan.nextLine()) != null){
				outp.writeUTF(line);
			}
	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
}
