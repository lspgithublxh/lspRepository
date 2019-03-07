package com.bj58.parallHandle.sychronizede;

public class TestSyso {

	public static void main(String[] args) {
		new TestSyso().tt();
	}
	
	public void tt() {
		System.out.println("haha");
		final TestSyso this_ = this;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				synchronized (this_) {
					System.out.println("start");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (TestSyso.class) {
						System.out.println("yes" + this);
					}
					new B().test();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (TestSyso.class) {
					System.out.println("ttt" + Thread.currentThread().getName());
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (this_) {
						int i = 0;
						int x = 2;
						System.out.println("end");
					}
				}
			}
		}).start();
	}
}
