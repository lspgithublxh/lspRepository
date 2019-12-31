package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import avro.shaded.com.google.common.collect.Maps;
import lombok.Data;
import lombok.experimental.Accessors;

public class SerilizerTest {

	public static void main(String[] args) {
//		test();
		new SerilizerTest().test2();
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
		kryo.register(HashMap.class);
		kryo.register(Entity.class);
		Output out;
		try {
			out = new Output(new FileOutputStream("D:\\test\\c.bin"));
			HashMap<Object, Entity> map = Maps.newHashMap();
			map.put("name", new Entity().setAge(11).setVal("lsp"));
			map.put("simple", new Entity().setAge(11).setVal("lsp"));
			kryo.writeObject(out, map);
			out.close();
			Input input = new Input(new FileInputStream("D:\\test\\c.bin"));
			HashMap readObject = kryo.readObject(input, HashMap.class);
			System.out.println(readObject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
