package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class Er_Tree_foreach102 {

	public static void main(String[] args) {
		test();
		test_103();
	}

	private static void test_103() {
		Integer[] arr = {3,9,20,null,null,15,7};
		List<String> li = new ArrayList<>();
		li.add("" + arr[0]);
		int nextConNum = 1;
		int index = 1;
		boolean leftToRight = false;
		while(index < arr.length) {
			int count = 0;
			String v = "";
			int start = index;
			if(leftToRight) {
				for(; index < start + nextConNum*2;index++) {
					if(arr[index] != null) {
						v += arr[index] + ",";
						count++;
					}
				}
				leftToRight = !leftToRight;
			}else {
				for(int i = start + nextConNum*2 - 1; i >= start;i--) {
					if(arr[i] != null) {
						v += arr[i] + ",";
						count++;
					}
				}
				index = start + nextConNum*2;
				leftToRight = !leftToRight;
			}
			
			li.add(v);
			nextConNum = count;
		}
		li.forEach(System.out::println);
	}

	private static void test() {
		Integer[] arr = {3,9,20,null,null,15,7};
		List<String> li = new ArrayList<>();
		li.add("" + arr[0]);
		int nextConNum = 1;
		int index = 1;
		while(index < arr.length) {
			int count = 0;
			String v = "";
			int start = index;
			for(; index < start + nextConNum*2;index++) {
				if(arr[index] != null) {
					v += arr[index] + ",";
					count++;
				}
			}
			li.add(v);
			nextConNum = count;
		}
		li.forEach(System.out::println);
	}
}
