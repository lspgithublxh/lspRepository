package com.bj58.fang.ArBpCc;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ReadHT extends Thread{

	AC2 ac1 = null;
	AS1 as1 = null;
	ARC arc = null;
	
	int type = 1;//1.server, 2.client, 3.regcenter
	InputStream in = null;
	DataInputStream input = null;
	Socket comSoc = null;
	Map<String, Object> context = new HashMap<>();
	
	public ReadHT config(Map<String, Object> context) {
		this.context = context;
		return this;
	}
	
	public ReadHT(InputStream in, int type, Socket comSoc) {
		super();
		this.in = in;
		input = new DataInputStream(in);
		this.type = type;
		this.comSoc = comSoc;
	}
	
	public ByteArrayOutputStream readData(int dataLength) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int rlen = 1024;
		byte[] b = new byte[rlen];
		int alen = 0;
		int oneLen = 1024;
		try {
			boolean end = false;
			while (true) {
				input.readFully(b);
				if(alen + rlen > dataLength) {
					oneLen = dataLength - alen;
					end = true;
				}else {
					oneLen = rlen;
				}
				alen += rlen;
				out.write(b, 0, oneLen);
				if(end) {
					break;
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	@Override
	public void run() {
		try {
			switch (type) {
			case 3:
				//1.阅读标记位
				String line = input.readUTF();
				//可以改为只读1024个字节的内容----或者加入换行
				if(line.startsWith("reg:")) {//注册服务--服务名--机器ip-port列表--请求url路径
					String[] info = line.split("\\|");
					String serName = info[1];
					String serUrl = info[2];
					String length = info[3];
					ByteArrayOutputStream out = readData(Integer.valueOf(length));
					String ipPorts = out.toString();
					String[] ipport = ipPorts.split(";");
					SDEntity entity = new SDEntity(serName, serUrl, ipport);
					entity.setRegTime(System.currentTimeMillis());
					arc.info.put(serName, entity);
					arc.messageHandle(serName, comSoc, 2);
				}else if(line.startsWith("update:")) {//删除服务--管理员才接受处理
					String[] info = line.split("\\|");
					String serName = info[1];
					String serUrl = info[2];
					String length = info[3];
					ByteArrayOutputStream out = readData(Integer.valueOf(length));
					String ipPorts = out.toString();
					String[] ipport = ipPorts.split(";");
					if(arc.info.containsKey(serName)) {
						SDEntity entity = arc.info.get(serName);
						entity.setIop(ipport);
						entity.setLastUpdateTime(System.currentTimeMillis());
					}else {
						SDEntity entity = new SDEntity(serName, serUrl, ipport);
						entity.setRegTime(System.currentTimeMillis());
						arc.info.put(serName, entity);
					}
					arc.messageHandle(serName, comSoc, 3);
				}else if(line.startsWith("req:")) {//请求获取--可以自己的ipport也发送过来 
					//发送回去
					String[] info = line.split("\\|");
					String serName = info[1];
					arc.messageHandle(serName, comSoc, 1);//请求服务
				}else if(line.startsWith("sdel:")) {//删除服务--管理员才接受处理
					
				}
				break;
			case 1://client的读，分2种  一是服务信息，二是调服务返回的数据
				//1.阅读标记位
				String line2 = input.readUTF();
				if(line2.startsWith("serv")) {
					String[] info = line2.split("\\|");
					String serName = info[1];
					String serUrl = info[2];
					String length = info[3];
					ByteArrayOutputStream out = readData(Integer.valueOf(length));
					String ipPorts = out.toString();
					String[] ipport = ipPorts.split(";");
					SDEntity entity = new SDEntity(serName, serUrl, ipport);
					this.context.put(serName, entity);
					ac1.readHandle(in, comSoc, 1, this);
				}
				break;
			case 2:
				break;
			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AC2 getAc1() {
		return ac1;
	}

	public void setAc1(AC2 ac1) {
		this.ac1 = ac1;
	}

	public AS1 getAs1() {
		return as1;
	}

	public void setAs1(AS1 as1) {
		this.as1 = as1;
	}

	public ARC getArc() {
		return arc;
	}

	public void setArc(ARC arc) {
		this.arc = arc;
	}
	
}
