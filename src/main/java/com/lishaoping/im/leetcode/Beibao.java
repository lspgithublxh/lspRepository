package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 关键意识到：f[i,v]=f[i-1,v]+f[i,v-ci] 其中含义为： = 第i个元素使用次数为0的所有种类+ 第i个元素使用1\2\3(次数与v有关)...的所有可能种类。因此当v比较小，当v=ci时，则ci只使用了1次，且0则表示前i-1个元素一个也不用-则只有这一种情况，因此值恒等于1：f[v,0]=1
 *
 *@author lishaoping
 *Client
 *2020年7月4日
 */
public class Beibao {

	public static void main(String[] args) {
		count();
		list();
	}

	private static void list() {
		int[] candicates = {25,10,5,1};
		int target = 25;
		List<HashSet<String>> list = new ArrayList<>(target+1);
		for(int i = 0; i < target+1;i++) {
			list.add(new HashSet<>());
		}
		for(int i = 0; i < candicates.length; i++) {
			int candicate = candicates[i];
			list.get(0).add("");//
			
			for(int j = candicate; j < list.size(); j++) {
				Set<String> source = list.get(j - candicate);
				if(!source.isEmpty()) {
					List<String> nw = new ArrayList<>();
					for(String s : source) {
						nw.add(s + "+" + candicate);
					}
					list.get(j).addAll(nw);
				}
			}
			
		}
		list.get(target).forEach(it ->{
			System.out.println(it);
		});
		System.out.println(list.get(target).size());
	}

	private static void count() {
		int[] candicates = {25,10,5,1};
		int target = 25;
		int[] f = new int[target + 1];
//		int oneSeries = target % candicates[0] == 0 ? 1 : 0;
		//本质上,f[0] 不是=1，而是=[target%25,target%10,target%5,target%1]
		f[0]=1;//第i个元素完全可以被v整除，才有f(i,0)这个项目，而整除本身就是1种方案
		//初始化，前1个元素的构成的种数
//		for(int i = 1; i < f.length; i++) {
//			f[i] = i % candicates[0] == 0 ? 1 : 0;
//		}
		//
		for(int i = 0; i < candicates.length; i++) {
			int candicate = candicates[i];
//			f[0] = target % candicate == 0 ? 1 : 0;
			for(int j = candicate; j < f.length; j++) {//对于小于candicate部分 f[j]的值 不能由前i个元素构成，即增加的第i个元素无效---f[j-candicates]是0，所以保持不变。
				f[j] = f[j] + f[j - candicate];//计算前i个元素构成目标值j时，前i个元素目标值j-candicate后者已经计算出来，
			}
		}
		System.out.println(f[target]);
	}
}
