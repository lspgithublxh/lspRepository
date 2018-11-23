package com.bj58.fang.cache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 加锁---耗时可能翻10倍
 * @ClassName:LockTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月22日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class LockTest {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock(true);
		Condition a = lock.newCondition();
		Condition b = lock.newCondition();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					lock.lockInterruptibly();
					System.out.println("haha");
					a.await();
					System.out.println("---reok");
					lock.unlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					lock.lockInterruptibly();
					a.signal();
					System.out.println("---signal");
					lock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
}
