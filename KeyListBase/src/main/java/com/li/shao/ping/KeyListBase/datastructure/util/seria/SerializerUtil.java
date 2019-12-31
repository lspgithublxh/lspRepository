package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PipedOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializerUtil {

	private Kryo kryo = new Kryo();
	
	public SerializerUtil(Class<?> entity, Class<?> container) {
		kryo.setReferences(true);
		kryo.register(container);
		kryo.register(entity);
	}
	
	public boolean serialize(Object obj, File file) {
		Output out;
		try {
			out = new Output(new FileOutputStream(file, true));
			kryo.writeObject(out, obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public long[] serialize2(Object obj, File file) {
		Output out;
		try {
			FileOutputStream output = new FileOutputStream(file, true);
			FileChannel channel = output.getChannel();
			long start = channel.position();
			out = new Output(output);
			kryo.writeObject(out, obj);
			out.close();
			long end = channel.position();
			return new long[] {start, end};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	
	public <T> T deserialize(byte[] data, Class<T> cls) {
		Input input;
		try {
			input = new Input(data);
			T readObject = kryo.readObject(input, cls);
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
