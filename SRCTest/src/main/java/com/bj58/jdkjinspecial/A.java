package com.bj58.jdkjinspecial;

/**
 * 编译级错误检查：一旦加了这个注解，那么写其他抽象方法---不是Object的方法就会报错
 * 子类抽象化父类的非抽象方法，不会报错，调用时只调用父类的方法，即也不报错
 * @ClassName:A
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月21日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial
 */
@FunctionalInterface
public interface A {

	default void moren() {
		System.out.println("特别的实现");
	}
	
	static void jingtai() {
		System.out.println("静态的实现");
	}
	
//	void a();
	void b(int a);
//	void c(int a, int b);
//	void d(String a);
	
	boolean equals(Object obj);
	
}
