package com.tools.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 提取出来专门测试
 *
 *@author lishaoping
 *ToolsTest
 *2017年9月23日
 */
public class BlockingQueueTest {
	/**
	 * 并发读没有问题
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月22日
	 * @throws InterruptedException
	 */
	public static void test() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		for(int i = 0; i < 10000; i++) {
			queue.put(i+ "");
		}
		final List<String> list = new ArrayList<String>();
		//1.并发读
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						String x = queue.take();
						System.out.print(x + ",");
//						list.add(x);
//						System.out.println("read:" + Thread.currentThread().getName() + "-----------" + x);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		//证明list并发插入会有问题，会取出空元素,即会丢掉一些元素
//		Thread.currentThread().sleep(5000);
////		Collections.sort(list); //有空元素
//		System.out.println("-------------" + list);
//		int c = 0;
//		for(int i = 0; i< 10000; i ++) {
//			if(!list.contains(i + "")) {
//				c++;
//				System.out.println(i);
//			}
//		}
//		System.out.println(c);
	}
	
	/**
	 * 并发写也没有问题
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月22日
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.并发写
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {//1万个任务
			final int  j = i;
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						queue.put(j + "");
//						System.out.println("write:" + Thread.currentThread().getName() + "-----------" + j);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
//
		final Set<String> list = new HashSet<String>();
		Thread.currentThread().sleep(5000);
//		Collections.sort(list); //有空元素
//		System.out.println("-------------" + list);
		System.out.println(queue.size());
		int c = 0;
		int s = queue.size();
		for(int i = 0; i< s; i ++) {
			String x = queue.take();
				
				if(list.contains(x)) {
					System.out.println("---------------");
				}
				list.add(x);
				System.out.print(x + ",");
			if(x == null) {
				System.out.println("error" + x);
			}
		}
		System.out.println(list.size());	
		System.out.println(list);
	}
	
	/**
	 * 并发读和写，不会有null,也没有重复问题
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月22日
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.并发写和读
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {
			final int  j = i;
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						queue.put(j + "");
						System.out.print("*" + j);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						System.out.print("&" + queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
}
