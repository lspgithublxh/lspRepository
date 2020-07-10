package com.lishaoping.im.leetcode100;

public class Floyd_rock_circle_142 {

	public static void main(String[] args) {
		ring();
	}
	
	static class Node{
		int v;
		Node next;
		public Node(int v) {
			super();
			this.v = v;
		}
		
		
	}

	private static void ring() {
		int i = 0;
		Node root = new Node(i++);
		Node n1 = new Node(i++);
		Node n2 = new Node(i++);
		Node n3 = new Node(i++);
		Node n4 = new Node(i++);
		Node n5 = new Node(i++);
		Node n6 = new Node(i++);
		Node n7 = new Node(i++);
		Node n8 = new Node(i++);
		Node n9 = new Node(i++);
		root.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n2;
		Node p1 = root;
		Node p2 = root;
		Node xy1 = null;
		Node xy2 = null;
		while(xy2 == null) {
			p1 = p1.next;
			if(p2.next == null) {
				System.out.println("no circle");
				break;
			}
			if(xy1 == null) {
				p2 = p2.next.next;
			}else {
				p2 = p2.next;
			}
			if(p2 == p1) {
				if(xy1 == null) {
					xy1 = p2;
					p1 = root;
				}else {
					xy2 = p2;
				}
			}
		}
		System.out.println("rukou:" + xy2 == null ? null : xy2.v);
		int index = 0;
		Node cur = root;
		if(xy2 != null) {
			while(cur != xy2) {
				index++;
				cur = cur.next;
			}
			System.out.println("index:" + index);
		}
		
	}
}
