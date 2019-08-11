package com.bj58.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class FileRead {

	public static void main(String[] args) {
		method();
	}

	private static void method() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lishaoping\\Documents\\Downloads\\970.pid"));
			String line = "";
			Map<String, Map<String, Long>> map = new HashMap<>();
			while((line = reader.readLine()) != null) {
				String t = null;
				if(line.startsWith("\"pool-")) {
					t = "pool-";
				}else if(line.startsWith("\"SCF-asyncback_worker")){
					t = "SCF-asyncback_worker";
				}else if(line.startsWith("\"SCF_async_worker")) {
					t = "SCF_async_worker";
				}else {
					t = "else";
				}
				Map<String, Long> type = map.get(t);
				if(type == null) {
					type = new HashMap<>();
					map.put(t, type);
				}
				String tt = "";
				if(line.contains("waiting on condition")) {
					tt = "waiting on condition";
				}else if(line.contains(" runnable ")) {
					tt = " runnable ";
				}else {
					tt = "else";
				}
				type.put(tt, type.get(tt) == null ? 0 : type.get(tt) + 1);
				
			}
			System.out.println(JSONObject.toJSONString(map));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
