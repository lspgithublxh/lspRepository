package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * f[i,v]=f[i-1,v]+f[i-1,v-ci]
 *
 *@author lishaoping
 *Client
 *2020年7月4日
 */
public class Beibao2 {

	public static void main(String[] args) {
		test();
		list();
	}

	private static void list() {
		int[] candicates = {10,1,2,7,6,1,5};//最简单处理就是最后打印时候才处理去重--集合法
		int target = 8;
		List<HashSet<String>> list = new ArrayList<>(target+1);
		for(int i = 0; i < target+1;i++) {
			list.add(new HashSet<>());
		}
		for(int i = 0; i < candicates.length; i++) {
			int candicate = candicates[i];
			list.get(0).add("");//
			
			for(int j = list.size()-1; j >= candicate; j--) {//从后往前算
				Set<String> source = list.get(j - candicate);
				if(!source.isEmpty()) {
					List<String> nw = new ArrayList<>();
					for(String s : source) {
						nw.add(s + "+" + candicate);
					}
					list.get(j).addAll(nw);
				}
			}
			
		}
		list.get(target).forEach(it ->{
			System.out.println(it);
		});
		System.out.println(list.get(target).size());
	}

	private static void test() {
		int[] candicates = {10,1,2,7,6,1,5};
		int target = 8;
		int[] f = new int[target + 1];
		f[0]=1;
		for(int i = 0; i < candicates.length; i++) {
			int candicate = candicates[i];
			for(int j = f.length-1; j >= candicate; j--) {
				f[j] = f[j] + f[j - candicate];
			}
		}
		System.out.println(f[target]);
	}
}
