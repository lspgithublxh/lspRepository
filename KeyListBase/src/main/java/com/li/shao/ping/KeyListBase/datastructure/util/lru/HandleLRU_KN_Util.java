package com.li.shao.ping.KeyListBase.datastructure.util.lru;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;
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
 *
 *---LRU-k可以避免一批这样的数据：出现一次而且量大，来把之前的访问次数多的那些键挤出去了。
 *
 *-- 之前我写的方法：单位时间内的访问次数，排序：将最小的那些移除。容易被一堆数据污染----。
 *
 *：：检验标准：怎样规律的数据流中哪些数据会被保留？哪些数据会被丢弃？
 *   规律1：很长的完全不重复的数据
 *规律2： 某几个数据在一组不重复的数据中间隔出现；
 *规律3：
 *
 *keyNodeMap与链表的数量上的不一致
 * @author lishaoping
 * @date 2019年12月20日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
@Slf4j
public class HandleLRU_KN_Util<T,V> {

	Node leftLimit = new Node(null,null, 0 ,0);
	Node rightLimit = new Node(null,null, 0 ,0);
	Map<T, Node> keyNodeMap = Maps.newConcurrentMap();
	private AtomicInteger size = new AtomicInteger(0);
	private int maxSize;
	private int minVisitCount;
	private int deep;//代表有几层历史
	
	HandleLRU_KN_Util<T, V> history;
	{
		leftLimit.right = rightLimit;
		rightLimit.left = leftLimit;
	}
	
	public HandleLRU_KN_Util(int maxSize, int minVisitCount, int deep) {
		this.maxSize = maxSize;
		this.minVisitCount = minVisitCount;
		this.deep = deep;
		if(deep > 0) {
			history = new HandleLRU_KN_Util<T, V>(maxSize, minVisitCount, deep - 1);
		}
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
	
	public synchronized Node put(T key, V val) {
		Node node = keyNodeMap.get(key);
		if(node == null) {//主程序不包含
			if(history != null) {//主程序
				Node hnode = history.put(key, val);
				if(hnode == null || hnode.times < minVisitCount) {
					return null;//不加入主程序
				}else {//移除掉,再加入主程序
					history.delete(key);
					
					if(size.get() >= maxSize) {
						//移除右边
						removeRight();
						insertLeft(hnode);
						//
					}else {
						insertLeft(hnode);
						size.incrementAndGet();
					}
					
					keyNodeMap.put(key, hnode);
					return null;//不再往上加
				}
			}
			Node n = new Node(key, val, System.nanoTime(), 1);
			if(size.get() >= maxSize) {
				//移除右边
//				pringCur();
				removeRight();
//				pringCur();
				insertLeft(n);
				//
			}else {
				insertLeft(n);
				size.incrementAndGet();
			}
			keyNodeMap.put(key, n);
			return n;
		}else {//包含
//			log.info("contain before");
//			pringCur();
//			log.info(keyNodeMap.keySet().toString());//会造成死锁
			node.val = val;
			node.updatetime = System.nanoTime();
			node.times++;
//			//移动条件还要满足才行：
//			if(node.times < minVisitCount) {
//				return node;
//			}
			Node nLef = node.left;
			Node nRig = node.right;
			
			nLef.right = nRig;
			nRig.left = nLef;
			
			
			insertLeft(node);
//			pringCur();
//			log.info("contain after");
		}
		return node;
	}

	private void removeRight() {
		Node rightTwo = rightLimit.left;
		Node rightThree = rightTwo.left;
		
		rightThree.right = rightLimit;
		rightLimit.left = rightThree;
		if(rightTwo.key != null) {
			keyNodeMap.remove(rightTwo.key);
		}
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
		if(node == null) {
			if(history != null) {
				V v = history.get(key);
				return v;
			}else {
				return null;
			}
		}
		return node.val;
	}
	
	public synchronized void delete(T key) {
		if(history != null) {
			history.delete(key);
		}
		Node node = keyNodeMap.get(key);
		if(node != null) {
			keyNodeMap.remove(key);
			Node nlef = node.left;
			Node nrig = node.right;
			nlef.right = nrig;
			nrig.left = nlef;
			size.decrementAndGet();
		}
	}
	
	public synchronized void printList() {
		if(history != null) {
			history.printList();
		}
		Node cur = leftLimit;
		while(cur != null) {
			log.info(cur.val + "," + cur.updatetime + "," + cur.times);
			cur = cur.right;
		}
	}
	
	public synchronized void pringCur() {
		Node cur = leftLimit;
		System.out.println("deep:" + deep);
		while(cur != null) {
			System.out.print(cur.val + ",");
			cur = cur.right;
		}
		System.out.println();
	}
	
	public synchronized void deepSize() {
		HandleLRU_KN_Util<T,V> cur = this;
		while(cur != null) {
			log.info(cur.deep + "-deep-" + cur.size.get());
			cur = cur.history;
		}
	}
	
	public static void main(String[] args) {
		test1();
//		test2();
	}

	private static void test2() {
		HandleLRU_KN_Util<String, Integer> util = new HandleLRU_KN_Util<String, Integer>(3, 2, 4);
//		int[] data = new int[] {1,2,3,1,2,3,1,2,3,1,2,3};
		int[] data = new int[] {1,2,3,1,2,3,1,2,3,1,2,3,5,6,7,5,6,7,5,6,7,7,8,9,7,8,9};
		for(int d : data) {
			util.put("key" + d, d);
		}
		util.deepSize();
	}

	private static void test1() {
		try {
			//有几层历史
			List<String> keyList = Lists.newCopyOnWriteArrayList();//reentrylock
//			HandleLRU_KN_Util<String, Integer> util = new HandleLRU_KN_Util<String, Integer>(3, 2, 4);
			HandleLRU_KN_Util<String, Integer> util = new HandleLRU_KN_Util<String, Integer>(300, 3, 4);

			SimpleThreadPoolUtil.pool.addTask(() -> {
				final String name = "thread1";
				for (int i = 0; i < 1000; i++) {
					SimpleThreadPoolUtil.pool.addTask(() -> {
						int v = (int) (Math.random() * 1000);
						util.put(name + "key" + v, v);
//						keyList.add(name + "key" + v);
					});
				}
			});
//			SimpleThreadPoolUtil.pool.addTask(() -> {
//				final String name = "thread2";
//				for (int i = 0; i < 100; i++) {
//					SimpleThreadPoolUtil.pool.addTask(() -> {
//						int v = (int) (Math.random() * 1000);
//						util.put(name + "key" + v, v);
//						keyList.add(name + "key" + v);
//					});
//				}
//			});
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			util.printList();
			System.out.println(util.size.get());
			System.out.println("---------------");
			util.deepSize();
//			System.out.println(keyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
