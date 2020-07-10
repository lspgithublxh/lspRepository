package com.lishaoping.im.leetcode100;

public class Jiayou_station134 {

	public static void main(String[] args) {
		tst();
	}

	private static void tst() {
		int[] gas = {1,2,3,4,5};
		int[] cos = {3,4,3,1,2};
		int rest = 0;
		int start = -1;
		int index = 0;
		while(true) {
			rest += gas[index] - cos[index];
			if(rest >= 0) {
				if(start == index) {
					break;
				}
				if(start == -1) {
					start = index;
				}
				index = (index + 1) % gas.length;
			}else {
				rest = 0;
				start = -1;
				index = (index + 1) % gas.length;
			}
		}
		System.out.println("start:" + start);
	}
}
