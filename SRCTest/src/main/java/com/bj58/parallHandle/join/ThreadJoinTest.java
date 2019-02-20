package com.bj58.parallHandle.join;

public class ThreadJoinTest {

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("cu:" + Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("end:" + Thread.currentThread().getName());
			}
		});
		t.start();
		try {
			System.out.println("main wait:");
			t.join();
			System.out.println("main go on:");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
