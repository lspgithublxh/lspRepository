package com.bj58.parallHandle;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 1.Call:有返回值的run方法
 * @ClassName:CallerAndComsumer
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月22日
 * @Version V1.0
 * @Package com.bj58.parallHandle
 */
public class CallerAndComsumer {

	public static void main(String[] args) {
		asynchronizeTwoMethod();
	}

	/**
	 * 主次的异步----主线程等待子线程完成
	 * @param 
	 * @author lishaoping
	 * @Date 2019年1月22日
	 * @Package com.bj58.parallHandle
	 * @return void
	 */
	private static void asynchronizeTwoMethod() {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<String> f = service.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("sub");
				String now = System.currentTimeMillis() + ";";
				return now;
			}
		});//此时任务已经跑起来了，已经在运行了。
		System.out.println("main");
		ThreadPoolExecutor s = (ThreadPoolExecutor)service;
		System.out.println("当前活跃线程数" + s.getTaskCount());
		try {
			Thread.sleep(2000);
//			System.out.println(f.get());
			System.out.println(f.get(1000, TimeUnit.MILLISECONDS));
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}//等待执行，，直到取得结果
		System.out.println("当前活跃线程数" + s.getTaskCount());
	}
}
