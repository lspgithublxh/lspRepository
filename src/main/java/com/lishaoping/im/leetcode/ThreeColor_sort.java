package com.lishaoping.im.leetcode;

public class ThreeColor_sort {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		int[] arr = {0,1,2,0,1,2,2,1,0,0,1,2,0,1};
		int left = 0;
		int right = arr.length - 1;
		for(int i = 0; i < arr.length;) {
			if(arr[i] == 0) {
				while(arr[left] == 0) {
					left++;
				}
				if(left < i) {
					int tem = arr[left];
					arr[left] = arr[i];
					arr[i] = tem;
				}else {
					i = left;//无需浪费时间
				}
			}else if(arr[i] == 2) {
				while(arr[right] == 2) {
					right--;
				}
				if(right > i) {
					int tem = arr[right];
					arr[right] = arr[i];
					arr[i] = tem;
				}else {
					break;
				}
				
			}else {
				i++;
			}
		}
		for(int a : arr) {
			System.out.print(a + ",");
		}
	}
}
