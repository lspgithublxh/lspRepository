package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的动态规划，还没遇到---树的高度可以用。二叉树的高度---f(i) = max{left:f(i), right:f(i)}
 *
 *@author lishaoping
 *Client
 *2020年7月8日
 */
public class ListToEr_tree_109 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		int start;
		int end;
		public Node(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}
		
	}
	
	private static void test() {
		int[] arr = {-10, -3, 0, 5, 9,10,11,12,13,14,15};
		int len = arr.length - 1;
		 len = len>>1 | len;
		 len = len>>2 | len;
		 len = len>>4 | len;
		 len = len>>8 | len;
		 len = len>>16 | len;
		 len = len + 1;
		 System.out.println(len);
		Integer[] tarr = new Integer[len*2];
		List<Node> st = new ArrayList<>();
		st.add(new Node(0, arr.length - 1));
		int index = 0;
		while(!st.isEmpty()) {
			Node a = st.remove(0);
			int nextPos = index++;
			if(a.start == -1) {
				tarr[nextPos] = null;
				continue;
			}
			//中点
			int cen = (a.start + a.end) / 2;
			if(a.start == a.end) {
				tarr[nextPos] = arr[a.start];
				st.add(new Node(-1, -1));//站位
				st.add(new Node(-1, -1));//站位
			}else if(a.start == cen) {//两个元素
				tarr[nextPos] = arr[a.start];
				st.add(new Node(-1, -1));//站位
				st.add(new Node(a.start+1, a.end));
			}else {//三个以上
				tarr[nextPos] = arr[cen];
				st.add(new Node(a.start, cen - 1));
				st.add(new Node(cen+1, a.end));
			}
		}
		String rs = "";
		for(Integer s : tarr) {
			rs += s + ",";
		}
		while(rs.endsWith("null,")) {
			rs = rs.substring(0, rs.lastIndexOf("null"));
		}
		System.out.println(rs);
	}
}
