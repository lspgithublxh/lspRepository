package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Gree_code89 {

	public static void main(String[] args) {
		xtest();
	}

	private static void xtest() {
		List<String> old = new ArrayList<>();
		List<String> next = new ArrayList<>();
		old.add("0");
		old.add("1");
		int n = 5;
		int i = 0;
		while(++i < 5) {
			for(int j = 0; j + 1 < old.size(); j+=2 ) {
				String item = old.get(j);
				String item2 = old.get(j+1);
				next.add("0" + item);
				next.add("1" + item);
				next.add("1" + item2);
				next.add("0" + item2);
			}
			List<String> tmp = old;
			old = next;
			next = tmp;
			next.clear();
		}
		old.forEach(item ->{
			System.out.println(item);
		});
	}
}
