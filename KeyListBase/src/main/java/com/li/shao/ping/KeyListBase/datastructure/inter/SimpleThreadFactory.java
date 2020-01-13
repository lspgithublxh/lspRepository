package com.li.shao.ping.KeyListBase.datastructure.inter;

import com.li.shao.ping.KeyListBase.datastructure.util.uid.UIDUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SimpleThreadFactory {

	private ThreadGroup tg = new ThreadGroup("top-thread-group");
	
	
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(tg, runnable, "lab-thread-" + UIDUtil.increNum());
		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler((Thread t, Throwable e)->{
			log.info(t + " thread exception, handle error:" + e);
		});
		return thread;
	};
}
