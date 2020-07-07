package com.lishaoping.im;

public class D {

	class Node {
		Node next;
		char val;
		
		public Node() {}
		public Node(char val) {
			this.val = val;
		}
	}
	
	public static void main(String[] args) {
		D d = new D();
		Node n1 = d.new Node('a');
		Node n2 = d.new Node('a');
		Node n3 = d.new Node('a');
		Node n4 = d.new Node('c');
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		Node p = d.test(n1);
		Node n = p;
		while(n != null) {
			System.out.print(n.val + ",");
			n = n.next;
		}
	}

	private  Node test(Node node) {
		if(node == null) {
			return null;
		}
		if(node.next == null) {
			return node;
		}
		Node start = node;
		Node end = node;
		Node pre_start = node;
		while(end != null) {
			if(end.next != null) {
				if(end.next.val == end.val) {
					//end ÒÆ¶¯¹ýÈ¥
					end = end.next;
				}else {
					if(start == end) {
						pre_start = start;
						end = end.next;
						start = end;
						
					}else {
						pre_start.next = end.next;
						//
						end.next = null;
						end = end.next;
						start = end;
					}
				}
			}else {
				if(start == end) {
					
				}else {
					pre_start.next = null;
					
				}
				end = end.next;
			}
		}
		if(pre_start.next == null) {
			return null;
		}
		if(pre_start == node) {
			pre_start = pre_start.next;
		}
		return pre_start;
	}
}

