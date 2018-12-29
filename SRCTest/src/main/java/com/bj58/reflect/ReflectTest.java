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
			test();//14368 2  3857  328
			test2();//14248 8 3914 327
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 反射调用20000次，才会有6ms的差别，所以并没有慢多少
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月29日
	 * @Package com.bj58.reflect
	 * @return void
	 */
	private static void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FFFchai f = new FFFchai();
		f.setAge(11211);
		f.setName("hehehehehheehe");
		long t1 = System.currentTimeMillis();
		for(int i = 0 ; i < 30; i++) {
			Integer age = f.getAge();
			String name = f.getName();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
		
	}

	private static void test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		FFFchai f = new FFFchai();
		f.setAge(11211);
		f.setName("hehehehehheehe");
		Method m1 = FFFchai.class.getMethod("getAge");
		Method m2 = FFFchai.class.getMethod("getName");
		long t12 = System.currentTimeMillis();
		for(int i = 0 ; i < 30; i++) {//并发了10000次反射才有这个累计时间差距
			Integer a = (Integer) m1.invoke(f);
			String b = (String) m2.invoke(f);
		}
		long t22 = System.currentTimeMillis();
		System.out.println(t22 - t12);
	}
}
