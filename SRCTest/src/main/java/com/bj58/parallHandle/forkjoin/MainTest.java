package com.bj58.parallHandle.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		ForkJoinPool pool = new ForkJoinPool(4);
		ComputeTask task = new ComputeTask(1);//1开始，递归计算----即为依赖式计算，：：
		ForkJoinTask<Double> rs = pool.submit(task);
		try {
			double d = rs.get(1000, TimeUnit.MILLISECONDS);
			System.out.println("rs is :" + d);
			double d2 = pool.submit(new JiechengTask(1)).get(1000, TimeUnit.MILLISECONDS);
			System.out.println(d2);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}
}
