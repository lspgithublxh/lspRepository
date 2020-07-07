package com.lishaoping.im;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class T {

	public static void main(String[] args) {
//		System.out.println(canHandle());
		System.out.println(match());
	}
	
	private static void start2() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			try {
				
				String line = scanner.nextLine();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String[] data = line.split("-");
				if(data.length != 3) {
					throw new Exception();
				}
//				Date date = sd.parse("2020-2-43");
//				System.out.println(sd.format(date));
				Calendar cd = Calendar.getInstance();
				int in_year = 2014;
				int in_mon = 12;
				int in_day = 22;
				cd.set(Calendar.YEAR, in_year);
				cd.set(Calendar.MONTH, in_mon - 1);
				cd.set(Calendar.DAY_OF_MONTH, in_day);
				System.out.println(sd.format(cd.getTime()));
				int year = cd.get(Calendar.YEAR);
				int month = cd.get(Calendar.MONTH);
				int day = cd.get(Calendar.DAY_OF_MONTH);
				
				System.out.println(year + "-" + (month + 1) + "-" + day);
				if(in_year < 1990 || in_year != year || in_mon != (month + 1) || in_day != day) {
					System.out.println("Invalid input");
					return;
				}
				//Ê±¼ä²î
				long time = cd.getTimeInMillis();
				Calendar cd2 = Calendar.getInstance();
				cd2.set(Calendar.YEAR, 1990);
				cd2.set(Calendar.MONTH, 0);
				cd2.set(Calendar.DAY_OF_MONTH, 1);
				System.out.println(sd.format(cd2.getTime()));
				long time2 = cd2.getTimeInMillis();
				long dif_day = (time - time2) / (24 * 60 * 60 * 1000);
				long rest = dif_day % 5;
				if(rest <= 2) {
					System.out.println("He is working");
				}else {
					System.out.println("He is having a rest");
				}
				System.out.println(dif_day);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Invalid input");
			}
		}
	}
	
	public static boolean match() {
		 Scanner scanner = new Scanner(System.in);
		    String pat = scanner.nextLine();
		    String str = scanner.nextLine();
		    char[] ma = pat.toCharArray();
		    String subs = str;
		    int index = 0;
		    while(subs.length() > 0) {
			    char[] source = subs.toCharArray();
			    int f = 0;
			    String mats = "";
			    int sourceIndex = 0;
			    while(f + 1 < ma.length) {
			    	char first = ma[f];
			    	char second = ma[f + 1];
			    	if(second == '*') {
			    		if(first == source[sourceIndex]) {
			    			f += 2;
			    			sourceIndex++;
			    			while(source[sourceIndex] == first) {
			    				sourceIndex++;
			    			}
			    		}else {
			    			f += 2;
			    		}
			    	}else if(second == '?') {
			    		if(first == source[sourceIndex]) {
			    			f += 2;
			    			sourceIndex++;
			    		}else {
			    			f += 2;
			    		}
			    	}else {
			    		if(first == source[sourceIndex]) {
			    			f++;
			    			sourceIndex++;
			    		}else {
			    			break;
			    		}
			    	}
			    }
			    if(f == ma.length - 1) {
			    	if(ma[f] == source[sourceIndex]) {
			    		return true;
			    	}else {
			    		break;
			    	}
			    }
			    if(index < subs.length() - 1) {
			    	subs = subs.substring(++index);
			    }else {
			    	break;
			    }
			   continue;
		    }
		    return false;
	}
	
	public static String canHandle(){
	    Scanner scanner = new Scanner(System.in);
	    String num = scanner.nextLine();
	    String shu = scanner.nextLine();
	    try {
			Integer n = Integer.valueOf(num);
			List<Integer> rs = Arrays.stream(shu.split("\\s+")).map(item -> Integer.valueOf(item))
					.collect(Collectors.toList());
			Integer small = rs.get(0);
			for (Integer item : rs) {
				if (small > item) {
					small = item;
				}
			}
			for (Integer item : rs) {
				Integer yu = item % small;
				if (yu != 0) {
					return "NO";
				}
				Integer shang = item / small;
				int start = 1;
				boolean b2 = false;
				while (start < shang) {
					if (start * 2 == shang) {
						b2 = true;
					}
					start = start * 2;
				}
				if (start == shang) {
					b2 = true;
				}
				if (!b2) {
					return "NO";
				}
			}
			return "YES";
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return "NO";
	}
}
