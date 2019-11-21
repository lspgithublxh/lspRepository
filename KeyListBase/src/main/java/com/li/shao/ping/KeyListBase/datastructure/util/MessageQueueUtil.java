package com.li.shao.ping.KeyListBase.datastructure.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageQueueUtil {
	
	LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);

	public boolean putMessage(String mes) {
		try {
			return queue.offer(mes, 100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getMessage() {
		try {
			return queue.take();//100, TimeUnit.MILLISECONDS
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		MessageQueueUtil messageQueueUtil = new MessageQueueUtil();
		Thread thread1 = new Thread(()->{
			for(int i = 0; i < 100; i++) {
				boolean putMessage = messageQueueUtil.putMessage(i + "");
				System.out.println("put " + i + "" + putMessage);
			}
		});
		Thread thread2 = new Thread(()->{
			while(true) {
				String mes = messageQueueUtil.getMessage();
				System.out.println("get mes:" + mes);
			}
		});
		thread1.start();
		thread2.start();
	}
}
