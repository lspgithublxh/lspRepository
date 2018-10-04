package com.bj58.im.client.algri;

import java.util.ArrayList;
import java.util.List;

public class JingsaituCount {

	public static void main(String[] args) {
//		start2(8);
		for(int i = 1; i < 13; i++) {
			start2(i);
		}
	}
	
	private static void start2(int num) {
		int[] ar = new int[num];
		ar[0] = num;
		List<int[]> lis = new ArrayList<int[]>();
		getZuhe2(ar, lis, num, 0);
//		System.out.println("-----------------------");
		List<String> set = new ArrayList<>();
		for(int[] item : lis) {
			String r = printArr(item);
			if(!set.contains(r)) {
				set.add(r);
			}
		}
//		System.out.println("-----------------------");
//		for(String k : set) {
//			System.out.println(k);
//		}
		System.out.println("set's length:" + set.size());
	}

	/**
	 * 先排列后计算
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月25日
	 * @Package com.bj58.im.client.algri
	 * @return void
	 */
	private static void start(int num) {
		int[] ar = new int[num];
		ar[0] = num;
		List<int[]> lis = new ArrayList<int[]>();
		getZuhe(ar, lis, num);
		System.out.println("-----------------------");
		for(int[] item : lis) {
			printArr(item);
		}
	}
	
	private static String printArr(int[] item) {
		String s = "";
		for(int i : item) {
//			System.out.print(i + ",");
			s += i + ",";
		}
//		System.out.println();
		return s;
	}

	/**
	 * 高位减一， 低位加一的办法
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月28日
	 * @Package com.bj58.im.client.algri
	 * @return void
	 */
	private static void getZuhe(int[] current, List<int[]> result, int n) {
		printArr(current);
		int count = 0;
		int[] next = new int[current.length];
		int sum = 0;
		for(int i = 0; i < current.length; i++) {
			count += current[i];
			sum += ((-(n - 1)) + i * 2) * current[i];
			next[i] = current[i];
		}
		if(count != n) {
			return;
		}else if(sum == 0){
			result.add(current);
		}
		//下一场
		for(int i = n - 2; i >= 0; i--) {
			if(next[i] > 0) {
				next[i]--;
				next[i + 1]++;
				//一种新的组合，可以计算而加上去
				getZuhe(next, result, n);
				break;
			}else if(next[i] == 0){
				continue;
			}
		}
		
	}
	
	private static void getZuhe2(int[] current, List<int[]> result, int n, int zuigao) {
//		printArr(current);
//		System.out.println("zuigao:" + zuigao);
		int count = 0;
		int[] next = new int[current.length];
		int sum = 0;
		for(int i = 0; i < current.length; i++) {
			count += current[i];
			sum += ((-(n - 1)) + i * 2) * current[i];
			next[i] = current[i];
		}
		if(count != n) {
			return;
		}else if(sum == 0){
			result.add(current);
		}
		if(zuigao == next.length - 1) {
			return;
		}
		//最高位和最低位是否和 为最大值
		if(next[zuigao] + next[next.length - 1] == n) {
			if(next[zuigao] > 0) {
				next[zuigao]--;
				next[zuigao + 1] = n - next[zuigao];
				if(zuigao + 1 != next.length - 1) {
					next[next.length - 1] = 0;
				}
//				zuigao = next[zuigao] == 0 ? zuigao + 1 : zuigao;
				//继续循环
				getZuhe2(next, result, count, zuigao);
				return;
			}else {
				if(zuigao == next.length - 2) {
					return;
				}else {
					next[zuigao + 1] = n;
					next[next.length - 1] = 0;
					//继续循环
					getZuhe2(next, result, count, zuigao + 1);
				}
			}
		}else {
			//下一场 高位减少1
			for(int i = n - 2; i >= 0; i--) {
				if(next[i] > 0) {
					next[i]--;
//					next[i + 1]++;
					int c = 0;
					for(int j = 0; j <= i; j++) {
						c += next[j];
					}
					next[i + 1] = n - c;
					for(int j = i + 2; j < next.length; j++) {
						next[j] = 0;
					}
					//一种新的组合，可以计算而加上去
					getZuhe2(next, result, n, zuigao);
					break;
				}else if(next[i] == 0){
					continue;
					
				}
			}
		}
		
		
	}
}
