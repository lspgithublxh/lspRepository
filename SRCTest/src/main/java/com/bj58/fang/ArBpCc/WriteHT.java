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
	
	ARC arc = ARC.getInstance();
	AS1 as1 = AS1.getInstance();
	AC2 ac1 = AC2.getInstance();
	
	public WriteHT config(String message) {
		this.content = message;
		return this;
	}
	
	public WriteHT config(Map<String, Object> context) {
//		String name = obj.getClass().getSimpleName();
//		if("ARC".equals(name)) {
//			arc = (ARC) obj;
//		}else if("AS1".equals(name)) {
//			as1 = (AS1) obj;
//		}else if("AC2".equals(name)) {
//			ac1 = (AC2) obj;
//		}
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
	
	public ARC getArc() {
		return arc;
	}

	public ARC setArc(ARC arc) {
		this.arc = arc;
		return arc;
	}

	public AS1 getAs1() {
		return as1;
	}

	public AS1 setAs1(AS1 as1) {
		this.as1 = as1;
		return as1;
	}

	public AC2 getAc1() {
		return ac1;
	}

	public AC2 setAc1(AC2 ac1) {
		this.ac1 = ac1;
		return ac1;
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
		case 3://server有关的写  注册一个服务
			int status3 = writeStr(content);
			as1.writeHandle(out, comSoc, 1, this);
			break;
		case 4://client有关的写
			ac1.writeHandle(out, comSoc, 1, this);
			break;
		case 5://client有关的写
			ac1.writeHandle(out, comSoc, 2, this);
			break;
		case 6://server有关的写
			byte[] data3 = (byte[]) this.context.get("data");
			String status2 = this.context.get("status").toString();
			String mes = this.context.get("message").toString();
			writeStr(String.format("callback:|%s|%s|%s", status2, mes, data3.length));
			byte[] data = data3;
			for(int i = 0; i < data.length; i+= 1024) {
				int end = i + 1024;
				byte[] buf = new byte[1024];
				if(i + 1024 > data.length) {
					end = data.length;
				}
				for(int j = i; j < end; j++) {
					buf[j - i] = data[j];
				}
				writeArray(buf, 0, 1024);//发送多余的一些，
			}
			System.out.println("server write back ok");
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
