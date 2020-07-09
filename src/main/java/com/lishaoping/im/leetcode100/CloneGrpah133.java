package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CloneGrpah133 {

	public static void main(String[] args) {
		cl();
	}
	static class Node{
		int v;
		List<Node> neighbors = new ArrayList<CloneGrpah133.Node>();
		public Node(int v) {
			super();
			this.v = v;
		}
	}

	private static void cl() {
		int x = 0;
		Node start = new Node(x++);
		Node n1 = new Node(x++);
		Node n2 = new Node(x++);
		Node n3 = new Node(x++);
		start.neighbors.add(n1);
		n1.neighbors.add(n2);
		n2.neighbors.add(n3);
		n3.neighbors.add(start);
		//
		Map<Integer, Node> numberMap = new HashMap<>();
		Map<Node, Integer> nodeMap = new HashMap<>();
		List<Node> que = new ArrayList<>();
		Set<Node> s = new HashSet<>();
		que.add(start);
		//bfs
		Integer glob = 0;
		while(!que.isEmpty()) {
			Node e = que.remove(0);
			Integer number = nodeMap.get(e);
			Node cp = null;
			if(number == null) {
				cp = new Node(e.v);
				int v = glob++;
				numberMap.put(v, cp);
				nodeMap.put(e, v);
			}else {
				cp = numberMap.get(number);
			}
			for(Node nb : e.neighbors) {
				Integer c1 = nodeMap.get(nb);
				Node cp2 = null;
				if(c1 == null) {
					cp2 = new Node(nb.v);
					int v = glob++;
					numberMap.put(v, cp2);
					nodeMap.put(nb, v);
				}else {
					cp2 = numberMap.get(c1);
				}
				cp.neighbors.add(cp2);
				if(!s.contains(nb)) {
					que.add(nb);
				}
			}
			s.add(e);
		}
		printNode(numberMap.get(0));
	}

	private static void printNode(Node node) {
		Set<String> has = new HashSet<>();
		List<Node> no = new ArrayList<>();
		no.add(node);
		while(!no.isEmpty()) {
			Node e = no.remove(0);
			for(Node b : e.neighbors) {
				if(!has.contains(b.hashCode() + "-" + e.hashCode())) {
					has.add(b.hashCode() + "-" + e.hashCode());
					has.add(e.hashCode() + "-" + b.hashCode());
					System.out.println(b.v + "->" + e.v);
					no.add(b);
				}
			}
		}
	}
}
