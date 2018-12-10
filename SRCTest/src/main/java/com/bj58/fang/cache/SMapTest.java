package com.bj58.fang.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 
 * @ClassName:SMapTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月6日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class SMapTest {

	public static void main(String[] args) {
//		 test();
//		 test2();
		 new SMapTest().test3();
	}

	ConcurrentSkipListMap<CacheEntity2<String>, String> skipListMap = null;
	private void test3() {
		try {
			skipListMap = new ConcurrentSkipListMap<>(new Comparator<CacheEntity2<String>>() {
				@Override
				public int compare(CacheEntity2<String> o1, CacheEntity2<String> o2) {
					if (o1.getVisiCount() > o2.getVisiCount()) {
						return 1;
					} else if (o1.getVisiCount() < o2.getVisiCount()) {
						return -1;
					}
					return 1;
				}
			});
			int i = 0;
			while(true) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				CacheEntity2<String> en = new CacheEntity2<String>("", 1l, i);//次数相同就会被覆盖
				System.out.println("start put");
				skipListMap.put(en, i++ + "");//会卡死
				System.out.println("put end");
				System.out.println(skipListMap.size());
			}
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					int i = 0;
//					while(true) {
//						try {
//							Thread.sleep(20);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						CacheEntity2<String> en = new CacheEntity2<String>("", 1l, 1);
//						skipListMap.put(en, i++ + "");
//						System.out.println(skipListMap.size());
//					}
//				}
//			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test2() {
		Map<String, CacheEntity2<String>> cmap = new HashMap<>();
		TreeMap<CacheEntity2<String>, String> sortmap = new TreeMap<>(new Comparator<CacheEntity2<String>>() {
			@Override
			public int compare(CacheEntity2<String> o1, CacheEntity2<String> o2) {
				if(o1.getVisiCount() > o2.getVisiCount()) {
					return 1;
				}else if(o1.getVisiCount() < o2.getVisiCount()) {
					return -1;
				}
				return 0;
			}
		});
		for(int i = 0; i < 100; i++) {
			int count = (int)(Math.random() * 100);
//			System.out.println(count);
			CacheEntity2<String> val = new CacheEntity2<String>("", 0, count);
			val.setKey("key" + i);
			cmap.put("key" + i, val);
			sortmap.put(val, null);
		}
		Iterator<Entry<CacheEntity2<String>, String>> entryS = sortmap.entrySet().iterator();
		int rmCount = (int)(sortmap.size() * 0.1);
		int i = 0;
		while(entryS.hasNext()) {
			if(++i <= rmCount) {
				Entry<CacheEntity2<String>, String> toRemo = entryS.next();
//				entryS.remove();//本策略是这样规定
				cmap.remove(toRemo.getKey().getKey());
				continue;
			}
			Entry<CacheEntity2<String>, String> cur = entryS.next();
//			System.out.println(cur.getKey().getVisiCount());
		}
		System.out.println(rmCount);
		System.out.println(cmap.size() + "----------");
	}

	private static void test() {
//		TreeMap<String, CacheEntity2<String>> map = new TreeMap<>(new Comparator<String>() {
//
//			@Override
//			public int compare(String o1, String o2) {
//				
//				return 0;
//			}
//		});
//		map.tailMap(fromKey)
		
		Map<String, CacheEntity2<String>> cmap = new HashMap<>();
		for(int i = 0; i < 100; i++) {
			CacheEntity2<String> val = new CacheEntity2<String>("", 0, i+1);
			cmap.put("key" + i, val);
		}
		
		Collection<CacheEntity2<String>> col = cmap.values();
		List<CacheEntity2<String>> li = new ArrayList<>(col);
		Collections.sort(li, new Comparator<CacheEntity2<String>>() {
			@Override
			public int compare(CacheEntity2<String> o1, CacheEntity2<String> o2) {
				if(o1.getVisiCount() > o2.getVisiCount()) {
					return 1;
				}else if(o1.getVisiCount() < o2.getVisiCount()) {
					return -1;
				}
				return 0;
			}
		});
		int end = li.size() * 1/2;
		for(int i = 0; i < end;i++) {
			col.remove(li.get(i));
		}
		System.out.println(cmap.size());
	}
}
