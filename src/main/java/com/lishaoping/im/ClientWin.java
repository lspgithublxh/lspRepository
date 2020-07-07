package com.lishaoping.im;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class ClientWin {

	public static void main(String[] args) {
		start();
	}

	private static void start() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			try {
				
				String line = scanner.nextLine();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String[] data = line.split("-");
				if(data.length != 3) {
					throw new Exception();
				}
				Calendar cd = Calendar.getInstance();
				int in_year = Integer.valueOf(data[0]);
				int in_mon = Integer.valueOf(data[1]);
				int in_day = Integer.valueOf(data[2]);
				cd.set(Calendar.YEAR, in_year);
				cd.set(Calendar.MONTH, in_mon - 1);
				cd.set(Calendar.DAY_OF_MONTH, in_day);
				int year = cd.get(Calendar.YEAR);
				int month = cd.get(Calendar.MONTH);
				int day = cd.get(Calendar.DAY_OF_MONTH);
				
				if(in_year < 1990 || in_year != year || in_mon != (month + 1) || in_day != day) {
					throw new Exception();
				}
				//Ê±¼ä²î
				long time = cd.getTimeInMillis();
				Calendar cd2 = Calendar.getInstance();
				cd2.set(Calendar.YEAR, 1990);
				cd2.set(Calendar.MONTH, 0);
				cd2.set(Calendar.DAY_OF_MONTH, 1);
				long time2 = cd2.getTimeInMillis();
				long dif_day = (time - time2) / (24 * 60 * 60 * 1000);
				long rest = dif_day % 5;
				if(rest <= 2) {
					System.out.println("He is working");
				}else {
					System.out.println("He is having a rest");
				}
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
		}
	}
	
	
}
