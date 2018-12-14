package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.bj58.fang.dynamicClass.CBInterface;
import com.bj58.fang.dynamicClass.ProxySub;

/**
 * 真正handle的方法
 * @ClassName:GeneralCallBackFun
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月12日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class GeneralCallBackFun implements CBInterface{

	private String interName = null;//比如 serName/服务名下接口 或者 具体实现类，这里隐藏这个实现类
	private String serviceName = null;
	
	public GeneralCallBackFun(String serviceName, String interName) {
		this.interName = interName;
		this.serviceName = serviceName;
	}

	@Override
	public Object callback(Object instance, ProxySub proxy, Method dynamicMethod, Method superMethod, Object... args) {
		//1.连接服务器
		System.out.println("to service ");
		SDEntity service = AC2.getInstance().getService(serviceName);
		String[] iop = service.getIop();
		for(String ip : iop) {
			String[] ippo = ip.split("\\:");
			try {
				Socket socket = new Socket(ippo[0], Integer.valueOf(ippo[1]));
				//参数序列化
				StringBuilder params = new StringBuilder();
				for(Object o : args) {//序列化--toString()方法
					params.append(o.toString());
					params.append(",");
				}
				int len = params.length() > 0 ? params.length() - 1 : 0;
				String methodName = superMethod.toGenericString();
				String request = String.format("methodCall:|%s|%s|%s", interName, methodName, len);
				String para = params.substring(0, params.length() - 1);
				//send to 发送两次
				Object lock = new Object();
				Map<String, Object> context = new HashMap<>();
				context.put("request", request);
				context.put("para", para);
				context.put("mainLock", lock);
				new WriteHT(socket.getOutputStream(), 5, socket).config(context).start();
				new ReadHT(socket.getInputStream(), 1, socket).config(context).start();
				synchronized (lock) {
					lock.wait();
				}
				Object status = context.get("status");
				if("200".equals(status)) {
					return context.get("data");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//2.
		System.out.println("get Data failed!!");
		return null;
	}

	
}
