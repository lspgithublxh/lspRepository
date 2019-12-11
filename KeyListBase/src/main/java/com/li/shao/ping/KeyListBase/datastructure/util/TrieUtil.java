package com.li.shao.ping.KeyListBase.datastructure.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 压缩存储字符串 FST
 * @author lishaoping
 * @date 2019年12月11日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
public class TrieUtil {

	private Node root;
	
	@Data
	@Accessors(chain = true)
	static class Node{
		private Character val;
		private Map<Character, Node> map;
		private boolean asEnd;
	}
	
	public void saveString(String str) {
		if(str == null) {
			return;
		}
		if(root == null) {
			root = new Node();
		}
		Node cur = root;
		//开始比较
		int len = str.length();
		for(int i = 0; i < len; i++) {
			char c = str.charAt(i);
			Map<Character, Node> map = Optional.ofNullable(cur.map).orElseGet(Maps::newHashMap);
			if(cur.map == null) {
				cur.map = map;
			}
			Node exis = Optional.ofNullable(map.get(c)).orElseGet(() ->{
				Node node = new Node().setVal(c);
				map.put(c, node);
				return node;
			});
			if(i == len - 1) {
				exis.setAsEnd(true);
			}
			cur = exis;
		}
	}
	
	public boolean exists(String str) {
		if(str == null) {
			return false;
		}
		int len = str.length();
		Node cur = root;
		for(int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if(cur.map == null) {
				return false;
			}
			Node node = cur.map.get(c);
			if(node == null) {
				return false;
			}
			cur = node;
		}
		if(cur.asEnd) {
			return true;
		}
		return false;
	}
	
	public boolean delete(String str) {
		if(str == null) {
			return false;
		}
		int len = str.length();
		Node cur = root;
		List<Node> pList = Lists.newArrayList();
		for(int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if(cur.map == null) {
				return false;
			}
			Node node = cur.map.get(c);
			if(node == null) {
				return false;
			}
			pList.add(cur);
			cur = node;
		}
		if(cur.asEnd) {
			cur.setAsEnd(false);
			int x = pList.size() - 1;
			while((cur.map == null || cur.map.isEmpty()) && !cur.asEnd) {
				Node parent = pList.get(x--);
				if(parent != null) {
					parent.map.remove(cur.val);
				}
				cur = parent;
			}
			//往回删
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		TrieUtil util = new TrieUtil();
		String str = "yes,moni";
		util.saveString(str);
		String str2 = "yes,monis";
		util.saveString(str2);
		String str3 = "yes,oni";
		util.saveString(str3);
		System.out.println(new Gson().toJson(util.root));
		System.out.println(util.exists(str));
		System.out.println(util.exists("y"));
		//存储100个
		boolean de = util.delete(str3);
		System.out.println(de);
		System.out.println(new Gson().toJson(util.root));
	}
	
	
}
