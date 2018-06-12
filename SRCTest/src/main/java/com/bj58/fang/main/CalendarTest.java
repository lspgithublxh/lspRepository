package com.bj58.fang.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarTest {

	public static void main(String[] args) {
		test2();
	}

	private static void test2() {
		long now = System.currentTimeMillis();
		long week = 7 * 24 * 60 * 60 * 1000;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			long start = f.parse("2018-06-06 00:00:00").getTime();
			System.out.println(start);
			long t2 = f.parse("2018-06-11 00:00:00").getTime();
			long t3 = f.parse("2018-06-18 00:00:00").getTime();
			System.out.println(start / week);
			System.out.println(now / week);
			System.out.println((now - start) / week);
			System.out.println((t2 - start) / week);
			System.out.println((t3 - start) / week);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void test() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println("1:" + f.format(f.parse("2018-06-09 12:12:12")));
			System.out.println("2:" + f.parse("2018-06-09 12:12:12").getTime());
			System.out.println("3:" + f.parse("2018-06-09 00:00:00").getTime());
			System.out.println("3:" + System.currentTimeMillis());
			Calendar c = Calendar.getInstance();
			c.set(2018, 6, 4);
			Calendar c2 = Calendar.getInstance();
			System.out.println(c.get(Calendar.DATE));
			System.out.println(c2.compareTo(c));
			System.out.println(((double)(f.parse("2018-06-09 12:12:12").getTime()) / 1000) / 60 /60 /24);
			System.out.println(((double)(f.parse("2018-06-09 23:59:59").getTime()) / 1000) / 60 /60 /24);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long t = System.currentTimeMillis();
		System.out.println(((double)(t) / 1000) / 60 /60 /24);
		
	}
}
//17691.11033074074
//17691.110479097224
//17691.110620069445
//17691.110899398147