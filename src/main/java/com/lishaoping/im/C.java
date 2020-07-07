package com.lishaoping.im;

import java.util.Scanner;

public class C {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			try {
				String line = scanner.nextLine();
				String[] arr = line.split("\\s+");
				if(arr == null || arr.length != 2) {
					System.out.println("-1");
					continue;
				}
				int n = Integer.valueOf(arr[0]);
				int ch = Integer.valueOf(arr[1]);
				if(ch < 0 || ch > 9 || n < 0) {
					System.out.println("-1");
					continue;
				}
				int total = count(n+"", ch);
				System.out.println(total);
			} catch (NumberFormatException e) {
				System.out.println("-1");
			}catch (Exception e) {
				
			}
			
		}
	}

	private static int count(String number, int ch) {
		int i = 0;
		int total1 = 0;
		int len = number.length();
		for(; i < number.length(); i++) {
			//第i位的出现的总次数
			char zi = number.charAt(i);
			
			String sub = i >= len - 1 ? "0" : number.substring(i + 1);
			int rightV = Integer.valueOf(sub);
			String leftSub = i == 0 ? "0" : number.substring(0, i);
			int leftV = Integer.valueOf(leftSub);
			
			int wei = number.length() - i - 1; 
			int v = (int) Math.pow(10, wei);
			if(zi - '0' == ch) {
				//先计算右边的个数：
				int tempV = rightV + 1;//右边部分
				
				if(leftV > 0) {
					tempV += leftV * v;//左边的部分
				}
				total1 += tempV;
			}else if(zi - '0' > ch){
				int tempV = (leftV+1) * v;
				total1 += tempV;
			}else {
				if(leftV > 0) {
					int tempV = (leftV) * v;
					total1 += tempV;
				}
			}
		}
		return total1;
	}
}
