package com.bj58.fang.dynamicClass;

import org.aspectj.lang.annotation.AdviceName;

public interface AConnection {

	@Deprecated
	public void method1();
	
	@AdviceName(value="")
	public int method2(Object a1, String a2);
	
	public Object method3(Object a3, Integer a4);
}
