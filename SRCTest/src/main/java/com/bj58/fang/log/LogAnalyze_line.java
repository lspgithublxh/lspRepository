package com.bj58.fang.log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * 一天之内的版本 统计分布图
 * @ClassName:LogAnalyze_line
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月17日
 * @Version V1.0
 * @Package com.bj58.fang.log
 */
public class LogAnalyze_line extends Application{

	public static void main(String[] args) {
		generate("D:\\software\\tt.txt", 2);
		mapreduce(table);
		launch(args);
	}
	
	private static void generate(String file, int count) {
		Pattern p = Pattern.compile("^(.+)?,.+?stat_ip.+?ver\\:(.+)?,", Pattern.CASE_INSENSITIVE);
		BufferedReader reader;
		List<String> table = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.find()) {
					String valx = "";
					for(int i = 1; i <= count; i++) {
						String val = m.group(i);
						valx += val + ",";
					}
					valx = valx.length() > 0 ? valx.substring(0, valx.length()) : valx;
					table.add(valx);
				}
			}
			LogAnalyze_line.table = table;
		}catch (Exception e) {
		}
	}
	
	public static void mapreduce(List<String> table) {
		Map<String, Map<String, Integer>> map = new TreeMap<>();
		for(String x : table) {
			String[] da = x.split(",");
			String xx = da[0].split(":")[0];
			if(map.get(da[1]) == null) {
				Map<String, Integer> banben = new TreeMap<String, Integer>();
				banben.put(xx, banben.get(xx) == null ? 1 : banben.get(xx) + 1);
				map.put(da[1], banben);
			}else {
				Map<String, Integer> banben = map.get(da[1]);
				banben.put(xx, banben.get(xx) == null ? 1 : banben.get(xx) + 1);
			}
//			map.put(xx + da[1], map.get(xx) == null ? 1 : map.get(xx) + 1);
		}
		
		for(Entry<String, Map<String, Integer>> entry : map.entrySet()) {
			List<String> li = new ArrayList<>();
			for(Entry<String, Integer> en : entry.getValue().entrySet()) {
				li.add(en.getKey() + "," + en.getValue());
			}
			System.out.println(li);
			table2.put(entry.getKey(), li);
		}
	}
	static Map<String, List<String>> table2 = new HashMap<>();
	static List<String> table = new ArrayList<>();
	
	@Override
	public void start(Stage arg0) throws Exception {
		LineChart<String, Number> chart = new LineChart<>(new CategoryAxis(), new NumberAxis());
		chart.autosize();
		chart.setCreateSymbols(false);
		int count = 0;
		for(Entry<String, List<String>> en : table2.entrySet()) {
			System.out.println(en.getKey());
			chart.getData().add(drawImage(en.getValue(), en.getKey()));
//			if(count++ >= 5) break; 
		}
		
		Scene scene = new Scene(chart, 600, 400);
		Stage stage = new Stage();
		stage.setTitle("统计图");
		stage.setScene(scene);
		stage.show();
		
	}
	
	private Series<String, Number> drawImage(List<String> data, String value) {
		Series<String, Number> se = new XYChart.Series<>();
		int sc = 0;
		for(String item : data) {
			String[] x_y = item.split(",");
			se.getData().add(new Data<String, Number>(x_y[0], Double.valueOf(x_y[1])));
		}
		se.setName(value);
		return se;
	}

}
