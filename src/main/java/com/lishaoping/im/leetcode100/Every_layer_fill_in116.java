package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class Every_layer_fill_in116 {

	public static void main(String[] args) {
		tsest();
	}

	static class Node{
		int v;
		Node left;
		Node right;
		Node next;
		public Node(int v, Node left, Node right) {
			super();
			this.v = v;
			this.left = left;
			this.right = right;
		}
		public Node() {
			super();
		}
		
		
	}
	
	private static void tsest() {
		//gouz---深度复制法：
		//双队列构造法：
		List<Node> left = new ArrayList<>();
		List<Node> right = new ArrayList<>();
		Node root = new Node();
		left.add(root);
		int start = 0;
		while(!left.isEmpty()) {
			while(!left.isEmpty()) {
				Node cur = left.remove(0);
				cur.v = start++;
				cur.left = new Node();
				cur.right = new Node();
				right.add(cur.left);
				right.add(cur.right);
			}
			left.addAll(right);
			right.clear();
			if(start == 32 -1) {
				break;
			}
		}
		//同层打印
		//先不
		Node pstart = root;
		Node pyou = root;
		Node curyou = null;
		while(pyou != null) {
			pstart = null;
			while(pyou != null) {
				if(curyou == null) {
					if(pyou.left != null) {
						curyou = pyou.left;
						pstart = curyou;
						if(pyou.right != null) {
							curyou.next = pyou.right;
							curyou = curyou.next;
						}
					}else if(pyou.right != null) {
						curyou = pyou.right;
						pstart = curyou;
					}
				}else {
					if(pyou.left != null) {
						curyou.next = pyou.left;
						curyou = curyou.next;
					}
					if(pyou.right != null) {
						curyou.next = pyou.right;
						curyou = curyou.next;
					}
				}
				pyou = pyou.next;
			}
			//
			curyou = null;
			pyou = pstart;
		}
		//遍历
		pstart = root;
		curyou = root;
		while(pstart != null) {
			pstart = null;
			while(curyou != null) {
				System.out.print(" " + curyou.v + ",");
				if(curyou.left != null && pstart == null) {
					pstart = curyou.left;
				}else if(curyou.right != null && pstart == null) {
					pstart = curyou.right;
				}
				curyou = curyou.next;
			}
			System.out.println();
			curyou = pstart;
		}
		
	}
}
