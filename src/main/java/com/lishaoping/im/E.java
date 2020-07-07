package com.lishaoping.im;

public class E {

	public static void main(String[] args) {
		String s = test("123", "233444");
		System.out.println(s);
	}

	private static String test(String number1, String number2) {
		int len1 = number1.length();
		int len2 = number2.length();
		int jin = 0;
		int index1 = len1 - 1;
		int index2 = len2 - 1;
		String s = "";
		for(;;) {
			if(index1 < 0 && index2 < 0) {
				break;
			}
			char c1 = index1 < 0 ? '0' : number1.charAt(index1);
			char c2 = index2 < 0 ? '0' : number2.charAt(index2);
			int rs = c1 - '0' + c2 - '0' + jin;
			int z = rs >= 10 ? rs - 10 : rs;
			jin = rs >= 10 ? 1: 0;
			s = z + s;
			index1--;
			index2--;
		}
		return s;
	}
}
