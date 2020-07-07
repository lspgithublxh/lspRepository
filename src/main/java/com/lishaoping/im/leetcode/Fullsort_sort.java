package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Fullsort_sort {

	public static void main(String[] args) {
		test();
	}
	private static void test() {
		int[] arr = {1,2,3,4,5,6,7,8,9};
		List<String> old = new ArrayList<>();
		List<String> next = new ArrayList<>();
		List<String> last = new ArrayList<>();
		old.add("" + arr[0]);
		for(int i = 1; i < arr.length; i++) {
			int candicate = arr[i];
			for(String s : old) {
				int index = s.length();
				String rs = "";
				for(;index > 0;) {
					rs = s.substring(0, index) + candicate + s.substring(index--);
					next.add(rs);
				}
			}
			List<String> tmp = old;
			old = next;
			next = tmp;
			if(i == arr.length - 2) {
				last.addAll(next);
			}
			next.clear();
		}
		
		for(String s : last) {
			old.add(arr[arr.length -1] + s);
		}
		System.out.println(old.size());
//		old.forEach(System.out::println);
		for(int i = 0 ; i < 100; i++) {
			System.out.println(old.get(i));
		}
	}
}
