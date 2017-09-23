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
 * ��ȡ����ר�Ų���
 *
 *@author lishaoping
 *ToolsTest
 *2017��9��23��
 */
public class BlockingQueueTest {
	/**
	 * ������û������
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		for(int i = 0; i < 10000; i++) {
			queue.put(i+ "");
		}
		final List<String> list = new ArrayList<String>();
		//1.������
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
		//֤��list��������������⣬��ȡ����Ԫ��,���ᶪ��һЩԪ��
//		Thread.currentThread().sleep(5000);
////		Collections.sort(list); //�п�Ԫ��
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
	 * ����дҲû������
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.����д
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {//1�������
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
//		Collections.sort(list); //�п�Ԫ��
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
	 * ��������д��������null,Ҳû���ظ�����
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.����д�Ͷ�
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
