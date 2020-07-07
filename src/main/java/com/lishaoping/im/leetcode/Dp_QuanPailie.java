package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * f[i] = h(f[i-1]);
 *
 *@author lishaoping
 *Client
 *2020Äê7ÔÂ5ÈÕ
 */
public class Dp_QuanPailie {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		List<String> old = new ArrayList<>();
		List<String> next = new ArrayList<>();
		old.add("" + arr[0]);
		for(int i = 1; i < arr.length; i++) {
			int candicate = arr[i];
			for(String s : old) {
				int index = 0;
				String rs = "";
				for(;index <= s.length();) {
					rs = s.substring(0, index) + candicate + s.substring(index++);
					next.add(rs);
				}
			}
			List<String> tmp = old;
			old = next;
			next = tmp;
			next.clear();
		}
		System.out.println(old.size());
//		old.forEach(System.out::println);
	}
}
