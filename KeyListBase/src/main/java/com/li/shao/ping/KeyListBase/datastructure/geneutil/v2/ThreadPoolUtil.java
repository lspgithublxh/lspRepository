/**
 * 
 */
package com.li.shao.ping.KeyListBase.datastructure.geneutil.v2;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lishaoping
 * @date 2019年9月25日
 * @package ltd.forexample.prosser.util
 * @type ThreadPoolUtil
 * TODO 
 */
@Slf4j
public class ThreadPoolUtil {

	public static ThreadPoolExecutor executor = new ThreadPoolExecutor(60, 400, 60, TimeUnit.SECONDS, 
			new ArrayBlockingQueue<Runnable>(200), new NameTreadFactory(), new FEIgnorePolicy());;
	
	public static ThreadPoolExecutor getThreadPool() {
//		if(executor == null) {
//			synchronized (ThreadPoolExecutor.class) {
//				if(executor == null) {
//					executor = new ThreadPoolExecutor(10, 300, 60, TimeUnit.SECONDS, 
//							new ArrayBlockingQueue<Runnable>(200), new NameTreadFactory(), new FEIgnorePolicy());
//				}
//			}
//		} 	
		
		return executor;
	}
	
	static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "4E-thread" + mThreadNum.getAndIncrement());
            t.setDaemon(true);
            t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println("4E-thread uncaughtException : " + e.getMessage());
				}
            } );
            return t;
        }
    }
	
	public static class FEIgnorePolicy implements RejectedExecutionHandler {
		@Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
        	try {
				if (!e.isShutdown()) {
					//直接执行run方法
					r.run();
				}
				log.info("main run task:");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
        }
    }
}
