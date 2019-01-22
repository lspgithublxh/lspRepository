package com.bj58.jdkjinspecial;

/**
 * 默认方法是一个实例方法
 * @ClassName:FuntionForm2
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月21日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial
 * @param <V>
 * @param <T>
 */
@FunctionalInterface
public interface FuntionForm2<V, T> {

	public V convert(T value);
	
	default <A, K> FuntionForm2<A, K> trans(FuntionForm2<A, K> han) {
		System.out.println(han.getClass().getName());//同一个方法引用，第二次就是另一个lamda表达式了。
//		System.out.println(han);
		return han;
	}
}
