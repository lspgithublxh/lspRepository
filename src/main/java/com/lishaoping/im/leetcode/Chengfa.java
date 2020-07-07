package com.lishaoping.im.leetcode;

public class Chengfa {

	public static void main(String[] args) {
		String rs = add("1234","4321");
		System.out.println(rs);
		String chen = cheng("12235555555555555555554", "1235555555555555555555534");
		System.out.println(chen);
	}
	
	

	private static String cheng(String num1, String num2) {
		int len1 = num1.length() - 1;
		int len2 = num2.length() - 1;
		String total = "0";
		int len11 = len1;
		for(;len1 >= 0 ;len1--) {
			char c1 = num1.charAt(len1);
			int number1 = c1 - '0';
			String cj = "";
			int len22 = len2;
			int dex = 0;
			for(;len22 >= 0; len22--) {
				char c2 = num2.charAt(len22);
				int number2 = c2 - '0';
				int rs = number2 * number1 + dex;
				dex = rs / 10;
				int show = rs % 10;
				cj = show + cj;
			}
			cj = dex > 0 ? dex + "" + cj : cj;
			for(int i = len1; i < len11; i++) {
				cj += "0";
			}
			total = add(total, cj);
		}
		return total;
	}



	private static String add(String num1, String num2) {
		int len1 = num1.length() - 1;
		int len2 = num2.length() - 1;
		int dex = 0;
		String rs = "";
		while(len1 >=0 || len2 >= 0) {
			char c1 = len1 >= 0 ? num1.charAt(len1) : '0';
			char c2 = len2 >= 0 ? num2.charAt(len2) : '0';
			int count = (c1 - '0') + (c2 - '0') + dex;
			dex = count / 10;
			int num = count % 10;
			rs = num + rs; 
			len1--;
			len2--;
		}
		rs = dex > 0 ? dex + "" + rs : rs;
		return rs;
	}
}
