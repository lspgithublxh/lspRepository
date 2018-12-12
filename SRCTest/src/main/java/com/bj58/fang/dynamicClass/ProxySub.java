package com.bj58.fang.dynamicClass;

import java.lang.reflect.Method;

public class ProxySub {

	private static ProxySub inst = new ProxySub();
	
	public static ProxySub getInstance() {
		return inst;
	}
	
	public Object proxy(Method method, Object instance, Object...args) {
		try {
			Method[] mes = instance.getClass().getDeclaredMethods();
			Method m = null;
			for(Method s : mes) {
				if("proxy".equals(s.getName())) {//method.toGenericString().equals(s.toGenericString())
					m = s;
					break;
				}
			}
			System.out.println(method.toGenericString());
			System.out.println("public void com.bj58.fang.dynamicClass.Aimple.method1()".equals(method.toGenericString()));
//			Method m = instance.getClass().getDeclaredMethod("proxy", Method.class, BIaoji.class, new Object[0].getClass());
			Object res = m.invoke(instance, method, null,  new Object[] {});//new Object(), "ee"
			System.out.println(res);
			System.out.println("--------invoke ok");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		if("public java.lang.Object com.bj58.fang.dynamicClass.Aimple.method3(java.lang.Object,java.lang.Integer)".equals(method.toGenericString())) {
//			
//		}
	}
}
