package com.li.shao.ping.KeyListBase.datastructure.util.monitor;

import java.util.Map;

import org.junit.Test;

import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil2;
import com.li.shao.ping.KeyListBase.datastructure.inter.SimpleThreadFactory;

import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 存在的意义：组织线程，明确线程之间关系。推论：可以从一个线程-当前线程而获取所有的线程。
 *  >将线程分组：设置优先级、守护线程。
 *  >线程组树：线程、线程组为元素节点。
 *  
 * -- 获取线程运行时间：各个调用方法运行时间。 
 * 
 * @author lishaoping
 * @date 2020年1月9日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.monitor
 */
@Slf4j
public class ThreadGroupUtil {

	class DefaultFactory extends SimpleThreadFactory{}
	
	private SimpleThreadPoolUtil2 tpool = new SimpleThreadPoolUtil2(100, 200, 10, 1000,
			(task) ->{task.run();return true;}, new DefaultFactory()) ;
	
	/**
	 * 查看所有的线程的堆栈：
	 * @return
	 */
	public Map<String, StringBuffer> getFocusThread() {
		ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
		while(threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}
		Thread[] allThread = new Thread[threadGroup.activeCount()];
		int total = threadGroup.enumerate(allThread);
		Map<String, StringBuffer> threadMap = Maps.newHashMap();
		for(Thread th : allThread) {
			if(!th.getName().equals("main") && !th.getName().startsWith("lab-thread-")) {
				continue;
			}
			StringBuffer stInfo = new StringBuffer();
			StackTraceElement[] stackTrace = th.getStackTrace();
			for(StackTraceElement st : stackTrace) {
				stInfo.append("" + st.getClassName() + "." + st.getMethodName() + "()" + st.getLineNumber()).append("\r\n");
			}
			threadMap.put(th.getName(), stInfo);
		}
		return threadMap;
	}
	
	/**
	 * 
	 */
	public void getThreadRunTime() {
		
	}
	
	public void getAllThread() {
		tpool.addTask(()->{log.info("new a thread");});
//		tpool.addTask(()->{log.info("new a thread2");});

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
		}
//		System.out.println("--------------------------------");
//		Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
//		allStackTraces.entrySet().stream().forEach(item ->{
//			Thread thread = item.getKey();
//			StackTraceElement[] stacks = item.getValue();
//			StackTraceElement[] stacks2 = thread.getStackTrace();
//			log.info("thread name:" + thread.getName());
//			log.info("" + (stacks == stacks2));
//		});
		
	}
	
	@Test
	public void test() {
//		getAllThread();
		Map<String, StringBuffer> focusThread = getFocusThread();
		focusThread.forEach((k, v) ->{
			System.out.println(k);
			System.out.println(v);
		});
		
	}
	
	public static void main(String[] args) {
		System.out.println("");
		new ThreadGroupUtil().getAllThread();
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
