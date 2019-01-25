package com.bj58.parallHandle.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

/**
 * 一次任务的结果由 同类 但不同 参数 的子任务 的结果来描述，自身也会出占有一定的计算因子-成分
 * 并发量只是线程的个数，且每个线程都有自己的任务队列------所以存在工作窃取的机制----实际上切换任务是耗时的---除非利用好了cpu核数
 * @ClassName:ComputeTask
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月23日
 * @Version V1.0
 * @Package com.bj58.parallHandle.forkjoin
 */
public class ComputeTask extends RecursiveTask<Double>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n;
	
	public ComputeTask(int n) {
		super();
		this.n = n;
	}


	@Override
	protected Double compute() {
		if(n  == 100) {
			return (double)1 / 100;
		}else {
			ComputeTask task1 = new ComputeTask(n+1);
//			ComputeTask task2 = new ComputeTask(n+2);
			task1.fork();
//			task2.fork();
			try {
				double num1 = task1.get();
//				double num2 = task2.get();
				//抛错与否的差别
//				task1.join();task2.join();
				System.out.println("n+1=" + (n+1));
				return num1 + (double)1 / n;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return 0.0;
	}

}
