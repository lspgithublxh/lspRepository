package com.bj58.fang.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SerializObject {

	public static void main(String[] args) {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Map<String, Object> config = new HashMap<String, Object>();
		map.put("config", config);
		config.put("name", "某个人");
		config.put("littleName", "xx");
		config.put("age", 28);
		config.put("message", new Message[] {new Message(1, "xiaomign", "北京"), new Message(2, "xiaowen", "上海")});
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("D:\\obj.txt")));
			out.writeObject(map);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("D:\\obj.txt")));
			Map<String, Map<String, Object>> map2 = (Map<String, Map<String, Object>>) in.readObject();
			System.out.println(map);
			System.out.println(map2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
