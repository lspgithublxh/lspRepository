package com.bj58.parallHandle.forkjoin;

import java.util.concurrent.RecursiveTask;

public class JiechengTask extends RecursiveTask<Double>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n;
	
	public JiechengTask(int n) {
		super();
		this.n = n;
	}

	@Override
	protected Double compute() {
		if(n > 20) {
			return 0.0;
		}
		long num = 1;
		for(int i = 1; i <= n; i++) {
			num *= i;
		}
		System.out.println("num :" + num);
		JiechengTask task = new JiechengTask(n + 1);
		task.fork();
		Double data = task.join();
		System.out.println("data:" + data);
		return data + (double)1 / num;
	}

}
