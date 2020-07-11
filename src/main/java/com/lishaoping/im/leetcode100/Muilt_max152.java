package com.lishaoping.im.leetcode100;

public class Muilt_max152 {

	public static void main(String[] args) {
		mmxm();
	}

	private static void mmxm() {
		int[] arr = {2,3,-2,4};
		int[] f = new int[arr.length];
		f[0] = arr[0];
		
		for(int i = 0; i < f.length; i++) {
			for(int j = 0; j <= i; j++) {
				int mu = 1;
				for(int k = j; k <= i; k++) {
					mu *= arr[k];
				}
				if(mu > f[i]) {
					f[i] = mu;
				}
			}
		}
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < f.length; i++) {
			if(f[i] > max) {
				max = f[i];
			}
		}
		System.out.println(max);
	}
}
