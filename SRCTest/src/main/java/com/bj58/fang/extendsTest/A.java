package com.bj58.fang.extendsTest;

public class A {

	public A getThis() {
		System.out.println("A");
		return this;//super不是指针，只是一个标记---方法和属性方法的标记---不是任何实例的指针，所以不能return super
	}
}
