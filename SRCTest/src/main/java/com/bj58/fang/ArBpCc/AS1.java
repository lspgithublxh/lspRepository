package com.bj58.fang.ArBpCc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动侦听
 * 回调处理
 * 发送注册
 * --注册-请求-调用-返回为止
 * @ClassName:AS1
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月7日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class AS1 {

	
	private static AS1 inst = new AS1();
	public static AS1 getInstance() {
		return inst;
	}
	public static void main(String[] args) {
		
	}
	
	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月7日
	 * @Package com.bj58.fang.ArBpCc
	 * @return void
	 */
	public void start() {
		try {
			//1.起
			ServerSocket server = new ServerSocket(12334);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						try {
							System.out.println("AS1 wait to access!");
							Socket toclient = server.accept();
							new ReadHT(toclient.getInputStream(), 2, toclient).start();
							System.out.println("AS1 receive a access!");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			//2.发送 --注册
			Socket toreg = new Socket("localhost", 12444);
			new WriteHT(toreg.getOutputStream(), 3, toreg).config("reg:|aservice|aservice/AService|15").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void readHandle(InputStream in, Socket sock, int type, ReadHT reader) {
		switch (type) {
		case 1:
			//取参数，执行方法调用
			Object param = reader.context.get("para");
			Object inter = reader.context.get("interName");
			Object method = reader.context.get("methodName");
			Object data = methodExecute(inter, method, param);
			//写回数据
			Map<String, Object> context = new HashMap<>();
			context.put("data", data);
			context.put("status", "200");
			context.put("message", "");
			try {
				new WriteHT(sock.getOutputStream(), 6, sock).config(context).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	private Object methodExecute(Object inter, Object method, Object param) {
		System.out.println("service call success!! and read Param[] is : " + param);
		Object rs = null;
		if("AService".equals(inter.toString())) {
//			return method1(inter, method, param);
			Class<?> cls;
			try {
				cls = Class.forName("com.bj58.fang.ArBpCc." + inter.toString());
				Object instance = cls.newInstance();
				Method[] mes = cls.getDeclaredMethods();
				String namexx = method.toString();
				namexx = namexx.substring(namexx.lastIndexOf(".") + 1);
				for(Method m : mes) {
					String name2 = m.toGenericString();
					name2 = name2.substring(name2.lastIndexOf(".") + 1);
					if(namexx.equals(name2)) {
						boolean run = false;
						if(param != null) {
							Object[] params = (Object[]) param;
							if(params.length > 0) {
								rs = m.invoke(instance, params);
								run = true;
							}
						}
						if(!run) {
							rs = m.invoke(instance);
						}
						break;
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return rs;
	}
	private Object method1(Object inter, Object method, Object param) {
		try {
			Class<?> cls = Class.forName("com.bj58.fang.ArBpCc." + inter.toString());
			boolean run = false;
			Object instance = cls.newInstance();
			if(param != null) {
				Object[] params = (Object[]) param;
				if(params.length > 0) {
					Class<?>[] types = new Class<?>[params.length];
					int i = 0;
					for(Object pa : params) {
						types[i++] = pa.getClass();
					}
					Method me = cls.getMethod(method.toString(), types);
					Object rs = me.invoke(instance, params);
					run = true;
					return rs;
				}
			}
			if(!run) {
				Method me = cls.getMethod(method.toString());
				Object rs = me.invoke(instance);
				return rs;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void writeHandle(OutputStream out, Socket comSoc, int status, WriteHT writeHT) {
		switch (status) {
		case 1:
			//再写ip
			byte[] front = new byte[1024];
			byte[] content = "localhost:12334".getBytes();
			for(int i = 0; i < content.length; i++) {//都是正数
				front[i] = content[i];
			}
			for(int i = content.length; i < 1024; i++) {
				front[i] = 0;
			}
			writeHT.writeArray(front, 0, front.length);
			break;
		default:
			break;
		}
		System.out.println("server write callback");
	}
}
