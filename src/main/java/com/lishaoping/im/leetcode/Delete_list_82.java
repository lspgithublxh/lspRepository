package com.lishaoping.im.leetcode;

public class Delete_list_82 {

	public static void main(String[] args) {
		tdest();
	}

	static class Node{
		int v ;
		Node next;
		public Node(int v) {
			this.v = v;
		}
	}
	
	private static void tdest() {
//		Node r = new Node(Integer.MAX_VALUE);
		Node pre = null;
		Node r = null;
//		int[] arr = {1,1,1,2,3,3,4,5,6,6,6,7,8};
		int[] arr = {1,2,3,4,4,3,5};
		for(int i = 0; i < arr.length; i++) {
			Node n = new Node(arr[i]);
			if(pre != null) {
				pre.next = n;
			}else {
				r = n;
			}
			pre = n;
		}
		//删除
		Node head = r;
		Node left = null;
		Node right = head;
		int v = Integer.MAX_VALUE;
		int count = 1;
		int dftimes = 1;
		while(right.next != null) {
			if(right.next.v == right.v) {
				v = right.v;
				count++;
			}else {
				if(count >= 2) {
					//需要执行删除
					if(left != null) {
						left.next = right.next;
						//删除之后，可能需要从头开始
						if(left.v == right.next.v) {
							left = null;
							right = head;
							count = 1;
							v = Integer.MAX_VALUE;
							dftimes = 1;
							continue;
						}
						dftimes = 0;
					}else {
						if(head.v != right.v) {
							left = head;
							left.next = right.next;
							if(left.v == right.next.v) {
								left = null;
								right = head;
								count = 1;
								v = Integer.MAX_VALUE;
								dftimes = 0;
								continue;
							}
							dftimes = 0;
						}else {
							head = right.next;
							dftimes = 0;
						}
						
					}
					
				}
				v = right.next.v;
				count = 1;
				if(++dftimes >= 2) {
					if(left != null) {
						left = left.next;
					}else {
						left = head;
					}
				}
			}
			right = right.next;
		}
		//
		if(count > 1) {//需要删除到结尾
			left.next = null;
		}
		//打印
		Node p = head;
		System.out.println("print:");
		while(p != null) {
			System.out.print(p.v + "->");
			p = p.next;
		}
	}
}
