package com.lishaoping.im.leetcode100;

import java.util.HashMap;
import java.util.Map;

public class Copy_deep_list138 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		int v;
		Node next;
		Node random;
		public Node(int v) {
			super();
			this.v = v;
		}
		
		
	}
	
	private static void test() {
		Node root = new Node(1);
		Node n1 = new Node(2);
		Node n2 = new Node(3);
		Node n3 = new Node(4);
		Node n4 = new Node(5);
		root.next = n1;
		root.random = n3;
		n1.next = n2;
		n1.random = n1;
		n2.next = n3;
		n2.random = n4;
		n3.next = n4;
		n3.random = n1;
		n4.random = n2;
		//开始复制
		Map<Integer, Node> numMap = new HashMap<>();
		Map<Node, Integer> nodeMap = new HashMap<>();
		int num = 0;
		Node cur = root;
		Node cp = new Node(cur.v);
		Node cpCur = cp;
		numMap.put(num, cpCur);
		nodeMap.put(cur, num);
		while(cur != null) {
			//复制next
			num++;
			if(cur.next != null) {
				cpCur.next = new Node(cur.next.v);
				cpCur = cpCur.next;
				numMap.put(num, cpCur);
				nodeMap.put(cur.next, num);
			}
			cur = cur.next;
		}
		cur = root;
		cpCur = cp;
		while(cur != null) {
			Integer numb = nodeMap.get(cur.random);
			if(numb != null) {
				Node node = numMap.get(numb);
				cpCur.random = node;
			}
			cur = cur.next;
			cpCur = cpCur.next;
		}
		System.out.println("cp ok:");
	}
}
