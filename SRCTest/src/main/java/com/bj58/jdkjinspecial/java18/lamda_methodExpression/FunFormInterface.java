package com.bj58.jdkjinspecial.java18.lamda_methodExpression;

@FunctionalInterface
public interface FunFormInterface<T, V> {

	public T conv(V...v);
}
