package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
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
			FileOutputStream output = new FileOutputStream(file, true);
			out = new Output(output);
			kryo.writeObject(out, obj);
			out.close();
			output.close();
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
			long end = channel.position();
			out.close();
			output.close();
			return new long[] {start, end};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> T deserialize2(File file, Class<T> cls, long start, long end) {
		Input input;
		try {
			byte[] data = readFromStart(file, start);
			input = new Input(data);
			T readObject = kryo.readObject(input, cls);
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T deserialize3(File file, Class<T> cls, long start, long end) {
		Input input;
		try {
			byte[] data = readFromStartToEnd(file, start, end);
			input = new Input(data);
			T readObject = kryo.readObject(input, cls);
			return readObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] readFromStartToEnd(File file, long start, long end) throws FileNotFoundException, IOException {
		FileInputStream finput = new FileInputStream(file);
		FileChannel channel = finput.getChannel();
		channel.position(start);
		int len = (int) (end - start);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] data = new byte[len];
		int nextStart = 0;
		int totalRead = 0;
		for(;;) {
			int count = channel.read(buffer);
			totalRead += count;
			if(count <= 0 || totalRead >= len) {
				break;
			}
			buffer.flip();
			buffer.get(data, nextStart, count);
			buffer.compact();
			nextStart += count;
			start += count;
			channel.position(start);
		}
		return data;
	}
	
	private byte[] readFromStart(File file, long start) throws FileNotFoundException, IOException {
		FileInputStream finput = new FileInputStream(file);
		FileChannel channel = finput.getChannel();
		channel.position(start);
		int len = (int) (channel.size() - start);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		byte[] data = new byte[len];
		int nextStart = 0;
		for(;;) {
			int count = channel.read(buffer);
			if(count <= 0) {
				break;
			}
			buffer.flip();
			buffer.get(data, nextStart, count);
			buffer.compact();
			nextStart += count;
			start += count;
			channel.position(start);
		}
		finput.close();
		return data;
	}
	
	public <T> T deserialize(File file, Class<T> cls) {
		Input input;
		try {
			FileInputStream finput = new FileInputStream(file);
			input = new Input(finput);
			T readObject = kryo.readObject(input, cls);
			finput.close();
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
