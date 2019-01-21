package com.bj58.jdkjinspecial;

@FunctionalInterface
public interface FuntionForm2<V, T> {

	public V convert(T value);
	
	default FuntionForm1<String, String> trans(FuntionForm1<String, String> han) {
		return han;
	}
}
