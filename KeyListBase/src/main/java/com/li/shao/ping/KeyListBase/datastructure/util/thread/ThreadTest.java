package com.li.shao.ping.KeyListBase.datastructure.util.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadTest {

	public static void main(String[] args) {
//		keke1();
//		keke2();
		keke3();
	}

	private static void keke3() {
		AtomicInteger count = new AtomicInteger(0);
		Thread thread1 = new Thread(()->{
			for(;;) {
				if(count.get() % 3 == 0) {
					System.out.println("1");
					int c = count.incrementAndGet();
					if(c > 198) {
						break;
					}
				}
			}
		});
		
		Thread thread2 = new Thread(()->{
			for(;;) {
				if(count.get() % 3 == 1) {
					System.out.println("2");
					int c = count.incrementAndGet();
					if(c > 199) {
						break;
					}
				}
			}
		});
		Thread thread3 = new Thread(()->{
			for(;;) {
				if(count.get() % 3 == 2) {
					System.out.println("3");
					int c = count.incrementAndGet();
					if(c > 200) {
						break;
					}
				}
			}
		});
		thread1.start();
		thread2.start();
		thread3.start();
	}

	private static void keke2() {
		AtomicInteger count = new AtomicInteger(0);
		Thread thread1 = new Thread(()->{
			for(;;) {
				if(count.get() % 2 == 0) {
					System.out.println("1");
					int c = count.incrementAndGet();
					if(c > 198) {
						break;
					}
				}
			}
		});
		
		Thread thread2 = new Thread(()->{
			for(;;) {
				if(count.get() % 2 == 1) {
					System.out.println("2");
					int c = count.incrementAndGet();
					if(c > 198) {
						break;
					}
				}
			}
		});
		thread1.start();
		thread2.start();
	}

	private static void keke1() {
		Object lock = new Object();
		Thread thread1 = new Thread(()->{
			for(int i = 0; i < 100; i++) {
				try {
					synchronized (lock) {
						System.out.println("1");
						lock.notify();
						lock.wait();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread thread2 = new Thread(()->{
			for(int i = 0; i < 100; i++) {
				try {
					synchronized (lock) {
						System.out.println("2");
						lock.notify();
						lock.wait();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}) ;
		thread1.start();
		thread2.start();
	}
}
