package com.bj58.fang.timer_keyvalue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基本的缓存重启测试类
 * 测试可以---多线程环境也没有问题
 * @ClassName:TestCacheContainer
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月3日
 * @Version V1.0
 * @Package com.bj58.fang.timer_keyvalue
 */
public class TestCacheContainer {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		CacheContainer container = CacheContainer.getInstance();
		IGetValue<Integer, Integer> iget = new IGetValue<Integer, Integer>() {
			@Override
			public Integer getValue(Integer cid) {
				//远程调用
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
				System.out.print("get value:" + cid + "--------" + format.format(new Date()));
				return (int) (cid + Math.round(Math.random() * 100));
			}
		};
		container.setIGetValue(iget);
		container.setExpiredTime(3000);
		for(int cityId = 1; cityId < 40; cityId++) {
			System.out.print(container.get(cityId) + ",");
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start" + Thread.currentThread().getName());
				String rs = "";
				for(int cityId = 1; cityId < 40; cityId++) {
					rs += container.get(cityId) + ",";
				}
				System.out.print("***" + rs + "***");
				System.out.println("end");
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start" + Thread.currentThread().getName());
				String rs = "";
				for(int cityId = 1; cityId < 40; cityId++) {
					rs += container.get(cityId) + ",";
				}
				System.out.print("***" + rs + "***");
				System.out.println("end");
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start" + Thread.currentThread().getName());
				String rs = "";
				for(int cityId = 1; cityId < 40; cityId++) {
					rs += container.get(cityId) + ",";
				}
				System.out.print("***" + rs + "***");
				System.out.println("end");
			}
		});
		t1.start();
		t2.start();
		t3.start();
		System.out.println("-------------get just1-------------------");
		for(int cityId = 1; cityId < 40; cityId++) {
			System.out.print(container.get(cityId) + ",");
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-------------get just2-------------------");
		for(int cityId = 1; cityId < 40; cityId++) {
			System.out.print(container.get(cityId) + ",");
		}
		System.out.println("-------------get just3-------------------");
		for(int cityId = 1; cityId < 40; cityId++) {
			System.out.print(container.get(cityId) + ",");
		}
		
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("start" + Thread.currentThread().getName());
				String rs = "";
				for(int cityId = 1; cityId < 40; cityId++) {
					rs += container.get(cityId) + ",";
				}
				System.out.print("***" + rs + "***");
				System.out.println("end");
			}
		});
		t4.start();
	}
}
