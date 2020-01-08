package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.google.protobuf.UnknownFieldSet;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD.Entity;

import avro.shaded.com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

/**
 * repeat则是数组
 *
 * @author lishaoping
 * @date 2020年1月7日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.seria
 */
@Slf4j
public class STest {

	private SimpleThreadPoolUtil tpool1 = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	
	@Test
	public void deserialize() {
		SerializerUtilProtobuf sp = new SerializerUtilProtobuf();
		Map<String, Entity> data2 = Maps.newTreeMap();
		data2.put("hahah", Entity.newBuilder().setStatus(1).setVal("nob").build());
		data2.put("hahah2", Entity.newBuilder().setStatus(1).setVal("nob2").build());
		data2.put("hahah3", Entity.newBuilder().setStatus(1).setVal("nob3").build());
		long[] end = sp.serialize(data2, new File("D:\\msc\\a.txt"));
		System.out.println(end[0] + "," + end[1]);
	}
	
	@Test
	public void concurrentBuild() {
		AtomicInteger ai = new AtomicInteger(0);
		for(int i = 0; i < 10; i++) {
			tpool1.addTask(()->{
				try {
					MapData.MD.Builder builder = MapData.MD.newBuilder();
					int j = ai.incrementAndGet();
					log.info("xx" + j);
					Map<String, Entity> data2 = Maps.newTreeMap();
					data2.put("hahahx" + j, Entity.newBuilder().setStatus(1).setVal("nob").build());
//					data2.put("hahah2", Entity.newBuilder().setStatus(1).setVal("nob2").build());
//					data2.put("hahah3", Entity.newBuilder().setStatus(1).setVal("nob3").build());
					builder.putAllDdd(data2);
					MD data = builder.build();
					ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
					data.writeDelimitedTo(bout2);
					ByteArrayInputStream in = new ByteArrayInputStream(bout2.toByteArray());
					MD op = MapData.MD.parseDelimitedFrom(in);
					log.info("" + op);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MapData.MD.Builder builder = MapData.MD.newBuilder();
//		Map<String, Entity> data2 = builder.getDddMap();
		Map<String, Entity> data2 = Maps.newTreeMap();
		data2.put("hahah", Entity.newBuilder().setStatus(1).setVal("nob").build());
		data2.put("hahah2", Entity.newBuilder().setStatus(1).setVal("nob2").build());
		data2.put("hahah3", Entity.newBuilder().setStatus(1).setVal("nob3").build());
		builder.putAllDdd(data2);
		
		MD data = builder.build();
		ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
		try {
			data.writeDelimitedTo(bout2);
			ByteArrayInputStream in = new ByteArrayInputStream(bout2.toByteArray());
			MD op = MapData.MD.parseDelimitedFrom(in);
			System.out.println(op.getDddMap());
			
			ByteArrayOutputStream bout3 = new ByteArrayOutputStream();
			data.writeTo(bout3);
			MD parseFrom = MapData.MD.parseFrom(bout3.toByteArray());
			log.info("" + parseFrom);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
