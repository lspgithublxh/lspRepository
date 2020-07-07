package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Qujian_hebing {

	public static void main(String[] args) {
		int[][] quj = {{1,4},{2,5},{6,9},{8,10}};
		merge(quj);
	}
	
	static class Node{
		public Node(int i, boolean j) {
			v = i;
			start = j;
		}
		int v;
		boolean start;
		@Override
		public String toString() {
			return start + " :" + v;
		}
	}

	private static void merge(int[][] quj) {
		int[][] s = new int[quj.length][2];
		List<int[]> li = new ArrayList<>();
		List<Node> list = new ArrayList<>();
		for(int[] item : quj) {
			list.add(new Node(item[0], true));
			list.add(new Node(item[1], false));
		}
		Collections.sort(list, (item1, item2)->{
			return item1.v - item2.v;
		});
		System.out.println(list);
		int count = 0;
		int start = -1;
		int end = -1;
		boolean fist = true;
		for(Node n : list) {
			if(n.start) {
				count++;
				if(fist) {
					start = n.v;
					fist = false;
				}
			}else {
				count--;
				end = n.v;
			}
			if(count == 0) {
				//
				li.add(new int[] {start, end});
				fist = true;
			}
		}
		li.forEach(item ->{
			System.out.println(item[0] + "," + item[1]);
		});
	}
}
