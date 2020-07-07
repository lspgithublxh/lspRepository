package com.lishaoping.im;

import java.util.concurrent.atomic.AtomicInteger;

public class G {

	public static void main(String[] args) {
		AtomicInteger i = new AtomicInteger(0);
		AtomicInteger in = new AtomicInteger(0);
		Thread a = new Thread(()-> {
			while(true) {
				if(i.get() % 2 == 0) {
					System.out.println("a thread:" + i.get());
					i.incrementAndGet();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(in.incrementAndGet() == 100) {
						break;
					}
				}
			}
		});
		Thread b = new Thread(()-> {
			while(true) {
				if(i.get() % 2 == 1) {
					System.out.println("b thread:" + i);
					i.incrementAndGet();
					if(in.incrementAndGet() == 100) {
						break;
					}
				}
			}
		});
		a.start();
		b.start();
	}
}
