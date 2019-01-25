package com.bj58.sort;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PaixuMap {

	public static void main(String[] args) {
		Map<String, Integer> map = new TreeMap<>();
//		Collections.sort(, null);
		Map<Integer, String> map2 = new TreeMap<>();
		map2.put(1, "11,12");
		map2.put(4, "122");
		map2.put(3, "2");
		map2.put(13, "2");
		map2.put(113, "2");
		map2.put(23, "2");
		map2.put(33, "2");
		System.out.println(map2);
		for(Entry<Integer, String> entry : map2.entrySet()) {
			System.out.println(entry.getKey() + "   " + entry.getValue());
		}
		
	}
}
