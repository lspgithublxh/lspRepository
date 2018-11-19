package com.bj58.fang.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {

	public static void main(String[] args) {
//		bingxingzhixingshunxuqu();
		bingxing();
		
	}

	/**
	 * 并行n次计算，顺序n次取值；    计算和取值也是并行的
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月19日
	 * @Package com.bj58.fang.thread
	 * @return void
	 */
	private static void bingxingzhixingshunxuqu() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<Future<Integer>> lis = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Future<Integer> future = service.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(1000);
					System.out.println("------" + Thread.currentThread().getName());
					String name = Thread.currentThread().getName();
					Integer n = Integer.valueOf(name.substring(name.lastIndexOf("-") + 1));
					return n;
				}
			});
			lis.add(future);
		}
		for(Future<Integer> f : lis) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 单纯并行n次执行，没有返回结果
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月19日
	 * @Package com.bj58.fang.thread
	 * @return void
	 */
	private static void bingxing() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i = 0; i < 10; i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						System.out.println("------" + Thread.currentThread().getName());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("--main---");
	}
}
