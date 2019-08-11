package com.bj58.jdkjinspecial.java18.lamda_methodExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.jdkjinspecial.atomic.Entity;

/**
 * 基本的：list转为map形式----其中key和value都通过传入的方法而获取
 * 经典的处理过程list-->map--->list--->item--->collect list---->
 * 
 * 
 * 功能角度：
 * stream()   集合转变为流对象(以便调用集合)
 * 
 * @ClassName:ListMapStream
 * @Description:
 * @Author lishaoping
 * @Date 2019年4月29日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial.java18.lamda_methodExpression
 */
public class ListMapStream {

	public static void main(String[] args) {
		//list to map
//		listToMap();
//		arrayToList();
//		castGood();
//		OptionalHandle();
//		listToArray();
//		CollectorsToHandle();
//		filterList();
//		listForeach();
//		sortedList();
		//更多流操作
		rangeHandle();
		//数组遍历转化
		foreachAndChange();
		//Math.toIntExact非intValue方式
		handleEntity();
		listToMap2();
	}

	private static void listToMap2() {
		Entity ent = new Entity("yes");
		List<Entity> li = new ArrayList<>();
		li.add(ent);
		Map<String, Entity> map = li.stream().collect(Collectors.toMap(ite -> ((Entity)ite).getName(), val -> (Entity)val));
		System.out.println(map);
	}

	private static void handleEntity() {
		Entity entity = new Entity("hahaha");
		List<Entity> li = new ArrayList<>();
		li.add(entity);
		List<String> ss = li.stream().map(Entity::getName).collect(Collectors.toList());
		System.out.println(ss);
	}

	private static void foreachAndChange() {
		Arrays.stream("1,2,3".split(",")).map(Integer::valueOf).collect(Collectors.toSet());
	}

	private static void rangeHandle() {
		IntStream.range(0, 10).forEach(num ->{
			System.out.println(num);
		});
	}

	private static void sortedList() {
		List<String> list = new ArrayList<>();
		list.add("dfdsfsf");
		list.add("abc");
		list.add("efg");
		list.add("hhaha");
		List<String> lst = list.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
		System.out.println(lst);
	}

	private static void listForeach() {
		List<String> list = new ArrayList<>();
		list.add("abc");
		list.add("efg");
		list.add("hhaha");
		list = Optional.ofNullable(list).map(li ->{return li;}).orElse(null);
		System.out.println(list);
		list = new ArrayList<>();
		try {
			list.stream().forEach(item -> {
				System.out.println("hehe");
			});
			JSONArray a = new JSONArray();
			a.add(new JSONObject());
			a.stream().forEach(item ->{
				System.out.println("hehe");
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void filterList() {
		List<String> list = new ArrayList<>();
		list.add("abc");
		list.add("efg");
		list.add("hhaha");
		List<String> li = list.stream().filter(item -> item.length() > 3).collect(Collectors.toList());
		System.out.println(li);
	}

	private static void CollectorsToHandle() {
		List<String> list = new ArrayList<>();
		list.add("abc");
		list.add("efg");
		list.add("hhaha");
//		Collectors.toSet();Collectors.toMap( item->item , item->item, (item1, item2)->item1);
		List<Integer> r = list.stream().map(item -> item.length()).collect(Collectors.toList());
		System.out.println(r);
		Map<String, String> map = list.stream().map(item1 -> item1).collect(Collectors.toMap(item->item, item->item, (item1, item2)->item1));
		System.out.println(map);
		Map<Integer, String> map2 = list.stream().map(item1 -> item1).collect(Collectors.toMap(String::length, item1->item1, (item1, item2)->item1));
		System.out.println(map2);
		Map<String, Integer> map3 = list.stream().map(item1 -> item1).collect(Collectors.toMap(String::toUpperCase, String::length, (item1, item2)->item1));
		System.out.println(map3);
	}

	private static void listToArray() {
		List<String> list = new ArrayList<>();
		list.add("abc");
		list.add("efg");
		list.add("hhaha");
		long[] ids = list.stream().mapToLong(item -> item.length()).toArray();
		for(long id : ids) {
			System.out.print(id + " ");
		}
	}

	private static void OptionalHandle() {
		List<String> list = new ArrayList<>();
		list.add("sssss");
		list.add("abc");
		list.add("1");
		//去重功能实现可以！！
		//相当于逻辑聚合了，功能整合了
		List<String> a = Optional.ofNullable(list).map((sss) -> sss.stream().map(item->item.length() > 3 ? item : null).collect(Collectors.toList())).orElse(null);
		System.out.println(a);
	}

	private static void castGood() {
		int itm = Integer.class.cast(11);//Arrays.stream(strs)
		System.out.println(itm);
		System.out.println(Integer.class.cast(null) + "---");
	}

	private static void arrayToList() {
		Arrays.stream("1,2,3,4".split(",")).forEach(item ->{
			System.out.println("-----" + item);
		});
	}

	private static void listToMap() {
		List<String> lis = new ArrayList<>();
		lis.add(1+"");
		lis.add(2+"");
		lis.add(3+"");
		//取键和值的方式：当方法如果是实体的方法时，直接调用；如果不是，则函数接口决定传参只有一个且为实例引用本身
//		Map<Integer, String> map = lis.stream().collect(Collectors.toMap(Integer::valueOf, Function.identity()));
		//对于，实体，可以传无参方法----和方法引用不同
		Map<Integer, String> map = lis.stream().collect(Collectors.toMap(String::length, Function.identity()));
		
		System.out.println(map);
		
	}
}
