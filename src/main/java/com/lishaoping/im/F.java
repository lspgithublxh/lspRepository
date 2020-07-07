package com.lishaoping.im;

public class F {

	public static void main(String[] args) {
		int s = test(9);
		System.out.println(s);
	}

	private static int test(int i) {
		if(i == 0) {
			return 0;
		}else if(i == 1) {
			return 1;
		}else if(i == 2) {
			return 2;
		}
		return test(i - 2) + 1 + test(i - 3) + 1;
	}
}
