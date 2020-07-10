package com.lishaoping.im.leetcode100;

public class Find_the_once_number137 {

	public static void main(String[] args) {
		ttestt();
		
		test();
	}

	private static void test() {
		int x= 123434;
		for(int i=0; i < 100;i++) {
			System.out.println(x|i|i^i);
		}
	}

	private static void ttestt() {
		int[] nums = {0,1,0,1,0,1,99};
		int[] target = new int[32];
		for(int i = 0; i < nums.length; i++) {
			int n = nums[i];
			int x = 1;
			//method1:
//			for(int j = 0; j < 32; j++) {
//				target[j] += ((n & x) > 0 ? 1 : 0);
//				x = x << 1;
//			}
			//method2:
			int j = 0;
			while(n > 0) {
				target[j++] += n%2;
				n = n>>1;
			}
		}
		//—∞’“£∫
		int s = 0;
		for(int i = 0; i < target.length; i++) {
			s |= (target[i] % 3 == 0 ? 0 : (1<<i));
		}
		System.out.println(s);
		
		//
	}
}
