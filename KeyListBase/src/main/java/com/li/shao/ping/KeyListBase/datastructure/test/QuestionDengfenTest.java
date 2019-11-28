package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;

import com.google.common.collect.Lists;

public class QuestionDengfenTest {

	public static void main(String[] args) {
//		List<double[]> points = dengfen(new int[][]{{1,2},{1,5},{3,5},{3,7},{5,7},{5,2},{1,2}}, 2);
		List<double[]> points = dengfen(new int[][]{{1,1},{1,5},{5,5},{5,1},{1,1}}, 999);

		points.stream().forEach(item ->{
			System.out.print("(" + item[0] + "," + item[1] + "),");
		});
	}

	private static List<double[]> dengfen(int[][] arr, int k) {
		//总长：
		int len = 0;
		double avgLen = 0;
		int[] start = arr[0];
		for(int i = 1; i < arr.length; i++) {
			if(start[0] == arr[i][0]) {//同x
				len += Math.abs(start[1] - arr[i][1]);
			}else {
				len += Math.abs(start[0] - arr[i][0]);
			}
			start = arr[i];
		}
		avgLen = len / (double)k;
		List<double[]> li = Lists.newArrayList();
		//增加均长
		double rest = 0;
		int[] prePoint = arr[0];
		int j = 1;
		while(j < arr.length) {
			//新增长度，看可以容纳多少个avgLen,得出分割点， 然后再得出下一次的rest(必然小于avgLen)
			int[] nextPoint = arr[j];
			double chang = 0;
			int x = -1;
			if(prePoint[0] == nextPoint[0]) {
				chang = nextPoint[1] - prePoint[1];
			}else {
				x = 0;
				chang = nextPoint[0] - prePoint[0];
			}
			double restPre = rest;
			rest += Math.abs(chang);
			double step = (chang > 0 ? avgLen - restPre : -avgLen + restPre);
			while(rest - avgLen >= 0) {//有点可加
//				li.add(new double[] {prePoint[1 + x] + (x == 0 ? step : 0), prePoint[0 - x] + (x == -1 ? step : 0)});
				li.add(new double[] {prePoint[0] + (x == 0 ? step : 0), prePoint[1] + (x == -1 ? step : 0)});
				rest -= avgLen;
				step += (step > 0 ? avgLen : -avgLen);
			}
			prePoint = nextPoint;
			j++;
		}
		return li;
	}
}
