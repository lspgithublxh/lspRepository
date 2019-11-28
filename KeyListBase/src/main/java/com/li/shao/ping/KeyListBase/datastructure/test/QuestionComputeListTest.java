package com.li.shao.ping.KeyListBase.datastructure.test;

import lombok.Data;
import lombok.experimental.Accessors;

public class QuestionComputeListTest {

	@Data
	@Accessors(chain = true)
	static class Node{
		Node next;
		int val;
	}
	
	public static void main(String[] args) {
		Node n1 = new Node().setVal(1).setNext(new Node().setVal(2).setNext(new Node().setVal(3).setNext(new Node().setVal(4))));
		Node n2 = new Node().setVal(1).setNext(new Node().setVal(2).setNext(new Node().setVal(3).setNext(new Node().setVal(4).setNext(new Node().setVal(5).setNext(new Node().setVal(6).setNext(new Node().setVal(7).setNext(new Node().setVal(8))))))));
		Node rs = new Node();
		int len1 = 0;
		int len2 = 0;
		Node cur1 = n1;
		Node cur2 = n2;
		while(cur1 != null) {
			len1++;
			cur1 = cur1.next;
		}
		while(cur2 != null) {
			len2++;
			cur2 = cur2.next;
		}
		cur1 = n1;
		cur2 = n2;
		if(len1 > len2) {
//			int v = compute(n1, n2, rs, len1 - len2);
			int v = compute2(cur1, cur2,len1 - len2);
		}else {
//			int v = compute(n2, n1, rs, len2 - len1);
			int v = compute2(cur2, cur1, len2 - len1);
		}
//		Node cur3 = rs;
		Node cur3 = len1 > len2 ? n1 : n2;
		while(cur3 != null) {
			System.out.print(cur3.val + ",");
			cur3 = cur3.next;
		}
	}

	private static int compute(Node n1, Node n2, Node rs, int more) {
		rs.next = new Node();
		int v = 0;
		int v2 = 0;
		if(more > 0) {
			v = compute(n1.next, n2, rs.next, more - 1);
			v2 = n1.val + v;//0
		}else {
			if(n1.next != null && n2.next != null) {
				v = compute(n1.next, n2.next, rs.next, 0);
			}else {
				rs.next = null;
			}
			v2 = n1.val + n2.val + v;//0
		}
		rs.val = v2 >= 10 ? v2 - 10 : v2;
		return v2 >= 10 ? 1:0;
	}
	
	private static int compute2(Node n1, Node n2, int more) {
		int v = 0;
		int v2 = 0;
		if(more > 0) {
			v = compute2(n1.next, n2, more - 1);
			v2 = n1.val + v;//0
		}else {
			if(n1.next != null && n2.next != null) {
				v = compute2(n1.next, n2.next, 0);
			}
			v2 = n1.val + n2.val + v;//0
		}
		n1.val = v2 >= 10 ? v2 - 10 : v2;
		return v2 >= 10 ? 1:0;
	}
}
