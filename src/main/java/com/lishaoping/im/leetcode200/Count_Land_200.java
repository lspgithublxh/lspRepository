package com.lishaoping.im.leetcode200;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Count_Land_200 {

	public static void main(String[] args) {
		tess();
	}

	private static void tess() {
		//
		int[][] dao = {
				{1,1,0,0,0},
				{1,1,0,0,0},
				{0,0,1,0,0},
				{0,0,0,1,1}
				};
//			{
//			{1,1,1,1,0},
//			{1,1,0,1,0},
//			{1,1,0,0,0},
//			{0,0,0,0,0}
//			};
		int number = 2;
		List<Set<Integer>> daoList = new ArrayList<>();
		for(int i = 0; i < dao.length; i++) {
			for(int j = 0; j < dao[i].length; j++) {
				if(dao[i][j] == 1) {
					//只需看上-左
					int[] bianhao = new int[2];
					if(i - 1 >= 0) {
						bianhao[0] = dao[i-1][j];
					}
					if(j - 1 >= 0) {
						bianhao[1] = dao[i][j-1];
					}
					if(bianhao[0] + bianhao[1] <= 1) {//使用新的编号
						Set<Integer> set = new HashSet<>();
						int b = number++;
						set.add(b);
						daoList.add(set);
						number = addBianhao(dao, number, i, j, b);
					}else {
						//旧的编号
						int oldb = bianhao[0] > 1 ? bianhao[0] : bianhao[1];
						addBianhao(dao, number, i, j, oldb);
						//是否合并：
						if(bianhao[0] >1 && bianhao[1] > 1 && bianhao[0] != bianhao[1]) {//合并
							Set<Integer> shuyu = null;
							Set<Integer> shuyu2 = null;
							for(Set<Integer> s : daoList) {
								if(s.contains(bianhao[0])){
									shuyu = s;
									if(shuyu2 != null) {
										break;
									}
								}
								if(s.contains(bianhao[1])){
									shuyu2 = s;
									if(shuyu != null) {
										break;
									}
								}
							}
							shuyu.addAll(shuyu2);
							daoList.remove(shuyu2);
						}
					}
				}
			}
		}
		System.out.println(daoList);
	}

	private static int addBianhao(int[][] dao, int number, int i, int j, int b) {
		dao[i][j] = b;
		if(i - 1 >= 0 && dao[i-1][j] == 0) {
			dao[i-1][j] = b;
		}
		if(j - 1 >= 0 && dao[i][j-1] == 0) {
			dao[i][j-1] = b;
		}
		return number;
	}
}
