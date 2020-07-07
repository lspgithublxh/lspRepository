package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cnk {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		List<List<String>> old = new ArrayList<>();
		List<List<String>> next = new ArrayList<>();
		int n = 5;
		int k = 5;
		for(int i = 0; i <= k; i++) {
			old.add(new ArrayList<>());
			next.add(new ArrayList<>());
		}
		for(int i = 1; i <= n; i++) {
			final int ii = i;
			old.get(0).clear();
			old.get(0).add("");
			for(int j = 1; j <= i && j <= k; j++) {
				next.get(j).clear();
				next.get(j).addAll(j >= old.size() ? new ArrayList<>() : old.get(j));
				List<String> less = j - 1 >= old.size() ? new ArrayList<>(): old.get(j - 1);
				List<String> collect = less.stream().map(item -> item +","+ ii).collect(Collectors.toList());
				next.get(j).addAll(collect);
			}
			List<List<String>> tem = old;
			old = next;
			next = tem;
		}
		old.forEach(System.out::println);
	}
}
