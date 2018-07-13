package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalyze {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		Pattern p = Pattern.compile("stat_landord_(\\d+)-(\\S+)?-(\\S+)?-(\\d+)", Pattern.CASE_INSENSITIVE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			BufferedReader reader = new BufferedReader(new FileReader("D:\\software\\abc.log"));
			String line = null;
			Map<String, Integer> map = new HashMap<String, Integer>();
			while((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.find()) {
					String userid = m.group(1);
					String cate = m.group(2);
					String version = m.group(3);
					String timestamp = m.group(4);
					String ver = version.substring(0, version.lastIndexOf("."));
					map.put(ver, map.containsKey(ver) ? map.get(ver) + 1 : 1);
					System.out.println(userid + ", " + cate + ", " + version + "," + format.format(new Date(Long.valueOf(timestamp))));
				}
			}
			System.out.println(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
