package com.lishaoping.im.leetcode100;

/**
 * Ī��˹����
 *
 *@author lishaoping
 *Client
 *2020��7��11��
 */
public class Qianxu_bianli_tree144 {

	public static void main(String[] args) {
		modris();
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

	private static void modris() {
		int v = 0;
		Node root = new Node(v++);
		Node left = new Node(v++);
		Node right = new Node(v++);
		root.left = left;
		root.right = right;
		Node ll = new Node(v++);
		left.left = ll;
		Node lr = new Node(v++);
		left.right = lr;
		Node lll = new Node(v++);
		Node llr = new Node(v++);
		ll.left = lll;
		ll.right = llr;
		
		Node preProcessor = null;
		Node cur = root;
		while(cur != null) {
			
			//�ҵ������ǰ���� ǰ���ǰ������߸����Ԫ��---������ֻ��ҪĩβԪ��ָ��Ϳ��� 
			if(cur.left != null) {
				preProcessor = cur.left;
				while(preProcessor.right != null && preProcessor.right != cur) {
					preProcessor = preProcessor.right;
				}
				if(preProcessor.right == cur) {
					preProcessor.right = null;
					cur = cur.right;
				}else {
					System.out.println(cur.v);
					preProcessor.right = cur;
					cur = cur.left;
				}
				
			}else {
				//û��ǰ��
				//ֱ����һ���ڵ�
				System.out.println(cur.v);
				cur = cur.right;
				
			}
			
		}
	}
}
