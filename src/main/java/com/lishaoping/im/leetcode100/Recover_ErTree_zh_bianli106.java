package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

import com.lishaoping.im.leetcode100.Recover_ErTree_From_bianli105.Node;

public class Recover_ErTree_zh_bianli106 {

	public static void main(String[] args) {
		recover();
	}
	
	static class Node{
		int v;
		Node left;
		Node right;
		public Node(int v) {
			super();
			this.v = v;
		}
		
	}
	
	private static void recover() {
		//恢复
		int[] houxu = {9,15,7,20,3};
		int[] zhongxu = {9,3,15,20,7};
		int hindex = 0;
		int zindex = 0;
		int hend = houxu.length - 1;
		int zend = zhongxu.length - 1;
		Node root = queding(houxu, zhongxu, hindex,hend, zindex,zend, new Node(houxu[houxu.length - 1]), null);
		//双队列打印二叉树
		printTree(root);
	}

	private static void printTree(Node root) {
		List<Node> left = new ArrayList<>();
		List<Node> right = new ArrayList<>();
		left.add(root);
		while(!left.isEmpty()) {
			while(!left.isEmpty()) {
				Node e = left.remove(0);
				System.out.print((e.left == null ? null : e.left.v) + "--" + e.v + "--" + (e.right == null ? null : e.right.v) +", ");
				if(e.left != null) {
					right.add(e.left);
				}
				if(e.right != null) {
					right.add(e.right);
				}
			}
			System.out.println();
			left.addAll(right);
			right.clear();
		}
		
	}

	private static Node queding(int[] houxu, int[] zhongxu, int hindex, int hend, int zindex, int zend, Node parent, Node root) {
		if(root == null) {
			root = parent;
		}
		if(hindex < 0 || hend >= houxu.length || zindex < 0 || zend >= zhongxu.length) {
			return root;
		}
		int b = houxu[hend];
		int pos = find(zhongxu, b);
		if(pos == zend) {
			//没有右半边
			if(pos > zindex) {//
				int left = houxu[hend - 1];
				parent.left = new Node(left);
				queding(houxu, zhongxu, hindex,hend-1, zindex, zend - 1, parent.left, root);
			}
			return root;
		}
		
		if(pos == zindex) {
			//没有左半边
			if(pos < zend) {
				int right = houxu[hend - 1];
				parent.right = new Node(right);
				queding(houxu, zhongxu, hindex, hend-1, zindex+1, zend, parent.right, root);
			}
			return root;
		}
		
		int n = zhongxu[pos + 1];
		int pos2 = find(houxu, n);
		int right = houxu[hend - 1];
		parent.right = new Node(right);
		
		int left = houxu[pos2 - 1];
		
		parent.left = new Node(left);
		
		
		queding(houxu, zhongxu, pos2, hend - 1, pos+1, zend, parent.right, root);
		queding(houxu, zhongxu, hindex,pos2-1, zindex, pos-1, parent.left, root);
		
		return root;
	}

	private static int find(int[] qianxu, int end) {
		int i = 0;
		for(int item : qianxu) {
			if(item == end) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
}
