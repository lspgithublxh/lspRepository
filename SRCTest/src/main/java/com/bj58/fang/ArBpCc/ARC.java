package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册、请求、调用三对基本调用
 * 
 * @ClassName:RegCenter
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月7日
 * @Version V1.0
 * @Package com.bj58.fang.rpc
 */
public class ARC {

	Map<String, SDEntity> info = new ConcurrentHashMap<>();
	
	/**
	 * 听地址：12444
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月7日
	 * @Package com.bj58.fang.ArBpCc
	 * @return void
	 */
	public void start() {
		try {
			ServerSocket server = new ServerSocket(12444);
			while(true) {
				System.out.println("wait to access");
				Socket client = server.accept();
				System.out.println("received a request:");
				new ReadHT(client.getInputStream(), 3, client).start();
//				new WriteHT(client.getOutputStream(), type, comSoc)//不能直接写，要根据读的结果来写；；主动调用的专门再看
				//写线程，主动去查各个服务是否还能联系
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取之后的回调方法
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月8日
	 * @Package com.bj58.fang.ArBpCc
	 * @return void
	 */
	public void messageHandle(String serName, Socket comSoc, int type) {
		switch (type) {
		case 1://客户端请求服务
			//直接返回给他们--新开一个写线程来实现
			try {
				System.out.println("request a service , send to him:" + serName);
				
				SDEntity service = info.get(serName);
				String[] content = generateServiceEntity(service, 1);
				WriteHT writer = new WriteHT(comSoc.getOutputStream(), 1, comSoc);
				writer.writeStr(content[0]);
				writer.writeStr(content[1]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2://reg
			System.out.println("reg a service" + serName + " at " + System.currentTimeMillis());
			break;
		case 3://update
			System.out.println("update a service" + serName + " at " + System.currentTimeMillis());
			break;
		default:
			break;
		}
	}

	/**
	 * tag|servName|servUrl|length
	 * ip1:port1;ip2:port2:ip3:port3;....
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月8日
	 * @Package com.bj58.fang.ArBpCc
	 * @return String
	 */
	private String[] generateServiceEntity(SDEntity service, int type) {
		String[] ipport = service.getIop();
		String second = "";
		for(String con : ipport) {
			second += con;
		}
		String first = "";
		switch (type) {
		case 1:
			first = String.format("serv|%s|%s|%s", service.getName(), service.getUrl(), second.length());
			break;
		default:
			break;
		}
		return new String[] {first, second};
	}

	public Map<String, SDEntity> getInfo() {
		return info;
	}

	public void setInfo(Map<String, SDEntity> info) {
		this.info = info;
	}

	/**
	 * 写完之后的回调方法
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月8日
	 * @Package com.bj58.fang.ArBpCc
	 * @return void
	 */
	public void writeOkHandle(int status, OutputStream out, Socket comSoc, int type) {
		//专门处理
		switch (type) {
		case 1:
			if(1 == status) {
				System.out.println("arc known write ok");
			}
			break;

		default:
			break;
		}
	}
}
