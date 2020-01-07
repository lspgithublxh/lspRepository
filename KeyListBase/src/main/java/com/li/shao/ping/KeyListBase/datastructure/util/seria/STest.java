package com.li.shao.ping.KeyListBase.datastructure.util.seria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import com.google.protobuf.UnknownFieldSet;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.MapData.MD.Entity;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.PersonProtos.Person;

import avro.shaded.com.google.common.collect.Maps;

public class STest {

	public static void main(String[] args) {
		MapData.MD.Builder builder = MapData.MD.newBuilder();
//		Map<String, Entity> data2 = builder.getDddMap();
		Map<String, Entity> data2 = Maps.newTreeMap();
		data2.put("hahah", Entity.newBuilder().setId(1).setNumber("nob").build());
		data2.put("hahah2", Entity.newBuilder().setId(1).setNumber("nob2").build());
		data2.put("hahah3", Entity.newBuilder().setId(1).setNumber("nob3").build());
		builder.putAllDdd(data2);
		
		MD data = builder.build();
		ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
		try {
			data.writeDelimitedTo(bout2);
			ByteArrayInputStream in = new ByteArrayInputStream(bout2.toByteArray());
			MD op = MapData.MD.parseDelimitedFrom(in);
			System.out.println(op.getDddMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PersonProtos.Person.Builder p = PersonProtos.Person.newBuilder();
		p.setEmail("xxx");
		p.setName("name");
		p.setId(1);
		Person p2 = p.build();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			p2.writeDelimitedTo(bout);
			ByteArrayInputStream in = new ByteArrayInputStream(bout.toByteArray());
			Person op = PersonProtos.Person.parseDelimitedFrom(in);
			System.out.println(op);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
