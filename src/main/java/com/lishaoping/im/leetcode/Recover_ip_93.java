package com.lishaoping.im.leetcode;

import java.util.Stack;

public class Recover_ip_93 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		String ip;
		int start;
		public Node(String ip, int start) {
			super();
			this.ip = ip;
			this.start = start;
		}
		
	}
	
	private static void test() {
		String ip = "25525511135";
		Node cur = new Node("", 0);
		Stack<Node> st = new Stack<>();
		while(cur != null) {
			//截取1,2,3份
			if(cur.start == ip.length()) {
				cur = st.pop();
				continue;
			}
			int duan_len = cur.ip.split("\\.").length;
			if(duan_len == 3) {//必须全部取
				int v = Integer.valueOf(ip.substring(cur.start));
				if(v > 255) {
					cur = st.pop();
					continue;
				}
				System.out.println(cur.ip + "." + v);
				cur = st.pop();
			}else {
				String pre = cur.ip.equals("") ? cur.ip : cur.ip +".";
				Node n1 = new Node(pre + ip.substring(cur.start, cur.start + 1), cur.start + 1);
				if(cur.start < ip.length() - 2) {
					int v = Integer.valueOf( ip.substring(cur.start, cur.start + 3));
					if(v <= 255) {
						Node n3 = new Node(pre + v, cur.start + 3);
						st.push(n3);
					}
				}
				
				if(cur.start < ip.length() - 1) {
					Node n2 = new Node(pre + ip.substring(cur.start, cur.start + 2), cur.start + 2);
					st.push(n2);
				}
				
				
				cur = n1;
			}
			
			
		}
	}
}
