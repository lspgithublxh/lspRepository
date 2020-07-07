package com.lishaoping.im.leetcode;

public class Search_xuanzhuan_arr81 {

	public static void main(String[] args) {
		boolean rs = search();
		System.out.println(rs);
	}

	private static boolean search() {
		int[] arr = {4,5,6,7,8,1,2,3};
		int target = 71;
		int left = 0;
		int right = arr.length - 1;
		while(left < right) {
			if(arr[left] == target || arr[right] == target) {
				return true;
			}
			if(right - left == 1) {
				break;
			}
			int center = (left + right) / 2;
			if(arr[center] >= arr[0]) {
				if(target <= arr[center]) {
					//×ó±ßÓÐÐòËÑË÷
					return searchSort(arr, left, center, target);
				}else {
					//ÓÒ±ß
					left = center;
					//¼ÌÐøËÑËÑ
				}
			}else {
				if(target <= arr[arr.length - 1]) {
					//ÓÒ±ßÓÐÐòËÑË÷
					return searchSort(arr, center, right, target);
				}else {
					//¼ÌÐøËÑË÷
					right = center;
				}
			}
		}
		return false;
	}

	private static boolean searchSort(int[] arr, int left, int right, int target) {
		while(left < right) {
			if(arr[left] == target || arr[right] == target) {
				return true;
			}
			if(right - left == 1) {
				break;
			}
			int center = (left + right) / 2;
			if(arr[center] > target) {
				right = center;
			}else {
				left = center;
			}
		}
		return false;
	}
}
