package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word_goon127 {

	public static void main(String[] args) {
		test();
	}

	static class Node{
		String v;
		int color = -1;//-1白0灰1黑
		int d = 0;
//		List<Node> child = new ArrayList<Word_goon127.Node>();
		public Node(String v) {
			super();
			this.v = v;
		}
		@Override
		public String toString() {
			return "Node [v=" + v + ", color=" + color + ", d=" + d + "]";
		}
	}
	
	private static void test() {
		//
		String beg = "hit";
		String end = "cog";
		String[] data = {"hot","dot","dog","lot","log","cog"};
		Map<String, Node> map = new HashMap<>();
		Map<String, List<Node>> linjinMap = new HashMap<>();
		Node root = null;
		for(int i = 0; i < data.length; i++) {
			String begin = data[i];
			Node has = map.get(begin);
			Node p =  has != null ? has : new Node(begin);
			map.put(begin, p);
			List<Node> kedaList = new ArrayList<>();
			linjinMap.put(begin, kedaList);
			for(int j = i+1; j < data.length; j++) {
				if(xianglin(p.v, data[j])) {
					kedaList.add(new Node(data[j]));
					List<Node> list = linjinMap.get(data[j]);
					if(list == null) {
						list = new ArrayList<>();
						linjinMap.put(data[j], list);
					}
					list.add(p);
				}
			}
			if(i == 0) {
				root = p;
			}
		}
		//形成了，开始DFS广度优先遍历
		//s->v的d值；
		int vv = Integer.MAX_VALUE;
		for(String d : data) {
			clear(map);
			if(xianglin(d, beg)) {
				int dx = sToV(d, end, linjinMap, map);
				dx += 1;//本身
				System.out.println(dx + 1);
				vv = dx + 1 < vv ? dx + 1 : vv;
			}
		}
		System.out.println("over:" + vv);
	}

	private static void clear(Map<String, Node> map) {
		for(Node e : map.values()) {
			e.color = -1;
			e.d = 0;
		}
	}

	private static int sToV(String beg, String end, Map<String, List<Node>> linjinMap, Map<String, Node> map) {
		List<Node> que = new ArrayList<>();
		que.add(map.get(beg));
		map.get(beg).color = 0;
		while(!que.isEmpty()) {
			Node cur = que.remove(0);
			if(cur.color == 0) {
				List<Node> list = linjinMap.get(cur.v);
				for(Node n : list) {
					if(n.color == -1) {
						n.color = 0;
						n.d = cur.d + 1;
						if(n.v.equals(end)) {
							return n.d;
						}
						que.add(n);
					}
				}
				cur.color = 1;
			}
		}
		
		
		return -1;
	}

	private static boolean xianglin(String v1, String v) {
		int difCount = 0;
		for(int i = 0; i < v1.length(); i++) {
			if(v1.charAt(i) != v.charAt(i)) {
				difCount += 1;
				if(difCount > 1) {
					return false;
				}
			}
		}
		return difCount == 1;
	}
}
