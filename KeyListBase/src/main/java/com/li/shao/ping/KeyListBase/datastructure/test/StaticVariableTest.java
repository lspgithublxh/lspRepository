package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.entity.ListNode;

public class StaticVariableTest {

	private static Map<String, ListNode> m = Maps.newConcurrentMap();
	volatile ListNode li = new ListNode();
	static {
		m.put("1", new ListNode().setId(10).setScore(2));
	}
	public void test() {
		AtomicInteger atomicInteger = new AtomicInteger(1);
		Thread thread1 = new Thread(()->{
			for(int i = 0; i < 40; i++) {
				int x = atomicInteger.incrementAndGet();
				m.get("1").setId(x);
				li.setId(x);
				System.out.println("set:" + x);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread thread2 = new Thread(()->{
			for(int i = 0; i < 40; i++){
				System.out.println("get:" + m.get("1").getId() + " ," + li.getId() );
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		thread2.start();
		
	  
	}
	
	public static void main(String[] args) {
		new StaticVariableTest().test();
	}
}
