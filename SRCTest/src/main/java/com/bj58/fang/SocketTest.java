package com.bj58.fang;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		test();
	}

	private static void test() throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.183.33", 27080);
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();
		out.write("{\"sessionId\":1 ,\"serviceName\":\"unitycmc\" ,\"scfKey\":\"ONzGoMsGPSDb2hj0xMubIK1kIXCXyxEa\" ,\"configTime\":0 }".getBytes());
		socket.shutdownOutput();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(in)));
		System.out.println(reader.readLine());
		socket.close();
		
	}
}
