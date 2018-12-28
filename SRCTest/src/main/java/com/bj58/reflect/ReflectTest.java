package com.bj58.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射大概慢6-10倍时间
 * @ClassName:ReflectTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月27日
 * @Version V1.0
 * @Package com.bj58.reflect
 */
public class ReflectTest {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("hahaha");
		
		try {
			test();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private static void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FFFchai f = new FFFchai();
		f.setAge(11211);
		f.setName("hehehehehheehe");
		long t1 = System.currentTimeMillis();
		for(int i = 0 ; i < 10000; i++) {
			Integer age = f.getAge();
			String name = f.getName();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		FFFchai ff = null;
		try {
			Class<?> cls = Class.forName("com.bj58.reflect.FFFchai");
			Object r = cls.newInstance();
			ff = (FFFchai) r;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Method m1 = FFFchai.class.getMethod("getAge");
		Method m2 = FFFchai.class.getMethod("getName");
		long t12 = System.currentTimeMillis();
		for(int i = 0 ; i < 10000; i++) {
			Integer a = (Integer) m1.invoke(f);
			String b = (String) m2.invoke(f);
		}
		long t22 = System.currentTimeMillis();
		System.out.println(t22 - t12);
		
		//3.com.esotericsoftware.reflectasm.MethodAccess
		
	}
}
