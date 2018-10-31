package com.bj58.fang.thread;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 数千个线程 的 同步 会耗费很多内存??
 * @ClassName:StringSyncronized
 * @Description:
 * @Author lishaoping
 * @Date 2018年10月17日
 * @Version V1.0
 * @Package com.bj58.fang.thread
 */
public class StringSyncronized {

	public static void main(String[] args) {
		Thread th1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ss = "谁";
				JSONObject o = new JSONObject();
				o.put("rs", "abc");
				ss = ss + o.getString("rs");
				synchronized (ss.intern()) {
					try {
						Thread.sleep(3000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
			}
		});
		Thread th2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						String ss = "谁";
						JSONObject o = new JSONObject();
						o.put("rs", "abc");
						ss = ss + o.getString("rs");
						synchronized (ss.intern()) {
							try {
								Thread.sleep(3000);
								System.out.println(Thread.currentThread().getName());
							} catch (InterruptedException e) {
								e.printStackTrace();
							};
						}
					}
				});
		Thread th3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ss = "谁";
				JSONObject o = new JSONObject();
				o.put("rs", "abc");
				ss = ss + o.getString("rs");
				synchronized (ss) {
					try {
						Thread.sleep(3000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
			}
		});
		Thread th4 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ss = "谁";
				JSONObject o = new JSONObject();
				o.put("rs", "abc");
				ss = ss + o.getString("rs");
				synchronized (ss.intern()) {
					try {
						Thread.sleep(3000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
			}
		});
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		
		Thread th5 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				String ss = "谁啊";
				synchronized (ss.intern()) {
					try {
						Thread.sleep(3000);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					};
				}
			}
		});
		th5.start();
		
		List<Thread> xx = new ArrayList<Thread>();
		for(int i = 0; i < 30000; i++) {
			Thread x = new Thread(new Runnable() {
				
				@Override
				public void run() {
//					String ss = "谁";
//					try {
//						Thread.sleep(3000);
//						System.out.println(Thread.currentThread().getName());
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					};
					JSONObject o = new JSONObject();
					o.put("rs", "abc");
					String ss = "谁";
					ss += o.getString("rs");
					synchronized (ss.intern()) {//占内存没有增加，但是不能退出会占据大量内存---但是和线程本身有关；；；
						try {
							Thread.sleep(3000);
							System.out.println(Thread.currentThread().getName());
						} catch (InterruptedException e) {
							e.printStackTrace();
						};
					}
				}
			});
			xx.add(x);
		}
		for(Thread t : xx) {
			t.start();
		}
	}
}
