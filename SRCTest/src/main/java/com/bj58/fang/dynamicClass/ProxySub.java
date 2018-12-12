package com.bj58.fang.dynamicClass;

import java.lang.reflect.Method;

public class ProxySub {

	private static ProxySub inst = new ProxySub();
	
	public static ProxySub getInstance() {
		return inst;
	}
	
	public Object proxy(Method method, BIaoji a) {
		return null;
	}
}
