package com.li.shao.ping.KeyListBase.datastructure;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.entity.LinkedNode;

import lombok.extern.slf4j.Slf4j;
/**
 * 性能比较好
 * TODO 
 * @author lishaoping
 * @date 2019年10月17日
 * @file ListDataService
 */
@Slf4j
public class ListDataService implements DataService{

	private Map<Long, LinkedNode> dataMap = Maps.newConcurrentMap();
	
	@Override
	public String queryTopNByKey(Long key, int n) {
		LinkedNode root = dataMap.get(key);
		LinkedNode cur = root;
		StringBuilder builder = new StringBuilder();//最简单最规范
		for(int i = 0; i < n && cur != null; i++) {
			builder.append(cur.getId()).append("`")
			.append(cur.getScore()).append("`")
			.append(cur.getValue()).append("`");
			cur = cur.getNext();
		}
		return builder.substring(0, builder.length() - 1).toString();
	}

	@Override
	public void updateNodesByKey(String nodes, Long key) {
		List<LinkedNode> nodeList = transToNodeList(nodes);
		//
		LinkedNode root = dataMap.get(key);
		if(root == null) {
			return;
		}
		for(LinkedNode entity : nodeList) {
			LinkedNode scorePos = null;
			LinkedNode idPos = null;
			LinkedNode cur = root;
			boolean spOk = false;
			boolean idOk = false;
			LinkedNode idParent = null;
			LinkedNode scoreParent = null;
			LinkedNode pass = null;
			while(cur != null) {
				if(!spOk && cur.getScore() <= entity.getScore()) {
					scorePos = cur;
					scoreParent = pass;
					spOk = true;
				}
				if(!idOk && cur.getId() == entity.getId()) {
					idPos = cur;
					idParent = pass;
					idOk = true;
				}
				pass = cur;
				cur = cur.getNext();
				
				if(spOk && idOk) {
					break;
				}
				
			}
			if(idPos == scorePos || idPos.getNext() == scorePos) {//id不必移除，直接更新score
				idPos.setScore(entity.getScore());
			}else {//先删除，后增加
				idParent.setNext(idPos.getNext());
				if(scoreParent == null) {
					dataMap.put(key, entity);
				}else {
					scoreParent.setNext(entity);
				}
				entity.setNext(scorePos);
			}
		}
	}

	private List<LinkedNode> transToNodeList(String nodes) {
		int index = 0;
		int count = 0;
		StringBuilder builder = new StringBuilder();
		List<LinkedNode> nodeList = Lists.newArrayList();
		char[] chrs = nodes.toCharArray();
		while(index < chrs.length) {
			int i = index;
			for(;i < chrs.length && chrs[i] != '`'; i++) {
				builder.append(chrs[i]);
			}
			switch (count) {
			case 0:
				LinkedNode node = new LinkedNode();
				node.setId(Long.valueOf(builder.toString()));
				nodeList.add(node);
				break;
			case 1:
				nodeList.get(nodeList.size() - 1).setScore(Double.valueOf(builder.toString()));
				break;
			case 2:
				nodeList.get(nodeList.size() - 1).setValue(builder.toString());//序列化方式
				break;
			default:
				break;
			}
			builder.setLength(0);
			count = ++count % 3;
			index = i + 1;
		}
		return nodeList;
	}

	@Override
	public void initUserNodesByKeyAndData(String nodes, Long key) {
		List<LinkedNode> list = transToNodeList(nodes);
		//
		LinkedNode root = list.get(0);
		for(int i = 1; i < list.size(); i++) {
			LinkedNode parent = null;
			LinkedNode cur = root;
			while(cur != null) {
				if(cur.getScore() < list.get(i).getScore()) {
					if(parent == null) {
						root = list.get(i);
						root.setNext(cur);
					}else {
						parent.setNext(list.get(i));
						list.get(i).setNext(cur);
					}
					break;
				}
				parent = cur;
				cur = cur.getNext();
			}
			if(cur == null) {
				parent.setNext(list.get(i));
			}
			
		}
		dataMap.put(key, root);
	}

	@Override
	public void deleteByKey(Long key) {
		dataMap.remove(key);
	}

	public static void main(String[] args) {
		ListDataService serv = new ListDataService();
		
		serv.initUserNodesByKeyAndData("1`2.1`data`12`3.3`secend`11`2.2`data2", 1L);
		//
		for(int j = 1; j < 1000; j++) {
			StringBuilder builder = new  StringBuilder();
			for(int i = 0; i < 3000; i++) {
				builder.append(i).append("`").append(Math.random() * 100).append("`").append("this_data").append("`");
			}
			String d = builder.substring(0, builder.length() - 1).toString();
			serv.initUserNodesByKeyAndData(d, (long)j);
			System.out.println("" + j);
		}

		for(int j = 0; j < 1000; j++) {
			long t1 = System.nanoTime();
			long t11 = System.currentTimeMillis();
			String data = serv.queryTopNByKey(1L, 5);
			log.info(data);
			serv.updateNodesByKey(((int)Math.random() * 100)+ "`" + (Math.random() + 10) +  "`data", 1L);
			String data2 = serv.queryTopNByKey(1L, 5);
			log.info(data2);
			serv.updateNodesByKey(((int)Math.random() * 100) + "`" + (Math.random() + 10)  + "`data2", 1L);
			String data3 = serv.queryTopNByKey(1L, 5);
			log.info(data3);
			long t2 = System.nanoTime();
			long t22 = System.currentTimeMillis();
			log.info((t2 - t1) / 1000 / 1000 + "ms");
			log.info((t22 - t11) + "ms");
		}
		//强化
	}
}
