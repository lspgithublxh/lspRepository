package com.li.shao.ping.KeyListBase.datastructure.geneutil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.li.shao.ping.KeyListBase.datastructure.inter.RejectionStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池工具:
 *
 *也提供两种提交方式：execute()和submit()方式
 * @author lishaoping
 * @date 2019年12月12日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil
 */
@Slf4j
public class SimpleThreadPoolUtil {

	private ArrayBlockingQueue<Runnable> tasks;//LinkedBlockingQueue
	private Set<Worker> workers;
	private int maxWorkerNum;
	private int maxIdleWorkerNum;
	private long maxIdelTime;
	private int maxTaskNum;
	private RejectionStrategy rejHandle;
	
	public SimpleThreadPoolUtil(int maxWorkerNum, int maxTaskNum, int maxIdleWorkerNum
			,long maxIdelTime) throws Exception {
		this.maxWorkerNum = maxWorkerNum;
		this.maxIdelTime = maxIdelTime;
		this.maxTaskNum = maxTaskNum;
		tasks = new ArrayBlockingQueue<Runnable>(maxTaskNum);
		workers = new HashSet<Worker>(maxWorkerNum);
		//初始化创建最小线程数
		this.maxIdleWorkerNum = maxIdleWorkerNum;
		initWorkder();
	}
	
	public SimpleThreadPoolUtil(int maxWorkerNum, int maxTaskNum, int maxIdleWorkerNum
			,long maxIdelTime, RejectionStrategy rejHandle){
		this.maxWorkerNum = maxWorkerNum;
		this.maxIdelTime = maxIdelTime;
		this.maxTaskNum = maxTaskNum;
		this.rejHandle = rejHandle;
		tasks = new ArrayBlockingQueue<Runnable>(maxTaskNum);
		workers = new HashSet<Worker>(maxWorkerNum);
		//初始化创建最小线程数
		this.maxIdleWorkerNum = maxIdleWorkerNum;
		try {
			initWorkder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initWorkder() throws Exception {
		for (int i = 0; i < maxIdleWorkerNum; i++) {
			boolean rs = workers.add(new Worker("worker-" + i).start());
			if (!rs) {
				throw new Exception("init worker error");
			}
		} 
	}

	public boolean addTask(Runnable task) {
		try {
			//同步查看：队列已经满,则看能否新增worker
			if(tasks.size() >= maxTaskNum) {
				synchronized (this) {
					if(tasks.size() >= maxTaskNum) {
						if(workers.size() < maxWorkerNum) {
							workers.add(new Worker("new-worker-" + task.hashCode()).start());
						}
						return rejHandle == null ? false : rejHandle.handle(task);//不能再添加任务--执行 拒绝策略：false, exception, main-exe
					}//继续
				}
			}
			//可以一半task的时候开始增加woker TODO add
			if(tasks.size() >= maxTaskNum / 2 && tasks.size() < maxTaskNum) {
				synchronized (this) {
					if(tasks.size() >= maxTaskNum / 2 && tasks.size() < maxTaskNum) {
						workers.add(new Worker("new-worker-" + task.hashCode()).start());
					}
				}
			}
			synchronized (this) {
				boolean rs = tasks.offer(task);
				return rs;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public long countIdleWorker() {
		return workers.stream().filter(item -> item.status.get() == 2).count();
	}
	
   class Worker{
		private AtomicInteger status;//执行者 状态, 运行中1，还是idle2
		private long startTime;
//		private long updateTime;
		private String name;
	public Worker(String name) {
		this.name = name;
		this.status = new AtomicInteger(2);
	}
		
		public Worker start() {
			startTime = System.currentTimeMillis();
			new Thread(()->{
				while(true) {
					try {
						status.set(2);
						Runnable ts = tasks.poll(maxIdelTime, TimeUnit.MILLISECONDS);
						//原子读取并且删除woker
//						log.info("ts:" + tasks.size() + "worksize:" + workers.size());
						if(ts == null) {
							synchronized (tasks) {
								if(workers.size() > maxIdleWorkerNum) {
									workers.remove(this);
									break;
								}else {
									continue;
								}
							}
						}
//						log.info("hashcode:" + ts.hashCode());
						status.set(1);
						ts.run();
						status.set(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("error-worker");
						log.error("[worker-take-error]" + name);
						status.set(2);
					}
				}
				log.error("worker" + name + " exit" );
			}).start();
			return this;
		}
		
	}
   
  public static SimpleThreadPoolUtil pool = new SimpleThreadPoolUtil(100, 200, 30, 1000,
			(task) ->{task.run();return true;}) ;
   
   public static void main(String[] args) {
	//线程池测试
	   try {
//		SimpleThreadPoolUtil pool = new SimpleThreadPoolUtil(10, 20, 3, 1000);
			SimpleThreadPoolUtil pool = new SimpleThreadPoolUtil(10, 20, 3, 1000,
					(task) ->{task.run();return true;}) ;
		for(int i = 0; i< 100; i++) {
			final int j = i;
			pool.addTask(()->{
				System.out.println("execute task" + j);
			});
		}
		Thread.sleep(3000);
		System.out.println("--------------------------------");
		for(int i = 0; i< 100; i++) {
			final int j = i;
			pool.addTask(()->{
				System.out.println("execute task" + j);
			});
		}
		System.out.println("end main");
	   } catch (Exception e) {
		e.printStackTrace();
	}
	   
}
}
