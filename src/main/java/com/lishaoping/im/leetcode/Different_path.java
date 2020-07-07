package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * dp 不同路径
 *
 *@author lishaoping
 *Client
 *2020年7月5日
 */
public class Different_path {

	public static void main(String[] args) {
		int zhong = test();
		System.out.println(zhong);
		int zhong2 = test2();
		System.out.println(zhong2);
	}

	private static int test() {
		int row = 3;
		int col = 7;
		int[] last = new int[col];
		last[0]=1;
		for(int i = 0; i < row; i++) {
			for(int j = 1; j < last.length; j++) {
				last[j] = last[j - 1] + last[j];
			}
		}
		return last[col - 1];
	}
	
	private static int test2() {//有障碍物
//		int[][] zhang = {{2,3},{5,6}};
		List<String> zhang = new ArrayList<>();
		zhang.add("2,3");
		zhang.add("3,4");
		int row = 3;
		int col = 7;
		int[] last = new int[col];
		last[0]=1;
		for(int i = 0; i < row; i++) {
			for(int j = 1; j < last.length; j++) {
				if(zhang.contains(i + "," + j)) {
					 last[j - 1] = 0;
				}
				if(zhang.contains(i + "," + j)) {
					last[j] = 0;
				}else {
					last[j] = last[j - 1] + last[j];
				}
				
			}
		}
		return last[col - 1];
	}
}
