package com.lishaoping.im.leetcode100;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUUtil {

	public static void main(String[] args) {
	}

	static class Node{
		Object v;
		Node pre;
		Node next;
		public Node(Object v) {
			super();
			this.v = v;
		}
		
	}
	
	static Map<String, Node> cacheMap = new ConcurrentHashMap<>();
	static int i = 0;
	static Node left = new Node(i++);
	static Node right = new Node(i++);
	
	public Object get(String key) {
		Node node = cacheMap.get(key);
		if(node != null) {
			tiaozheng(node);
		}
		return node;
	}

	private void tiaozheng(Node node) {
		Node pre = node.pre;
		Node next = node.next;
		pre.next = next;
		next.pre = pre;
		
		node.next = left.next;
		left.next.pre = node;
		left.next = node;
		node.pre = left;
	}
	
	public void put(String key, Object v) {
		Node node = cacheMap.get(key);
		if(node != null) {
			node.v = v;
			
			tiaozheng(node);
		}else {
			node = new Node(v);
			cacheMap.put(key, node);
			
			node.next = left.next;
			left.next.pre = node;
			left.next = node;
			node.pre = left;
		}
	}
}
