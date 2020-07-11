package com.lishaoping.im.leetcode100;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaxNum_179 {

	public static void main(String[] args) {
		ttt();
	}

	private static void ttt() {
		int[] arr = {3,30,34,5,9};
		for(int i =0; i < arr.length; i++) {
			for(int j = i+1; j < arr.length; j++) {
				if(!compare(arr[i], arr[j])) {
					int t1 = arr[i];
					arr[i] = arr[j];
					arr[j] = t1;
				}
			}
		}
//		List<String> li = Arrays.asList(arr).stream().map(i -> i+"").collect(Collectors.toList());
//		System.out.println(li);
		for(int s : arr) {
			System.out.print(s + "");
		}
//		System.out.println(String.join("", li));j
		
	}

	private static boolean compare(int i, int j) {
		String n1 = i + "";
		String n2 = j + "";
		int index = 0;
		while(true) {
			char c1 = n1.charAt(index);
			char c2 = n2.charAt(index);
			if(c1 > c2) {
				return true;
			}else if(c1 < c2) {
				return false;
			}else {
				index++;
				if(index >= n1.length() && index >= n2.length()) {
					return true;
				}
				if(index >= n1.length() || index >= n2.length()) {
					n1 += n2;
					n2 += n1;
				}
			}
		}
	}
}
