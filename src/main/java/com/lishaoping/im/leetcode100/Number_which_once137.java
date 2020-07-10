package com.lishaoping.im.leetcode100;

public class Number_which_once137 {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		//
		int[] arr = {0,1,0,1,0,1,99};	
		int once = 0;
		int twice = 0;
		for(int num : arr) {
			once = once ^ num & (~twice);
			twice = twice ^num & (~once);
		}
		System.out.println(once);
	}
}
