package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class QuestionTowerTest {

	public static void main(String[] args) {
		List<List<Integer>> lis = Lists.newArrayList();
		lis.add(Lists.newArrayList(1,2,3,4));
		lis.add(Lists.newArrayList(5,8,3,4,5));
		lis.add(Lists.newArrayList(1,2,7,9,6,7));
		lis.add(Lists.newArrayList(5,2,1,4,6,7,8));
		findTarget(lis);
	}

	private static void findTarget(List<List<Integer>> lis) {
		//查询本级的位置和总值
		int curlvl = lis.size() - 1;
		List<Integer> l = lis.get(curlvl);
		Map<Integer, Integer> m = Maps.newHashMap();
		Map<Integer, String> mx = Maps.newHashMap();
		for(int i = 0; i < l.size(); i++) {
			m.put(i, l.get(i));
			mx.put(i, l.get(i) + ",");
		}
		curlvl--;
		while(curlvl >= 0) {
			List<Integer> heng = lis.get(curlvl);
			Map<Integer, Integer> m2 = Maps.newHashMap();
			Map<Integer, String> mx2 = Maps.newHashMap();
			for(int i = 0; i< heng.size(); i++) {
				if(m.get(i) < m.get(i + 1)) {
					m2.put(i, m.get(i) + heng.get(i));
					mx2.put(i, mx.get(i)  + heng.get(i) + ",");
				}else {
					m2.put(i, m.get(i + 1) + heng.get(i));
					mx2.put(i, mx.get(i + 1) + heng.get(i) + ",");
				}
			}
			m = m2;
			mx = mx2;
			curlvl--;
		}
		System.out.println(m);
		System.out.println(mx);
	}
}
