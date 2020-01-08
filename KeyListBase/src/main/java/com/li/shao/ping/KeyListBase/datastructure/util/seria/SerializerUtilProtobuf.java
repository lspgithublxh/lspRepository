package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.TreeMap;

import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD.Entity;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData2.MD2;

import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @author lishaoping
 * @date 2020年1月8日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.seria
 */
@Slf4j
public class SerializerUtilProtobuf {

	public long[] serialize(Map<String, Entity> data, File outFile) {
		MapData.MD.Builder builder = MapData.MD.newBuilder();
		builder.putAllDdd(data);
		MD container = builder.build();
		try {
			FileOutputStream out = new FileOutputStream(outFile, true);
			long start = out.getChannel().position();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			container.writeTo(bout);
			writeToOut(bout, out);
			out.flush();
			out.close();
			FileOutputStream out2 = new FileOutputStream(outFile, true);
			long end = out2.getChannel().position();
			out2.close();
			return new long[] {start, end};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeToOut(ByteArrayOutputStream bout, FileOutputStream out) {
		try {
//			bout.writeTo(out);
			byte[] arr = bout.toByteArray();
			FileChannel channel = out.getChannel();
			ByteBuffer buffer = ByteBuffer.wrap(arr);
			channel.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long[] serialize2(Map<String, String> data, File outFile) {
		MapData2.MD2.Builder builder = MapData2.MD2.newBuilder();
		builder.putAllDdd(data);
		MD2 container = builder.build();
		try {
			FileOutputStream out = new FileOutputStream(outFile, true);
			long start = out.getChannel().position();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			container.writeTo(bout);
			writeToOut(bout, out);
			out.flush();
			out.close();
			FileOutputStream out2 = new FileOutputStream(outFile, true);
			long end = out2.getChannel().position();
			out2.close();
			return new long[] {start, end};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TreeMap<String, Entity> deserialize(File inFile) {
		try {
			MD op = MapData.MD.parseFrom(new FileInputStream(inFile));
			Map<String, Entity> dddMap = op.getDddMap();
			TreeMap<String, Entity> dataMap = Maps.newTreeMap();
			dataMap.putAll(dddMap);
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TreeMap<String, Entity> deserialize3(File inFile, long start, long end) {
		try {
			byte[] data = readFromStartToEnd(inFile, start, end);
			MD op = MapData.MD.parseFrom(data);
			Map<String, Entity> dddMap = op.getDddMap();
			TreeMap<String, Entity> dataMap = Maps.newTreeMap();
			dataMap.putAll(dddMap);
			return dataMap;
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
			if(count <= 0) {
				break;
			}
			totalRead += count;
			if(totalRead >= len) {
				count = len - nextStart;
			}
			buffer.flip();
			buffer.get(data, nextStart, count);
			buffer.compact();
			nextStart += count;
			start += count;
			channel.position(start);
			if(totalRead >= len) {
				break;
			}
		}
		finput.close();
		return data;
	}
	
	public TreeMap<String, String> deserialize2(File inFile, long start, long end) {
		try {
			byte[] data = readFromStartToEnd(inFile, start, end);
			MD2 op = MapData2.MD2.parseFrom(data);
			Map<String, String> dddMap = op.getDddMap();
			TreeMap<String, String> dataMap = Maps.newTreeMap();
			dataMap.putAll(dddMap);
			return dataMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
