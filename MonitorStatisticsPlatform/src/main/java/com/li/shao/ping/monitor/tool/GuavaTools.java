package com.li.shao.ping.monitor.tool;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;

import lombok.Data;

@Data
public class GuavaTools {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		//集合创建
		List<String> list = Lists.newArrayList();//Lists.newCopyOnWriteArrayList();
		Set<String> set = Sets.newConcurrentHashSet();
		Map<String,String> map = Maps.newConcurrentMap();
		//不变集合
		ImmutableList<String> li = ImmutableList.of("", "b", "c");
		ImmutableMap<String, String> mmap = ImmutableMap.of("key", "val");
		//运算：map.set交集并集
		//字符串检测
		Strings.isNullOrEmpty("a");
		//文件操作
//		Files.copy(from, to);
	}
}
