package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class DNA_times_187 {

	public static void main(String[] args) {
		ttt();
	}

	private static void ttt() {
		String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
		int[] f = new int[s.length() - 20 + 1];
		int index = 19;
		int i = 0;
		List<String> rs = new ArrayList<>();
		while(index < s.length()) {
			String target = s.substring(index - 10 + 1, index+1);
			String rest  = s.substring(0, index - 10 + 1);
			if(rest.contains(target)) {
				f[i] = (i == 0 ? 1 : (f[i - 1] + 1));
				rs.add(target);
			}else {
				f[i] = f[i-1];
			}
			i++;
			index++;
		}
		System.out.println(f[f.length - 1]);
		System.out.println(rs);
	}
}
