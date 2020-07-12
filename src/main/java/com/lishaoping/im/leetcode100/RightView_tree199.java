package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class RightView_tree199 {

	public static void main(String[] args) {
		ttt();
	}

	private static void ttt() {
		Integer[] arr = {1,2,3,null,5,null,4};
		int start = 0;
		int end = 0;
		List<Integer> see = new ArrayList<>();
		while(start < arr.length) {
			//本行最后一个元素的相对位置
			int pos = -1;
			for(int i = end; i>=start; i--) {
				if(arr[i] != null) {
					pos = i;
					see.add(arr[i]);
					break;
				}
			}
			if(pos == -1) {
				System.out.println("error");
				break;
			}
			int len = pos - start + 1;
			int next_start = end + 1;
			int next_end = end + 2*len;
			start = next_start;
			end = next_end;
			if(end >= arr.length) {
				end = arr.length - 1;
			}
		}
		System.out.println(see);
	}
}
