package com.bj58.jdkjinspecial;

import java.util.function.Function;

@FunctionalInterface
public interface FuntionForm1<V, T> {

	public V convert(T value);
	
	default FuntionForm1<String, String> trans(FuntionForm1<String, String> han) {
		return han;
	}
	
	public static <A, R> void tt(Function<? super A, ? extends R> e) {
		
	}
	
	default void gv() {};//接口中可以有非静态的实现方法
}
