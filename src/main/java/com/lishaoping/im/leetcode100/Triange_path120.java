package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class Triange_path120 {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		int[][] arr = {
				{2},
				{3,4},
				{6,5,7},
				{4,1,8,3}
		};
		//test21-- 
		int row = 1;
		int[] prepri = {arr[0][0]};
		while(row < arr.length) {
			int[] curpri = new int[arr[row].length];
			for(int i = 0; i < arr[row].length; i++) {
				int num = arr[row][i];
				int up_1 = i >= prepri.length ? Integer.MAX_VALUE: prepri[i];
				int up_0 = i - 1 < 0 ? Integer.MAX_VALUE : prepri[i-1];
				int price = up_1 > up_0 ? up_0 + num : up_1 + num;
				curpri[i]=price;
			}
			prepri = curpri;
			row++;
		}
		for(int i = 0; i < prepri.length; i++) {
			System.out.println("k:" + arr[arr.length - 1][i] + "v:" + prepri[i]);
		}
	}
}
