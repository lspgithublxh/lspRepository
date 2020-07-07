package com.lishaoping.im.leetcode;

public class SearchErwei {

	public static void main(String[] args) {
		//两次二分搜索
		boolean rs = test();
		System.out.println(rs);
	}

	private static boolean test() {
		int[][]  arr=   {{1,2,3,4},{5,6,7,8},{10,11,12,13}};
		int target = 13;
		int left = 0;
		int right = arr.length - 1;
		int[] next = null;
		while(arr[left][0] <= target && arr[right][0] > target) {
			//继续缩小范围
			if(right - left == 1) {
				next = arr[left];
				break;
			}
			int center = (right + left) / 2;
			if(arr[center][0] >= target) {
				left = center;
			}else {
				right = center;
			}
		}
		if(next == null) {
			if(target >= arr[right][0]) {
				next = arr[right];
			}
		}
		if(next != null) {
			left = 0;
			right = next.length - 1;
			while(next[left] <= target && next[right] >= target) {
				if(next[left] == target || next[right] == target) {
					return true;
				}
				if(right - left <= 1) {
					return false;
				}
				int center = (right + left) / 2;
				if(next[center] > target) {
					right = center;
				}else {
					left = center;
				}
			}
		}
		return false;
	}
}
