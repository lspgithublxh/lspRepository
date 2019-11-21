package com.li.shao.ping.KeyListBase.datastructure.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用数组实现一个队列
 * 两个指针：首s 尾w 
 *   s + 1 = w 将空。即弹出一个元素后，设置状态为空。
 *   w + 1 = s 将满。即增加一个元素后，设置为满状态。
 *   
 *   进一步disruptor
 * TODO 
 * @author lishaoping
 * @date 2019年11月21日
 * @file ArrayQueueUtil
 */
public class ArrayQueueUtil {

	private int capacity = 100;
	private Object[] arr = new Object[capacity];
	private int start = 0;
	private int end = 0;
	public volatile int size = 0;//不用volatile那么size的改变不会被另一个线程感知，导致的问题如：一直put很多次,却size==0读不出来；即便同步块中也没用
	
	
	public ArrayQueueUtil(int capacity) {
		this.capacity = capacity;
	}

	public <T>  boolean put(T data) {
		if(size == capacity) {
			return false;
		}
		synchronized (this) {//避免指令重排,避免多线程put后面的覆盖前面的
			arr[end] = data;
			end = (end + 1) % capacity;
			size++;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get() {
		if(size == 0) {
			return null;
		}
		Object data = null;
		synchronized (this) {//避免指令重排  避免多线程读重复消费
			data = arr[start];
			start = (start + 1) % capacity;
			size--;
		}
		return (T)data;
	}
	
	public static void main(String[] args) {
		ArrayQueueUtil arrayQueueUtil = new ArrayQueueUtil(100);
//		for(int i = 0; i < 102; i++) {
//			boolean s = arrayQueueUtil.put(i);
//			System.out.println(s + " put " + i + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
//
//		}
//		for(int c = 0; c < 100; c++) {
//			Object i = arrayQueueUtil.get();
//			System.out.println("get " + i + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
//		}
//		Thread thread1 = a(arrayQueueUtil);
		multiple(arrayQueueUtil);
		
	}

	private static void multiple(ArrayQueueUtil arrayQueueUtil) {
		AtomicInteger i =  new AtomicInteger(0);
		AtomicInteger putCount =  new AtomicInteger(0);
		AtomicInteger getCount =  new AtomicInteger(0);

		for(int j = 0; j < 5; j++) {
			Thread thread1 = new Thread(()->{
				
				try {
					while(true) {
						putCount.getAndIncrement();
						boolean s = arrayQueueUtil.put(i.incrementAndGet());
						System.out.println(Thread.currentThread().getName() + "," + s + " put " + i.get() + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
						Thread.sleep(10);
						if(putCount.get() == 1000) {
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread1.start();
		}
		
		for(int j = 0; j < 5; j++) {
			Thread thread2 = new Thread(()->{
				try {
					while(true) {
						getCount.getAndIncrement();
						Object s = arrayQueueUtil.get();
						Thread.sleep(10);
						if(s == null) {
							continue;
						}
						if(putCount.get() == 1000) {
							System.out.println(getCount.get());
						}
						System.out.println(Thread.currentThread().getName() + "," + "get " + s + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
						
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			});
			thread2.start();
		}
		
	}

	public static Thread a(ArrayQueueUtil arrayQueueUtil) {
		Thread thread1 = new Thread(()->{
			int i = 0;
			try {
				while(true) {
					boolean s = arrayQueueUtil.put(++i);
					System.out.println(s + " put " + i + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
//					Thread.currentThread().interrupt();
			}
		});
		Thread thread2 = new Thread(()->{
			try {
				while(true) {
					Object i = arrayQueueUtil.get();
					Thread.sleep(10);
					if(i == null) {
						continue;
					}
					
					System.out.println("get " + i + ",size" + arrayQueueUtil.size + ", start" + arrayQueueUtil.start + ", end" + arrayQueueUtil.end);
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread1.interrupt();
		return thread1;
	}
}
