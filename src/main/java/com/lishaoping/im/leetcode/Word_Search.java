package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Word_Search {

	public static void main(String[] args) {
		test();
	}
	
	static class Node{
		int i ;
		int j ;
	    char v;
		Node parent;
		List<Node> child = new ArrayList<>();
		public Node(int i, int j, char v, Node parent){
			this.i = i;
			this.j = j;
			this.v = v;
			this.parent = parent;
		}
	}

	private static void test() {
		char[][] arr = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
		String word = "ABCCED";
		char c = word.charAt(0);
		//根节点：就是parent==null的节点
		List<Node> ij = findFisrt(arr, c);
		for(Node root : ij) {
			List<String> currPath = new ArrayList<>();
			currPath.add(root.i +","+ root.j);
			findNextNode(root, currPath, arr, word, 1);
			System.out.println(currPath);
		}
	}

	private static void findNextNode(Node root, List<String> currPath, char[][] arr , String word, int index) {
		if(index >= word.length()) {
			return;
		}
		//寻找root全部可达的节点
		int nexti = root.i + 1;
		int nexti2 = root.i - 1;
		
		int nextj = root.j + 1;
		int nextj2 = root.j - 1;
		boolean hasAdd = false;
		Node nextNode = null;
		if(nexti < arr.length && !currPath.contains(nexti + "," + root.j) && word.charAt(index) == arr[nexti][root.j]) {
			root.child.add(new Node(nexti,root.j,arr[nexti][root.j], root));
			currPath.add(nexti + "," + root.j);
			hasAdd = true;
			nextNode = root.child.get(root.child.size() - 1);
		}
		if(nexti2 >= 0 && !currPath.contains(nexti2 + "," + root.j) && word.charAt(index) == arr[nexti2][root.j]) {
			root.child.add(new Node(nexti2,root.j,arr[nexti2][root.j], root));
			if(!hasAdd) {
				currPath.add(nexti2 + "," + root.j);
				hasAdd = true;
				nextNode = root.child.get(root.child.size() - 1);
			}
		}
		if(nextj < arr[0].length && !currPath.contains(root.i + "," + nextj) && word.charAt(index) == arr[root.i][nextj]) {
			root.child.add(new Node(root.i,nextj,arr[root.i][nextj], root));
			if(!hasAdd) {
				currPath.add(root.i + "," + nextj);
				hasAdd = true;
				nextNode = root.child.get(root.child.size() - 1);
			}
		}
		if(nextj2 >= 0 && !currPath.contains(root.i + "," + nextj2) && word.charAt(index) == arr[root.i][nextj2]) {
			root.child.add(new Node(root.i,nextj2,arr[root.i][nextj2], root));
			if(!hasAdd) {
				currPath.add(root.i + "," + nextj2);
				hasAdd = true;
				nextNode = root.child.get(root.child.size() - 1);
			}
		}
		//封装为Node
		//递归第一个
		if(nextNode == null) {
			//无可达节点
			while(root.parent != null) {
				currPath.remove(currPath.size() - 1);
				int pos = root.parent.child.indexOf(root);
				if(pos == root.parent.child.size() - 1) {
					root = root.parent;
					//路径回退
					index = index - 1;
				}else {
					Node brother = root.parent.child.get(pos + 1);
					currPath.add(brother.i + "," + brother.j);
					findNextNode(brother, currPath, arr, word, index);
				}
			}
		}else {
			findNextNode(nextNode, currPath, arr, word, index + 1);
			//
			if(currPath.size() == word.length()) {
				System.out.println("success:" + currPath);
			}
		}
		
		//如果没有可达的节点，则返回到父节点，--寻找兄弟节点
		
	}

	private static List<Node> findFisrt(char[][] arr, char c) {
		List<Node> rs = new ArrayList<>();
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				if(arr[i][j] == c) {
					rs.add(new Node(i, j, c, null));
				}
			}
		}
		return rs;
	}
}
