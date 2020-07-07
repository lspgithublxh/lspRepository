package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * dp��ʽѰ��ȷ��
 *
 *@author lishaoping
 *Client
 *2020��7��6��
 */
public class Jiema_91 {

	public static void main(String[] args) {
		try {
			test();
		} catch (Exception e) {
		}
		test2();
	}

	private static void test2() {
		String s = "226";
		int[] f = new int[s.length()];
		f[0]=1;
		for(int i = 1; i < f.length; i++) {
			char ic = s.charAt(i);
			char i_1c = s.charAt(i - 1);
			int v = Integer.valueOf(i_1c+""+ic);
			if(v <= 26) {
				f[i] = f[i-1] + (i <= 1 ? 1 : f[i-2]);
			}else {
				f[i] = f[i-1];
			}
		}
		System.out.println(f[f.length - 1]);
	}

	static class Node{
		public Node(int i, int j) {
			this.start = i;
			this.end = j;
		}
		List<String> comp = new ArrayList<String>();
		int start;
		int end;
		
	}
	
	private static void test() {
		String s = "226";
		Stack<Node> stack = new Stack<>();
		int i = 0;
		Node cur = new Node(0,1);
		while(cur != null) {
			if(cur.end - cur.start > 2) {
				cur = stack.pop();
				continue;
			}
			if(cur.start >= s.length() || cur.end > s.length()) {
				//������ˣ�
				if(cur.start == s.length()) {
					System.out.println(cur.comp);
				}
				//���µ�����һ���ڵ�
				cur = stack.pop();
				continue;
			}
			if(cur.end - cur.start == 2) {
				int v = Integer.valueOf(s.substring(cur.start, cur.end));
				if(v > 26) {
					cur = stack.pop();
					continue;
				}
			}
			//����ȡ
			Node dui = new Node(cur.start, cur.end + 1);
			dui.comp.addAll(cur.comp);
			stack.push(dui);
			//��ȡ
			Node jie = new Node(cur.end, cur.end + 1);
			String v = s.substring(cur.start, cur.end);
			jie.comp.addAll(cur.comp);
			jie.comp.add(((char)(Integer.valueOf(v) + 'A' - 1)) + "");//ӳ��Ϊ�ַ�
			cur = jie;
		}
		
	}
}
