package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class LogAnalyze extends Application{

	static List<List<String>> table = null;
	public static void main(String[] args) {
//		test();
//		test2();
//		userAnalize(args);
		restrictIpAnalize2(args);
	}

	private static void restrictIpAnalize2(String[] args) {
//		String regex = "-\\s+(\\S+?)restrict.+?success\\:(\\S+)$";
//		int count = 2;
//		String file = "D:\\software\\error.txt";
//		table = tableData(regex, count, file);
//		launch(args);
		
		
		String regex = "stat_ip.+?ver\\:(.+)?,";
		int count = 1;
		String file = "D:\\software\\tt.txt";
		table = tableData(regex, count, file);
		List<String> d = new ArrayList<>();
		List<List<String>> d2 = new ArrayList<>();
		for(String ss : table.get(0)) {
			d.add(ss.substring(0, ss.lastIndexOf(".")));
		}
		d2.add(d);
		table = d2;
//		for(String ip : table.get(0)) {
//			System.out.println(IPLocation.test(ip.split(";")[0]));
//		}//版本分布和ip定位分布
		launch(args);
	}
	
	private static void restrictIpAnalize(String[] args) {
//		String regex = "-\\s+(\\S+?)restrict.+?success\\:(\\S+)$";
//		int count = 2;
//		String file = "D:\\software\\error.txt";
//		table = tableData(regex, count, file);
//		launch(args);
		
		
//		String regex = "stat_ip\\:(.+)?,lat";
		String regex = "lat\\:(.+)?,lon\\:(.+)?,os";
		int count = 2;
		String file = "D:\\software\\version.txt";
		table = tableData(regex, count, file);
		System.out.println(table.get(0).size());
		int size = table.get(0).size();
		for(int i = 0; i < size; i++) {
			System.out.println(IPLocation.test2(table.get(1).get(i), table.get(0).get(i)));
//			System.out.println(IPLocation.test2(ip.split(";")[0]));
		}
		launch(args);
	}

	/**
	 * 加了系统保护之后：
	 * protect system catch：江苏省-南京市-鼓楼区-\n,11025;江苏省-徐州市--\n,4028;广东省-广州市-荔湾区-\n,1966;江苏省-泰州市-靖江市-\n,985;
	 * toofast:江苏省-南京市-鼓楼区-\n,350;江苏省-徐州市--\n,138;广东省-广州市-荔湾区-\n,70;江苏省-泰州市-靖江市-\n,54;北京市-北京市-西城区-\n,1;
	 * blacklist:江苏省-南京市-鼓楼区-\n,14885;江苏省-徐州市--\n,6447;广东省-广州市-荔湾区-\n,3068;江苏省-泰州市-靖江市-\n,1519;
	 * 未加系统保护之前：
	 * blacklist:江苏省-南京市-鼓楼区-\n,23782;江苏省-徐州市--\n,10090;广东省-广州市-荔湾区-\n,4798;江苏省-泰州市-靖江市-\n,2453;
	 * toofast:江苏省-南京市-鼓楼区-\n,603;江苏省-徐州市--\n,252;广东省-广州市-荔湾区-\n,125;江苏省-泰州市-靖江市-\n,62;
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月16日
	 * @Package com.bj58.fang.log
	 * @return String
	 */
	public static String getIps() {
		String regex = "-\\s+toofastrestrict.+?success\\:(\\S+)$";
		int count = 1;
		String file = "D:\\software\\error2.txt";
		table = tableData(regex, count, file);
		return mapreduce(table.get(0));
	}
	
	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月16日
	 * @Package com.bj58.fang.log
	 * @return void
	 */
	public static void analizeForOut(String[] args, List<List<String>> tableData) {
		table = tableData;
		launch(args);
	}
	
	private static void userAnalize(String[] args) {
		String regex = "ip:(\\S+?),os:(\\S+?),useragent:(.+?),deviceId:(\\S+?),userid:(\\S+?),version:(\\S+?)$";
		int count = 6;
		String file = "D:\\software\\sos.txt";
		table = tableData(regex, count, file);
		launch(args);
	}

	private static String mapreduce(List<String> data){
		Map<String, Integer> ma = new TreeMap<>();
		for(String d : data) {
			if(ma.containsKey(d)) {
				ma.put(d, ma.get(d) + 1);
			}else {
				ma.put(d, 1);
			}
		}
		List<Object[]> li = new ArrayList<>();
		for(String ip : ma.keySet()) {
			li.add(new Object[] {ip, ma.get(ip)});
		}
		Collections.sort(li, new Comparator<Object[]>() {
			@Override
			public int compare(Object[] a, Object[] b) {
				if(((Integer)a[1]) > ((Integer)b[1])) {
					return -1;
				}else if(((Integer)a[1]) < ((Integer)b[1])){
					return 1;
				}
				return 0;
			}
		});
		String rs = "";
		int count = 0;
		for(Object[] it : li) {
			if(count++ > 100) break;
			rs += ((String)it[0]) + "," +  ((Integer)it[1]) + ";";
		}
		System.out.println(rs);
		return rs;
	}
	
	
	
	public static List<List<String>> tableData(String regex, int count, String file) {
		Pattern p = Pattern.compile(regex);
		BufferedReader reader;
		List<List<String>> table = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			int lcc = 0;
			String line = null;
			while((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				lcc++;
				if(m.find()) {
					for(int i = 1; i <= count; i++) {
						String val = m.group(i);
						if(table.size() > i - 1) {
							table.get(i - 1).add(val);
						}else {
							List<String> li = new ArrayList<>();
							li.add(val);
							table.add(li);
						}
					}
				}
			}
			System.out.println(lcc);
		}catch (Exception e) {
		}
		return table;
	}



	private static void test2() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("D:\\software\\aaa.txt"));
			String line = null;
			Map<String, Integer> map = new HashMap<String, Integer>();
			Pattern p = Pattern.compile("\\s+'(\\S+)?'$", Pattern.CASE_INSENSITIVE);
			while((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.find()) {
					String ip = m.group(1);
					map.put(ip, map.containsKey(ip) ? map.get(ip) + 1 : 1);
				}
			}
			List<Object[]> li = new ArrayList<>();
			for(String ip : map.keySet()) {
				li.add(new Object[] {ip, map.get(ip)});
			}
			Collections.sort(li, new Comparator<Object[]>() {
				@Override
				public int compare(Object[] a, Object[] b) {
					if(((Integer)a[1]) > ((Integer)b[1])) {
						return 1;
					}else if(((Integer)a[1]) < ((Integer)b[1])){
						return -1;
					}
					return 0;
				}
			});
			for(Object[] it : li) {
				System.out.println(((String)it[0]) + ", " +  ((Integer)it[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
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

	@Override
	public void start(Stage arg0) throws Exception {
		for(List<String> d : table) {
			drawImage(mapreduce(d));
		}
		
	}

	private void drawImage(String data) {
		PieChart pchart = new PieChart();
		String[] d = data.split(";");
		int totalCount = 0;
		int i = 0;
		for(String da : d) {
			String[] dx = da.split(",");
			totalCount += Integer.valueOf(dx[1]);
		}
		for(String da : d) {
			String[] dx = da.split(",");
//			dx[0] = String.format("%s_%.1f%%_%s", dx[1], (Integer.valueOf(dx[1]) / (double )totalCount) * 100, dx[0]);
			dx[0] = String.format("%s %.1f%%", dx[0], (Integer.valueOf(dx[1]) / (double )totalCount) * 100);
			if(dx[0].length() > 20) {
				dx[0] = dx[0].substring(0, 15);
			}
			pchart.getData().add(new PieChart.Data(dx[0], Integer.valueOf(dx[1])));
		}
//		pchart.getData().add(new PieChart.Data("样式1", 230));
//		pchart.getData().add(new PieChart.Data("样式2", 130));
//		pchart.getData().add(new PieChart.Data("样式3", 30));
//		pchart.getData().add(new PieChart.Data("样式4", 330));
		pchart.autosize();
		pchart.setLabelLineLength(10);
		pchart.setLegendSide(Side.LEFT);
		pchart.setTitle("2018.11.27 二手房大类页");
		pchart.setStartAngle(180);
		
		Scene scene = new Scene(pchart, 600, 400);
		Stage stage = new Stage();
		stage.setTitle("访问者版本分布统计");
		stage.setScene(scene);
		stage.show();
	}
}
