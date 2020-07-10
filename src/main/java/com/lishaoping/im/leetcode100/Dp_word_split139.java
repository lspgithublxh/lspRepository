package com.lishaoping.im.leetcode100;

import java.util.HashSet;
import java.util.Set;

public class Dp_word_split139 {

	public static void main(String[] args) {
		split();
	}

	private static void split() {
		String s = "leetcode";
		Set<String> set = new HashSet<>();
		set.add("leet");
		set.add("code");
		set.add("lee");
		set.add("tcode");
		int[][] f = new int[s.length()][s.length()];
		String[][] spl = new String[f.length][f[0].length];
		// i,是开始,j是结束
		for(int i = 0; i < f[0].length;i++) {
			f[i][i] = set.contains(s.substring(i, i+1)) ? 1 : 0;
		}
		//两个字符以上：
		for(int len = 2; len <= s.length(); len++) {
			for(int j = 0; j+len <= s.length(); j++) {
				//首先看全部：
				int endIndex = j+len;
				if(set.contains(s.subSequence(j, endIndex))) {
					f[j][endIndex-1] += 1;
				}
				for(int k = j; k < endIndex-1; k++) {
					if(f[j][k] * f[k+1][endIndex-1] > 0) {
						System.out.println(s.substring(j, k+1) + ", " + s.substring(k+1, len));
					}
					if(f[j][k] * f[k+1][endIndex-1] > 0) {
						spl[j][endIndex-1] = s.substring(j, k+1) + ", " + s.substring(k+1, len);
					}
					f[j][endIndex-1] += f[j][k] * f[k+1][endIndex-1];
					
				}
			}
		}
		System.out.println(f[0][s.length() - 1]);
		System.out.println(spl[0][s.length() - 1]);
	}
}
