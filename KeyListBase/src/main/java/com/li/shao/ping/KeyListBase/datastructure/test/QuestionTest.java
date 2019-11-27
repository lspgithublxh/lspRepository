package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class QuestionTest {

	public static void main(String[] args) {
		//不重复的最长的字符串
		findLongestStr("eedddeserssfdsxfgrt");//dsxfgrt
		findLongestStr("eedddesesserssedrfcvgbhjnmklposfdsxdfdxsxfgrrtyugfded");//dsxfgrt
		findLongestStr("fcvgbhjnmklposfdsxdfdxsxfgrrtyugfded");//dsxfgrt
	}

	private static void findLongestStr(String str) {
		char[] arr = str.toCharArray();
		int length = 0;
		String sub = "";
		List<Character> cList = Lists.newArrayList();
		int left = 0;
		int right = 1;
		cList.add(arr[left]);
		while(right < arr.length) {
			int pos = cList.indexOf(arr[right]);
			if(pos >= 0) {
				if(cList.size() > length) {
					length = cList.size();
					sub = Joiner.on("").join(cList);
				}
				cList = cList.size() == 1 ? Lists.newArrayList() : cList.subList(pos + 1, cList.size());
				left = 0;
				cList.add(arr[right]);
				right += 1;
			}else {
				cList.add(arr[right]);
				right += 1;
			}
		}
		sub = cList.size() > length ? Joiner.on("").join(cList) : sub;
		System.out.println(sub);
	}
}
