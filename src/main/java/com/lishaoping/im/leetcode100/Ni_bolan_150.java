package com.lishaoping.im.leetcode100;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Ni_bolan_150 {

	public static void main(String[] args) {
		tesst();
		ttt();
	}

	private static void ttt() {
		String s = "the day is monday";
		s = s.trim();
		String[] x = s.split("\\s+");
		List<String> li = Arrays.asList(x);
		Collections.reverse(li);
		String xs = String.join(" ", li.toArray(new String[0]));
		System.out.println(xs);
	}

	private static void tesst() {
		Stack<String> st = new Stack<>();
		String[] arr = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
		int i = 0;
		while(i < arr.length) {
			String obj = arr[i++];
			if(obj.equals("+")) {
				compute(st, '+');
			}else if(obj.equals("-")) {
				compute(st, '-');
			}else if(obj.equals("*")) {
				compute(st, '*');
			}else if(obj.equals("/")) {
				compute(st, '/');
			}else {
				st.push(obj);
			}
		}
		System.out.println("rs:" + st.pop());
	}

	private static void compute(Stack<String> st, char c) {
		String right = st.pop();
		String left = st.pop();
		int lef =  Integer.valueOf(left);
		int rig = Integer.valueOf(right);
		int v = 0;
		switch (c) {
		case '+':
			v = lef + rig;
			break;
		case '-':
			v = lef - rig;
			break;
		case '*':
			v = lef * rig;
			break;
		case '/':
			v = lef / rig;
			break;
		default:
			break;
		}
		st.push(v + "");
	}
}
