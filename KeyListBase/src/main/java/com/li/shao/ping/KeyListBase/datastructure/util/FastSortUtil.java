package com.li.shao.ping.KeyListBase.datastructure.util;

import com.google.common.primitives.Ints;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月20日
 * @file FastSortUtil
 */
public class FastSortUtil {

	public void sort(int[] arr, int left, int right) {
		if(left >= right) {
			return;
		}
		int jizhun = arr[right];
		int iniLeft = left;
		int intRight = right;
		boolean turnLeft = true;
		while(left != right) {
			if(turnLeft) {
				if(arr[left] > jizhun) {
					arr[right] = arr[left];
					turnLeft = !turnLeft;
					right--;
				}else {
					left++;
				}
				
			}else {
				if(arr[right] < jizhun) {
					arr[left] = arr[right];
					turnLeft = !turnLeft;
					left++;
				}else {
					right--;
				}
				
			}
		}
		arr[left] = jizhun;
		if(left > 0) {
			sort(arr, iniLeft, left - 1);
		}
		if(left < arr.length - 1) {
			sort(arr, left + 1, intRight);
		}
		
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10 ;i++) {
			int[] s = new int[10];
			for(int j = 0; j < 10; j++) {
				s[j] = (int) (Math.random() * 10);
			}
			new FastSortUtil().sort(s,0,s.length - 1);
			System.out.println(Ints.asList(s));
		}
		
		
	}
}
