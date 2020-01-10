package com.li.shao.ping.KeyListBase.datastructure.inter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SimpleThreadFactory {

	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(runnable, "lsp-thread-");
		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler((Thread t, Throwable e)->{
			log.info(t + " thread exception, handle error:" + e);
		});
		return thread;
	};
}
