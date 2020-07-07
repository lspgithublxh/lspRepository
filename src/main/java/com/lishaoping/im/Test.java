package com.lishaoping.im;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {

	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(0);
		int max = 5;
		test(0, count, max);
		System.out.println(count.get());
	}

	private static void test(int hasStep, AtomicInteger count, int max) {
		if(hasStep == max) {
			count.incrementAndGet();
		}else if(hasStep < max){
			test(hasStep + 1, count, max);
			test(hasStep + 2, count, max);
		}
	}
}
