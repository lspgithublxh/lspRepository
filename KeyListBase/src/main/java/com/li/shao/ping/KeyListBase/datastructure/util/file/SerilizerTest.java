package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.TreeMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.SerializerUtil;

import avro.shaded.com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;

public class SerilizerTest {

	public static void main(String[] args) {
//		test();
		
//		new SerilizerTest().test2();
		test2x();
	}

	private static void test2x() {
		File file = new File("D:\\test\\c.bin");
		SerializerUtil util = new SerializerUtil(Entity.class, TreeMap.class);
		TreeMap<String, Entity> map = Maps.newTreeMap();
		map.put("hehe", new Entity().setAge(11).setVal("name"));
		map.put("ss", new Entity().setAge(11).setVal("name"));
		map.put("rrr", new Entity().setAge(11).setVal("name"));

		long[] l2 = util.serialize2(map, file);
		TreeMap trmap = util.deserialize3(file, TreeMap.class, l2[0], l2[1]);
		System.out.println(trmap);
	}

	@Data
	@Accessors(chain = true)
	static class Entity{
		private String val;
		private int age;
	}
	
	private static void test() {
		Kryo kryo = new Kryo();
		kryo.setReferences(true);
		kryo.register(HashMap.class);
		Output out;
		try {
			out = new Output(new FileOutputStream("D:\\test\\c.bin"));
			HashMap<Object, Object> map = Maps.newHashMap();
			map.put("name", "lsp");
			map.put("simple", 1);
			kryo.writeObject(out, map);
			out.close();
			Input input = new Input(new FileInputStream("D:\\test\\c.bin"));
			HashMap readObject = kryo.readObject(input, HashMap.class);
			System.out.println(readObject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void test2() {
		Kryo kryo = new Kryo();
		kryo.setReferences(true);
		kryo.register(TreeMap.class);
		kryo.register(Entity.class);
		Output out;
		try {
			out = new Output(new FileOutputStream("D:\\test\\c.bin"));
			FileOutputStream output2 = new FileOutputStream("D:\\test\\c.bin", true);
			long end = output2.getChannel().position();
			TreeMap<String, Entity> map = Maps.newTreeMap();
			map.put("hehe", new Entity().setAge(11).setVal("name"));
			map.put("ss", new Entity().setAge(11).setVal("name"));
			map.put("rrr", new Entity().setAge(11).setVal("name"));
			kryo.writeObject(out, map);
			out.close();
			Input input = new Input(new FileInputStream("D:\\test\\c.bin"));
			TreeMap readObject = kryo.readObject(input, TreeMap.class);
			System.out.println(readObject);
			FileOutputStream output3 = new FileOutputStream("D:\\test\\c.bin", true);
			long end3 = output3.getChannel().position();
			System.out.println(end + "," + end3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
