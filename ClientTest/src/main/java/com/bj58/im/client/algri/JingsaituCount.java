package com.bj58.im.client.algri;

import java.util.ArrayList;
import java.util.List;

public class JingsaituCount {

	public static void main(String[] args) {
		start(5);
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
	
	private static void printArr(int[] item) {
		for(int i : item) {
			System.out.print(i + ",");
		}
		System.out.println();
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
}
