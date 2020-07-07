package com.lishaoping.im.leetcode;

public class Dp_minPath {

	 public static void main(String[] args) {
		int pathtotal = test();
		System.out.println(pathtotal);
		int pathtotal2 = test2();
		System.out.println(pathtotal2);
	}
	 
	 private static int test2() {
			int[][] arr = {{1,3,1,3},{1,5,1,3},{4,2,1,6}};
			int[] last = new int[arr[0].length];
			String[] path = new String[arr[0].length];
			last[0]=1;
			path[0] = "s";
			for(int i = 1; i < last.length; i++) {
				last[i] = Integer.MAX_VALUE;
			}
			for(int i = 0; i < arr.length; i++) {
				if(i > 0) {
					last[0] += arr[i][0];
					path[0] = "¡ý";
				}
				for(int j = 1; j < last.length; j++) {
					if(last[j - 1] < last[j]) {
						path[j] = "¡ú";
					}else {
						path[j] = "¡ý";
					}
					last[j] = arr[i][j] + ( last[j - 1] < last[j] ? last[j - 1] : last[j]);
				}
//				for(int s : last) {
//					System.out.print(s + ",");
//				}
//				System.out.println();
				for(String s : path) {
					System.out.print(s + ",");
				}
				System.out.println();
			}
			return last[arr[0].length - 1];
		}
	 
	 private static int test() {
		int[][] arr = {{1,3,1,3},{1,5,1,3},{4,2,1,6}};
		int[] last = new int[arr[0].length];
		last[0]=1;
		for(int i = 1; i < last.length; i++) {
			last[i] = Integer.MAX_VALUE;
		}
		for(int i = 0; i < arr.length; i++) {
			if(i > 0) {
				last[0] += arr[i][0];
			}
			for(int j = 1; j < last.length; j++) {
				last[j] = arr[i][j] + ( last[j - 1] < last[j] ? last[j - 1] : last[j]);
			}
			for(int s : last) {
				System.out.print(s + ",");
			}
			System.out.println();
		}
		return last[arr[0].length - 1];
	}
}
