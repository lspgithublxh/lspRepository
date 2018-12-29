package com.bj58.pubthree.jiankong;

public class OpenFalcon {

	public static void main(String[] args) {
		test();
	}

	/**
	 * 由变量类型自动定方法返回类型
	 * 单变量可以是任意值
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月28日
	 * @Package com.bj58.pubthree.jiankong
	 * @return void
	 */
	private static void test() {
		Integer i = new OpenFalcon().method();
		System.out.println(i);
	}
	
	private <T> T method() {
		Integer i = 10;
		return (T) i;
	}
}
