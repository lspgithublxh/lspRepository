package com.bj58.fang.codeGenerate;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class JustTest implements Serializable{

	public static void main(String[] args) {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("1", "sx");
		map.put("2", "we");
		map.put("3", "vv");
		File file = new File("");
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String filePath = JustTest.class.getResource("").getPath();
		System.out.println(filePath);
		filePath = filePath.substring(filePath.indexOf("target/classes") + "target/classes".length()).replace("/", ".");
		System.out.println(filePath.substring(1, filePath.length() - 1));
		String[] ss = null;
		Map<String, String[]> m = new HashMap<String, String[]>();
		m.put("c", ss);
		m.put("d", new String[] {"ss", "kec"});
		System.out.println(m.get("c"));
		ss = new String[]{"c", "d"};
		String[] s = {"a", "c","cd"};
		System.out.println(m.get("c"));
		System.out.println(Arrays.asList(s).toString().replace("[", "{").replace("]", "}"));
		Long xx = 333l;
		System.out.println(xx.getClass().getName().split("\\.")[2]);
		BigDecimal[] big = new BigDecimal[] {new BigDecimal(3.5)};
		
		String[][] sssx = new String[][] {{"sss", "cd"},{"ed"}};
		String oo = "";
		for(String[] xs : sssx) {
			oo += Arrays.asList(xs).toString().replace("[", "{").replace("]", "}") + ",";
		}
		System.out.println(oo.substring(0, oo.length() - 1));
		System.out.println(sssx[0].getClass().getName().startsWith("["));
		String[][] ssssx = new String[][] {};
	}
}
