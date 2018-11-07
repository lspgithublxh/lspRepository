package com.scrapy.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MulitThread {

	 public static void main(String[] args) {
		one();
	}

	private static void one() {
//		Executors.newFixedThreadPool(10);
		ExecutorService exe = Executors.newCachedThreadPool();
		for(int i = 0 ; i < 10; i++) {
			exe.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("----" + Thread.currentThread().getName());
				}
				
			});
		}
		
		
		
	}
}
