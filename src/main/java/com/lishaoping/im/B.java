package com.lishaoping.im;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B {

	public static void main(String[] args) {
//		int[] i = {0}; 
//		Thread t1 = new Thread(()->{
//			for(;;) {
//				if(i[0] % 2 == 0) {
//					System.out.println("1");
//					i[0]++;
//				}
//			}
//		}) ;
//		Thread t2 = new Thread(()->{
//			for(;;) {
//				if(i[0] % 2 == 1) {
//					System.out.println("2");
//					i[0]++;
//				}
//			}
//		}) ;
//		t1.start();
//		t2.start();
		
		
		//
//		Arrays.asList(new ArrayList());
		Arrays.asList(new String[] {"cc"});//
		
		int[]sx = new int[] {4,5,61,2,3,8,7,6,4,7,11,112,223};
		int s = findKth(sx, 5);
		System.out.println();
		System.out.println(s);
		for(int x :sx) {
			System.out.print("," + x);
		}
	}
	
	public static int findKth(int[] arr, int k){
		int[] temp = new int[k];
		int j = 0;
		int max = 0;
		for(int i = 0; i < arr.length; i++) {
			int a = arr[i];
			if((a & 0x1) == 1) {
				if(j < temp.length) {
					temp[j] = a;
					if(temp[max] < a) {
						max = j;
					}
					
				}else {
					if(temp[max] > a) {
						temp[max] = a;
					}
					//重新寻找最大的：
					for(int i2 = 0; i2 < temp.length; i2++) {
						if(temp[i2] > temp[max]) {
							 max = i2;
						}
					}
				}
				j++;
			}
		}
		return temp[max];
	}
	
	
	public static int findKth2(int[] arr, int k){
		int count = 0;
		for(int i = 0; i < arr.length; i++) {
			int a = arr[i];
			if((a & 0x1) == 1) {
				int temp = arr[count++];
				arr[count - 1] = a;
				arr[i] = temp;
			}
		}
		//类似快排
		sort(arr, 0, count - 1, k - 1);
		return arr[k-1];
		

	}

	private static void sort(int[] arr, int start, int end, int k) {
		if(start >= end) {
			return;
		}
		if(start < 0 || end >= arr.length) {
			return;
		}
		int right = end;
		int left = start;
		int temp = arr[end];
		boolean dir_left = true;
		while(left < right) {
			if(dir_left) {
				if(arr[left] > temp) {
					arr[right] = arr[left];
					right--;
					dir_left = false;
				}else {
					left++;
				}
			}else {
				if(arr[right] < temp) {
					arr[left] = arr[right];
					left++;
					dir_left = true;
				}else {
					right--;
				}
			}
		}
		arr[left] = temp;
		if(left == k) {
			return;
		}
		sort(arr, start, left - 1, k);
		if(left == k) {
			return;
		}
		sort(arr, right+1, end, k);
	}
}
