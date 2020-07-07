package com.lishaoping.im.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �ؼ���ʶ����f[i,v]=f[i-1,v]+f[i,v-ci] ���к���Ϊ�� = ��i��Ԫ��ʹ�ô���Ϊ0����������+ ��i��Ԫ��ʹ��1\2\3(������v�й�)...�����п������ࡣ��˵�v�Ƚ�С����v=ciʱ����ciֻʹ����1�Σ���0���ʾǰi-1��Ԫ��һ��Ҳ����-��ֻ����һ����������ֵ�����1��f[v,0]=1
 *
 *@author lishaoping
 *Client
 *2020��7��4��
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
		//������,f[0] ����=1������=[target%25,target%10,target%5,target%1]
		f[0]=1;//��i��Ԫ����ȫ���Ա�v����������f(i,0)�����Ŀ���������������1�ַ���
		//��ʼ����ǰ1��Ԫ�صĹ��ɵ�����
//		for(int i = 1; i < f.length; i++) {
//			f[i] = i % candicates[0] == 0 ? 1 : 0;
//		}
		//
		for(int i = 0; i < candicates.length; i++) {
			int candicate = candicates[i];
//			f[0] = target % candicate == 0 ? 1 : 0;
			for(int j = candicate; j < f.length; j++) {//����С��candicate���� f[j]��ֵ ������ǰi��Ԫ�ع��ɣ������ӵĵ�i��Ԫ����Ч---f[j-candicates]��0�����Ա��ֲ��䡣
				f[j] = f[j] + f[j - candicate];//����ǰi��Ԫ�ع���Ŀ��ֵjʱ��ǰi��Ԫ��Ŀ��ֵj-candicate�����Ѿ����������
			}
		}
		System.out.println(f[target]);
	}
}
