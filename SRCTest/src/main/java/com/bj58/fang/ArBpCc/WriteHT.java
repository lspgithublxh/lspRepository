package com.bj58.fang.ArBpCc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 自顶向下抽象描述
 * @ClassName:WriteHT
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月7日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class WriteHT  extends Thread{

	OutputStream out = null;
	DataOutputStream output = null;
	Socket comSoc = null;
	int type = 1;//1.
	byte[] data = null;
	String content = null;
	Map<String, Object> context = new HashMap<>();
	private int off;
	private int len;
	
	ARC arc = null;
	AS1 as1 = null;
	AC2 ac1 = null;
	
	public WriteHT config(String message) {
		this.content = message;
		return this;
	}
	
	public WriteHT config(Map<String, Object> context) {
		this.context = context;
		return this;
	}
	
	public WriteHT config2(byte[] data, int off, int len) {
		this.data = data;
		this.off = off;
		this.len = len;
		return this;
	}
	
	public WriteHT(OutputStream out, int type, Socket comSoc) {
		super();
		this.out = out;
		output = new DataOutputStream(out);
		this.type = type;
		this.comSoc = comSoc;
	}
	
	public int writeStr(String text){
		try {
			output.writeUTF(text);
			output.flush();
			System.out.println("write str ok:" + text);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public int writeArray(byte[] arr, int off, int len) {
		try {
			output.write(arr, off, len);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	@Override
	public void run() {
		switch (type) {
		case 1://arc有关的写
			int status = writeStr(content);
			arc.writeOkHandle(status, out, comSoc, 1);
			break;
		case 2://arc有关的写
			writeArray(data, off, len);
			break;
		case 3://server有关的写
			break;
		case 4://client有关的写
			ac1.writeHandle(out, comSoc, 1, this);
			break;
		default:
			break;
		}
//		try {
//			Scanner reader = new Scanner(System.in);
//			while (reader.hasNext()) {
//				String line = reader.nextLine();
//				writeStr(line);
//			} 
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
	}
}
