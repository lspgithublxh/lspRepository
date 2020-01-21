package com.li.shao.ping.KeyListBase.datastructure.util.thread;

import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil2;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SyncTest {

	public static void main(String[] args) {
//		test1();
		test2();
	}

	private static void test2() {
		//证明一个锁的同步块里不能用另一个对象锁wait()
		try {
			Object lock = new Object();
			Object lock2 = new Object();
			SimpleThreadPoolUtil2.pool.addTask(()->{
				try {
					Thread.sleep(1000);
					synchronized (lock) {
						log.info("notify main");
						lock2.notify();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			synchronized (lock2) {
				lock2.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test1() {
		//证明-线程被唤醒不是立即开始执行
		log.info("start");
		try {
			Object lock = new Object();
			SimpleThreadPoolUtil2.pool.addTask(()->{
				try {
					Thread.sleep(1000);
					synchronized (lock) {
						log.info("notify other thread2");
						lock.notify();
						log.info("thread1 start wait");
						lock.wait();
						log.info("thread1 wake");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			SimpleThreadPoolUtil2.pool.addTask(()->{
				try {
					synchronized (lock) {
						log.info("notify other thread1");
						lock.notify();
						log.info("do something");
						Thread.sleep(2000);
						log.info("thread2 start wait");//此时thread1仍然没有醒来，因为thread2还占用锁
						lock.wait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			Thread.sleep(100000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
