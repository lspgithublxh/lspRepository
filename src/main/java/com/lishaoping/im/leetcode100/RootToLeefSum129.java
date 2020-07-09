package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class RootToLeefSum129 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		int total;

		public Node(int total) {
			super();
			this.total = total;
		}
		
		
	}
	
	private static void test() {
		Integer[] arr = {1,2,3};
		List<Node> list = new ArrayList<>();
		List<Node> next = new ArrayList<>();
		list.add(new Node(arr[0]));
		int start = 1;
		int end = 2;
		while(start < arr.length) {
			for(int i = 0; i < list.size() ; i++ ) {
				Node n = list.get(i);
				Integer v1 = arr[start];
				Integer v2 = arr[start+1];
				if(v1 != null) {
					next.add(new Node(n.total*10 + v1));
				}
				if(v2 != null) {
					next.add(new Node(n.total*10 + v2));
				}
				start += 2;
				if(start >= arr.length) {
					break;
				}
			}
			List<Node> tmp = list;
			list = next;
			tmp.clear();
			next = tmp;
		}
		System.out.println(list.stream().map(n -> n.total).reduce(0, (item1, item2) -> item1+item2));
	}
}
