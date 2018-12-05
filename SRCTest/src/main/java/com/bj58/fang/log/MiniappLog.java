package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniappLog {

	public static void main(String[] args) {
//		generate("/software/after_2.txt", 1);
//		generate("/software/localquery.txt", 1);
		generate("/software/xiaoqu.txt", 1);
	}
	
	private static void generate(String file, int count) {
		Pattern p = Pattern.compile("^(\\d+)----xiaoqu$", Pattern.CASE_INSENSITIVE);
//		Pattern p = Pattern.compile("^(\\d+)-------localquery$", Pattern.CASE_INSENSITIVE);
//		Pattern p = Pattern.compile("^(\\d+)-------after$", Pattern.CASE_INSENSITIVE);
//	Pattern p = Pattern.compile("^(\\d+)-------localquery$", Pattern.CASE_INSENSITIVE);
		BufferedReader reader;
		List<String> table = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.find()) {
					String val = m.group(1);
					table.add(val);
				}
			}
			LogAnalyze_line.table = table;//累计平均耗时和，实时平均耗时
			int time = 0;
			for(String s : table) {
				time += Integer.valueOf(s);
			}
			System.out.println(time / (float)table.size());
		}catch (Exception e) {
		}
	}
}
