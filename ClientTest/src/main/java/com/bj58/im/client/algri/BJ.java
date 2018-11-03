package com.bj58.im.client.algri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BJ {

	public int[] s;
	public int[] w;
	public int[][] k;//空格点
	public BJ(int[] s, int[] w, int[][] k) {
		super();
		this.s = s;
		this.w = w;
		this.k = k;
	}
	
	public BJ zhuanzhi(int direct, int[] step) {
		if(direct == 1) {//不做
			
		}else if(direct == 2) {//
			int x = s[0];
			s[0] = s[1];
			s[1] = x;
			
			int temp = w[0];
			w[0] = w[1];
			w[1] = temp;
			for(int i = 0; i < k.length; i++) {
				int[] kk = k[i];
				int tt = kk[0];
				kk[0] = kk[1];
				kk[1] = tt;
			}
		}else if(direct == 3) {//
			w[0] += step[0];
			for(int i = 0; i < k.length; i++) {
				int[] kk = k[i];
				kk[0] += step[i+1]; 
			}
		}else if(direct == 4) {//
			w[0] -= step[0];
			for(int i = 0; i < k.length; i++) {
				int[] kk = k[i];
				kk[0] -= step[i+1]; 
			}
		}
		return this;
	}
	
	public static void main(String[] args) {
		String s = "0000000000000000009990000000";
		Pattern hp3 = Pattern.compile("009{3}00");
		Matcher m = hp3.matcher(s);
		if(m.find()) {
			System.out.println(m.start());
		}
	}
}
