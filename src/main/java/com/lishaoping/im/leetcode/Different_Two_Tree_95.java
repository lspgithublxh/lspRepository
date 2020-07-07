package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Different_Two_Tree_95 {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		//形成组合：
		int n = 3;
		List<String> old = new ArrayList<>();
		List<String> next = new ArrayList<>();
		old.add("1");
		for(int i = 2; i <= n; i++) {
			for(String o : old) {
				String[] zhuhe = o.split(",");
				for(int j = 0; j <= zhuhe.length; j++) {
					String ns = add(zhuhe, j, i);
					next.add(ns);
				}
			}
			List<String> tmp = old;
			old = next;
			next = tmp;
			next.clear();
		}
		old.forEach(System.out::println);
		//组合转数组
		System.out.println("----------------------------");
		List<String> rs = new ArrayList<>();
		Set<String> s = new HashSet<>();
		for(String item : old) {
			String arr = "";
			String[] v = item.split(",");
			int index = 1;
			Map<String, Integer> pathVl = new HashMap<>();
			pathVl.put("0", Integer.valueOf(v[0]));
			while(index < v.length) {
				int cv = Integer.valueOf(v[index++]);
				String path = "0";
				Integer pv = pathVl.get(path);
				while(pv != null) {
					if(pv < cv) {
						path = path + "1";
					}else {
						path += "0";
					}
					pv = pathVl.get(path);
				}
				pathVl.put(path, cv);
			}
			//放入数组
			//从arr中确定上一层的所有非空元素-及其路径，确定arr开始添加位置；
			String path = "0";
			arr += pathVl.get(path);
			int start = 1;
			int len = 1;
			while(true) {
				TreeSet<String> keys = new TreeSet<>();
				for(String k : pathVl.keySet()) {
					if(k.length() == len) {
						keys.add(k);
					}
				}
				if(keys.isEmpty()) {
					break;
				}
				for(String k : keys) {
					Integer kvl = pathVl.get(k + "0");
					Integer kvr = pathVl.get(k + "1");
					if(kvl != null || kvr != null) {
//						arr[start++]=kvl;
//						arr[start++]=kvr;
						arr += "," + kvl;
						arr += "," + kvr;
					}
				}
				len++;
			}
			arr = arr.endsWith("null") ? arr.substring(0, arr.lastIndexOf(",")) : arr;
			if(s.contains(arr)) {
				continue;
			}
			rs.add(arr);
			s.add(arr);
		}
		rs.forEach(item ->{
			
			System.out.println(item);
		});
		//数组转结构
	}

	private static String add(String[] zhuhe, int j, int i) {
		String rs = "";
		for(int k = 0; k < j; k++) {
			rs = rs + zhuhe[k] + ",";
		}
		rs = rs + i;
		for(int m = j; m < zhuhe.length; m++) {
			rs = rs + "," + zhuhe[m];
		}
		return rs;
	}
}
