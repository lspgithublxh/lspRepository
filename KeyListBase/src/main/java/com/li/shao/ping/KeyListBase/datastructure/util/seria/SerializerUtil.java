package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializerUtil {

	private Kryo kryo = new Kryo();
	
	public SerializerUtil(Class<?> entity) {
		kryo.setReferences(true);
		kryo.register(HashMap.class);
		kryo.register(entity);
	}
	
	public boolean serialize(Object obj, File file) {
		Output out;
		try {
			out = new Output(new FileOutputStream(file));
			kryo.writeObject(out, obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public <T> T deserialize(File file, Class<T> cls) {
		Input input;
		try {
			input = new Input(new FileInputStream(file));
			T readObject = kryo.readObject(input, cls);
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
