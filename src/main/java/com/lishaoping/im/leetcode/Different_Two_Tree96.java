package com.lishaoping.im.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Different_Two_Tree96 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		int fangan;//��������ܷ�����
		int restnum;//ʣ����
		int fen;//�������
		int use;//����������
		public Node(int fangan, int restnum, int fen, int use) {
			super();
			this.fangan = fangan;
			this.restnum = restnum;
			this.fen = fen;
			this.use = use;
		}
		
	}
	
	
	private static void test() {
		//
		int n = 12;
		Stack<Node> st = new Stack<>();
		Node root = new Node(1, n-1, 0, 1);
		Node parent = root;
		int total = 0;
		while(parent != null) {
			//ȡ����һ������з�����
			//ÿ�ַ���,�ҳ����е����ӷ���
			int now_fen = parent.fen == 0 ? 1 : parent.fen * 2;
			int restnum = parent.restnum;
			for(int use = 1; use <= now_fen*2 && use <= restnum && use <= parent.use * 2; use++) {
				for(int fact_fen = use / 2 + use % 2; fact_fen <= use && fact_fen <= now_fen; fact_fen++) {
					if(restnum - use < 0) {
						break;
					}
					//ʹ�ø��������� ������ �ܷ�����
					int fangan = compute(use, fact_fen);
					if(restnum - use == 0) {
						total += fangan * parent.fangan;
						continue;
					}
					Node next = new Node(fangan * parent.fangan, restnum - use, now_fen, use);
					st.push(next);
				}
			}
			if(st.isEmpty()) {
				break;
			}
			parent = st.pop();
		}
		total = total == 0 ? 1 : total;
		System.out.println("total:" + total);
	}


	private static int compute(int use, int now_fen) {
		//
		int k = now_fen;
		int h = use;
		int up = 1;
		for(int i = k; i >= h-k+1; i--) {
			up *= i;
		}
		int down = 1;
		for(int j = 2*k-h; j >=1;j--) {
			down *= j;
		}
//		System.out.println(((double)(up)/down));
		int c = up / down;
		int you = 1;
		for(int i = 1;i <= 2*k-h;i++ ) {
			you *= 2;
		}
		return c * you;
	}
}
