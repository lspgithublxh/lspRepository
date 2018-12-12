package com.bj58.fang.dynamicClass;

public class Aimple implements AConnection {
	
	@Override
	public void method1() {
		System.out.println("aimple method1 call");
	}

	@Override
	public int method2(Object a1, String a2) {
		return 0;
	}

	@Override
	public Object method3(Object a3, Integer a4) {
		return new Object();
	}

}
