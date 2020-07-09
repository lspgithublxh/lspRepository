package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Huiwen_string_spli131 {

	public static void main(String[] args) {
//		tt();
		tt2();
	}

	private static void tt() {
		String s = "aab";
		int[] f = new int[s.length()];
		f[0] = 1;
		for(int i = 1; i < s.length(); i++) {
			for(int j = i-1; j >= -1; j--) {
				String sub = s.substring(j+1, i+1);
				if(ishuiwen(sub)) {
					f[i] += j == -1 ? 1 : f[j];
				}
			}
		}
		for(int t : f) {
			System.out.println("g:" + t);
		}
	}

	private static boolean ishuiwen(String sub) {
		int start = 0;
		int end = sub.length() - 1;
		while(start < end) {
			if(sub.charAt(start++) != sub.charAt(end--)) {
				return false;
			}
		}
		return true;
	}
	
	private static void tt2() {
		String s = "aab";
		int[] f = new int[s.length()];
		f[0] = 1;
		Map<Integer, List<String>> li = new HashMap<>();
		for(int i = 1; i < s.length(); i++) {
			for(int j = i-1; j >= -1; j--) {
				String sub = s.substring(j+1, i+1);
				if(ishuiwen(sub)) {
					f[i] += j == -1 ? 1 : f[j];
					List<String> list = li.get(i);
					if(list == null) {
						list = new ArrayList<>();
						li.put(i, list);
					}
					List<String> list2 = null;
					if(j == -1) {
						list.add(s.substring(0, i+1));
						continue;
					}else {
						list2 = li.get(j);
					}
					if(j == 0) {
						list2 = new ArrayList<>();
						list2.add(s.charAt(0) + "");
						li.put(j, list2);
					}
					for(String x : list2) {
						list.add(x + "-" + sub);
					}
				}
			}
		}
		for(int t : f) {
			System.out.println("g:" + t);
		}
		li.forEach((k,v) ->{
			System.out.println("k:" + k + "  , v:" + v);
		});
	}
}
