package com.li.shao.ping.KeyListBase;

public class ThreadTest {

	public static void main(String[] args) {
		Object lock1 = new Object();
		Object lock2 = new Object();
		Thread thread = new Thread(()->{
			synchronized (lock1) {
				try {
					Thread.sleep(1000);
					synchronized (lock2) {
						System.out.println("sss");
					}
					System.out.println("end1");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}) ;
		Thread thread2 = new Thread(()->{
			synchronized (lock2) {
				try {
					synchronized (lock1) {
						System.out.println("");
					}
					System.out.println("end2");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}) ;
		thread.start();
		thread2.start();
	}
}
