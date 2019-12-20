package com.li.shao.ping.KeyListBase.datastructure.util.lru;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *最近最少使用算法:latest recently used
 *
 *算法1：链表+map方式： 存在则取出放到队尾，没有则直接放到队尾，看链表长度>设置，则移除队首元素
 * @author lishaoping
 * @date 2019年12月20日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
@Slf4j
public class HandleLRU_KUtil<T,V> {

	Node leftLimit = new Node(null,null, 0 ,0);
	Node rightLimit = new Node(null,null, 0 ,0);
	Map<T, Node> keyNodeMap = Maps.newConcurrentMap();
	private AtomicInteger size = new AtomicInteger(0);
	private int maxSize;
	private int minVisitCount;
	
	{
		leftLimit.right = rightLimit;
		rightLimit.left = leftLimit;
	}
	
	public HandleLRU_KUtil(int maxSize, int minVisitCount) {
		this.maxSize = maxSize;
		this.minVisitCount = minVisitCount;
	}
	
	@Data
	@Accessors(chain = true)
	class Node{
		T key;
		V val;
		int times;
		Node left;
		Node right;
		long creattime;
		long updatetime;
		
		public Node(T d, V v, long t, int times) {
			key = d;
			val = v;
			creattime = t;
			updatetime = t;
			this.times = times;
		}
	}
	
	public synchronized boolean put(T key, V val) {
		Node node = keyNodeMap.get(key);
		if(node == null) {//不包含
			Node n = new Node(key, val, System.nanoTime(), 0);
			if(size.get() >= maxSize) {
				//移除右边
				removeRight();
				insertLeft(n);
				//
			}else {
				insertLeft(n);
				size.incrementAndGet();
			}
			keyNodeMap.put(key, n);
		}else {//包含
			node.val = val;
			node.updatetime = System.nanoTime();
			node.times++;
			//移动条件还要满足才行：
			if(node.times < minVisitCount) {
				return true;
			}
			Node nLef = node.left;
			Node nRig = node.right;
			
			nLef.right = nRig;
			nRig.left = nLef;
			
			
			insertLeft(node);
		}
		return true;
	}

	private void removeRight() {
		Node rightTwo = rightLimit.left;
		Node rightThree = rightTwo.left;
		
		rightThree.right = rightLimit;
		rightLimit.left = rightThree;
	}

	private void insertLeft(Node n) {
		Node leftTwo = leftLimit.right;
		n.right = leftTwo;
		leftTwo.left = n;
		
		leftLimit.right = n;
		n.left = leftLimit;
	}
	
	private void insertLeft(T key, V val) {
		Node n = new Node(key, val, System.nanoTime(), 0);
		Node leftTwo = leftLimit.right;
		n.right = leftTwo;
		leftTwo.left = n;
		
		leftLimit.right = n;
		n.left = leftLimit;
	}
	
	public V get(T key) {
		Node node = keyNodeMap.get(key);
		return node.val;
	}
	
	public synchronized void delete(T key) {
		Node node = keyNodeMap.get(key);
		if(node != null) {
			keyNodeMap.remove(key);
			Node nlef = node.left;
			Node nrig = node.right;
			nlef.right = nrig;
			nrig.left = nlef;
		}
	}
	
	public synchronized void printList() {
		Node cur = leftLimit;
		while(cur != null) {
			log.info(cur.val + "," + cur.updatetime);
			cur = cur.right;
		}
	}
	
	public static void main(String[] args) {
		try {
			HandleLRU_KUtil<String, Integer> util = new HandleLRU_KUtil<String, Integer>(100, 10);
			SimpleThreadPoolUtil.pool.addTask(() -> {
				final String name = "thread1";
				for (int i = 0; i < 10000; i++) {
					SimpleThreadPoolUtil.pool.addTask(() -> {
						int v = (int) (Math.random() * 1000);
						util.put(name + "key" + v, v);
					});
				}
			});
			SimpleThreadPoolUtil.pool.addTask(() -> {
				final String name = "thread2";
				for (int i = 0; i < 10000; i++) {
					SimpleThreadPoolUtil.pool.addTask(() -> {
						int v = (int) (Math.random() * 1000);
						util.put(name + "key" + v, v);
					});
				}
			});
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			util.printList();
			System.out.println(util.size.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
