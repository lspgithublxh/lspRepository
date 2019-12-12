package com.li.shao.ping.KeyListBase.datastructure.geneutil;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 固定大小线程池
 *
 * @author lishaoping
 * @date 2019年12月12日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil
 */
public class SolidSizeThreadPoolUtil {

	private LinkedBlockingQueue<Runnable> tasks;
	private int threadSize;
	
	private SolidSizeThreadPoolUtil(int size, int taskSize) {
		threadSize = size;
		tasks = new LinkedBlockingQueue<Runnable>(taskSize);
		executeTask();
	}
	
	public boolean addTask(Runnable task) {
		try {
			boolean rs = tasks.offer(task);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void executeTask() {
		for(int i = 0; i < threadSize; i++) {
			new Thread(()->{
				while(true) {
					try {
						Runnable task = tasks.take();
						task.run();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}
	}
   
}
