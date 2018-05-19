package com.bj58.fang.json;

import java.io.File;
import java.io.IOException;
import java.net.StandardSocketOptions;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class CompareTwoJson {

	public static void main(String[] args) throws IOException {
		test();
	}

	private static void test() throws IOException {
		String path = CompareTwoJson.class.getResource("").getPath();
		path = path.replace("/target/classes", "/src/main/java");
		String path1 = path + "json1.txt";
		String path2 = path + "json2.txt";
		String content1 = FileUtils.readFileToString(new File(path1), "utf-8");
		String content2 = FileUtils.readFileToString(new File(path2), "utf-8");
		JSONObject obj1 = JSONObject.parseObject(content1);
		JSONObject obj2 = JSONObject.parseObject(content2);
		System.out.println(obj1.toJSONString(obj1, SerializerFeature.SortField));
		System.out.println(obj2.toJSONString(obj2, SerializerFeature.SortField));
		System.out.println(obj1.toJSONString().length());
		System.out.println(obj2.toJSONString().length());
		
		
	}
	
	
}
