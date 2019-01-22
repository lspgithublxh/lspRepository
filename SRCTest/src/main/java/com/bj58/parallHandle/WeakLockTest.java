package com.bj58.parallHandle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有竞争的时候才加锁等待
 * @ClassName:WeakLockTest
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月22日
 * @Version V1.0
 * @Package com.bj58.parallHandle
 */
public class WeakLockTest {

	public static void main(String[] args) {
		qingliangsuo();
		
	}

	private static void qingliangsuo() {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		ExecutorService servic = Executors.newFixedThreadPool(2);
		servic.submit(()->{
			lock.lock();//相当于同步开始--有竞争的时候才加锁
			try {
				System.out.println("方法，处理");
				//实际处理方法
				System.out.println("wait");
				condition.await();//阻塞，按条件阻塞;;;可以按照多个条件阻塞
				System.out.println("awake");
			} catch (Exception e) {
			}finally {
				lock.unlock();
			}
		});
		try {
			Thread.sleep(1000);
			lock.lock();
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}
