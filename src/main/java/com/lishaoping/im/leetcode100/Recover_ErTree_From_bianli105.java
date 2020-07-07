package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Recover_ErTree_From_bianli105 {

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
		int[] qianxu = {3,9,20,15,7};
		int[] zhongxu = {9,3,15,20,7};
		int qindex = 0;
		int zindex = 0;
		Node root = queding(qianxu, zhongxu, qindex, zindex, null, null);
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

	private static Node queding(int[] qianxu, int[] zhongxu, int qindex, int zindex, Node parent, Node root) {
		if(qindex >= qianxu.length || zindex >= zhongxu.length) {
			return root;
		}
		//确定当前路径，
		int end = zhongxu[zindex];
		int index = find(qianxu, end);
		Node cur = null;
		if(end == qianxu[qindex]) {
			//无左分支，是右节点
			
			Node n = new Node(qianxu[qindex]);
			if(root == null) {
				root = n;
			}else {
				parent.right = n;
			}
			parent = n;
			if(qindex + 1 >= qianxu.length || zindex + 1 >= zhongxu.length) {
				return root;
			}
			int nextStart = qianxu[qindex + 1];
			int nextEnd = zhongxu[zindex + 1];
			queding(qianxu, zhongxu, nextStart, nextEnd, parent, root);
			return root;
		}else {
			//构造路径链
			Node n = constructPath(qianxu, qindex, index);
			cur = n;
			if(parent == null) {
				parent = n;
				root = n;
			}else {
				parent.right = n;
			}
		}
		//确定下一次路径的起点和终点
		int nextStart = index + 1;
		int nextEnd = -1;
		int p1 = index - 1;
		int p2 = zindex + 1;
		while(p2 < zhongxu.length && p1 >= 0) {
			if(qianxu[p1] != zhongxu[p2]) {
				break;
			}else {
				p1--;
				p2++;
			}
		}
		if(p2 == zhongxu.length) {//则不存在下一个分支
			return root;
		}
		//断定：上述while一定可以找到---除非没有
		nextEnd = p2;
		int pv = zhongxu[p2-1];
		//寻找Pv对应的节点
		Node np = findx(cur, pv);
		queding(qianxu, zhongxu, nextStart, nextEnd, np, root);
		return root;
	}

	private static Node findx(Node root, int pv) {
		Node end = root;
		while(end != null) {
			if(end.v == pv) {
				return end;
			}else {
				end = end.left;
			}
		}
		return null;
	}

	private static Node constructPath(int[] qianxu, int qindex, int index) {
		Node n = new Node(qianxu[qindex]);
		Node cur = n;
		while(qindex < index) {
			cur.left = new Node(qianxu[++qindex]);
			cur = cur.left;
		}
		return n;
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
