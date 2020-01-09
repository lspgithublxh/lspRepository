package com.li.shao.ping.KeyListBase.datastructure.util.monitor;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 存在的意义：组织线程，明确线程之间关系。推论：可以从一个线程-当前线程而获取所有的线程。
 *  >将线程分组：设置优先级、守护线程。
 *  >线程组树：线程、线程组为元素节点。
 * @author lishaoping
 * @date 2020年1月9日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.monitor
 */
@Slf4j
public class ThreadGroupUtil {

	public void getAllThread() {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		while(threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}
		Thread[] allThread = new Thread[threadGroup.activeCount()];
		int total = threadGroup.enumerate(allThread);
		log.info("all thread:" + total);
		for(Thread th : allThread) {
			log.info("thread name:" + th.getName());//通过名称可以过滤很多线程：如main线程
			StackTraceElement[] stackTrace = th.getStackTrace();
			for(StackTraceElement st : stackTrace) {
				log.info("" + st.getClassName() + "," + st.getMethodName() + "," + st.getLineNumber());
			}
//			th.getAllStackTraces()
			
		}
	}
	
	@Test
	public void test() {
		getAllThread();
	}
}
