package com.bj58.fang.Dis_MRTaskTest;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadThread extends Thread{

	Socket socket;
	byte[] data;
	String mes;
	
	Object lock = new Object();
	
	public ReadThread(Socket socket) {
		super();
		this.socket = socket;
	}

	public byte[] getData() {
		if(data == null) {
			synchronized (lock) {
				if(data == null) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return data;
	}

	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int curr = 0;
			int step = 1024;//开始第一轮阅读
			int totalLen = 0;
//			int start = 0;
			byte[] cache = new byte[1024];
			boolean startRead = true;
			while(true) {
				in.readFully(cache, 0, step);
				//首次
				if(startRead) {
					startRead = false;
					//转为string, 读取长度和类型
					String mes = new String(cache);//是1024长度
					this.mes = mes;
					if(mes.startsWith("start|")) {//start|type|len|...
						String[] da = mes.split("\\|");
						String type = da[1];
						String info_len = da[2];
						totalLen = Integer.valueOf(info_len);
					}else {
						//数据 处理
						out.write(cache, 0, step);
						//是否下一次继续阅读
						if(step < 1024) {
							break;
						}
						curr += step;
					}
					//计算step
					if(totalLen > curr + step) {//还有数据
						step = 1024;
					}else {
						step = totalLen - curr;
					}
					if(step == 0) {//刚好整数，读完退出
						break;
					}
				}
			}
			data = out.toByteArray();//转换为数据
			synchronized (lock) {
				lock.notifyAll();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
