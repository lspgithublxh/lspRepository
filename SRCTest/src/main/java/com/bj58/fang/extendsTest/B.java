package com.bj58.fang.extendsTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class B extends A{
	A x = null;
	
	public B() {
//		try {
//			x = super.getClass().newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
		
	}
	
	@Override
	public A getThis() {
		
//		A x = super.getThis();//不写不会调用父类的方法
//		System.out.println(x);
		System.out.println(this);//动态代理，实例化父类和自己
		Method m;
		try {
			m = A.class.getMethod("getThis");//可以
//			m = super.getClass().getMethod("getThis");//不可以
//			m = super.getClass().getSuperclass().getMethod("getThis");//可以
			System.out.println(super.getClass().getName());//
//			m.invoke(new A());//可以
			m.invoke(m, super.getThis());//不可以
//			m.invoke(m, this);//不可以
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		new C().xx();//this指向当前实例，如果是父类方法调用，那么其实实例只有一个，所以任然是this；如果是不同类，那么自然不是
//		super.getClass().newInstance();
		return null;
	}
	
	public static void main(String[] args) {
		new B().getThis();
		
	}
}
