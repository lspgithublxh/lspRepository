package com.bj58.fang.servercluster.ServerCluTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
//        
//        Socket s = new Socket();
//        try {
//			s.connect(new InetSocketAddress("10.233.22.22", 101), 2000);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("lianbushang");
//		}
//        System.out.println("ok");
    	new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					Map<Thread, StackTraceElement[]> ee = Thread.getAllStackTraces();
					for(Entry<Thread, StackTraceElement[]> en : ee.entrySet()) {
						Thread th = en.getKey();
						StackTraceElement[] v = en.getValue();
						System.out.println("-------------------------------------");
//						if(th.getName().startsWith("Thread")) {
//							System.out.println("thread name:" + th.getName() + " state:" + th.getState().name() + "  ----:");
//						}
						System.out.println("thread name:" + th.getName() + " state:" + th.getState().name() + "  ----:");
						if(v != null) {
							for(StackTraceElement el : v) {//+ " class:" + el.getClassName() 
								System.out.println(" at:" + el.getClassName()  + " on " +  el.getMethodName() + "() line:" + el.getLineNumber());
							}
							
						}
						System.out.println("-------------------------------------");

//						if(v != null && v.length > 0) {
////							System.out.println(v[0].toString());
//							if(th.getName().startsWith("Thread")) {
//								System.out.println(v[0].getMethodName()+ " class:" + v[0].getClassName() + " file:" + v[0].getFileName() + " line:" + v[0].getLineNumber());
//							}
//							if(v[0].getClassName().equals("java.net.InetSocketAddress")) {
////								th.stop();
//								th.interrupt();
//							}
//						}
//						if(th.getState().name().equals("WAITING")) {
//							th.interrupt();
//						}
						
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
    		
    	}).start();
        try {
        	//阻塞跟踪：在本地方法上已经阻塞
        	//    private native Field[]       getDeclaredFields0(boolean publicOnly);

			final Socket ss = new Socket("10.233.22.22", 101);//会卡死；；；在另一个线程里 获取主线程，杀死
			System.out.println("guo");
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						DataInputStream ins = new DataInputStream(ss.getInputStream());
						System.out.println("heheh");
						System.out.println("  " + ins.readUTF());;
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("end");
						
					}
				}
			}).start();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						DataOutputStream out = new DataOutputStream(ss.getOutputStream());
						System.out.println("hehhexx");
						out.writeUTF("hahahaha");
						System.out.println("hehhexx2");
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("end");
						
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ssssbushang");
		}
    }
}
