package com.bj58.fang.Dis_MRTaskTest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WriteThread extends Thread{

	Socket socket;
	String mes;
	byte[] data;
	
	public WriteThread(Socket socket, String mes) {
		super();
		this.socket = socket;
		this.data = mes.getBytes();
	}
	
	public WriteThread(Socket socket, byte[] data) {
		super();
		this.socket = socket;
		this.data = data;
	}



	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public byte[] getData() {
		return data;
	}



	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public void run() {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			int step = 1024;
			int da_len = data.length;
			int start = 0;
			int end = 1024;
			//正确
			while(true) {
				//本地end
				if(start + 1 > da_len) {//读完
					break;
				}
				if(start + step > da_len) {
					end = da_len;//或许可以补全发送
				}else {
					end = start + step;
				}
				out.write(data, start, end);
				out.flush();
				start = end;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
