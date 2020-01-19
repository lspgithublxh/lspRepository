package com.li.shao.ping.KeyListBase.datastructure.util.httpserver;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceMapper {
	
	public static ResourceMapper instance = new ResourceMapper();

	private Pattern pattern = Pattern.compile("\\$\\{(\\S+?)\\}");
	private Pattern pForeach = Pattern.compile("\\$foreach\\((.+?)\\)(.+?)\\$end", Pattern.DOTALL);

	public String matchAndReplace(String resource, Map<String, Object> resourceMap) {
		//第一种情况：数组替换
		String copy = resource;
		try {
			Matcher pFor = pForeach.matcher(resource);
			while(pFor.find()) {
				String expression = pFor.group(1);
				String[] expreArr = expression.split("\\s+");
				String item = expreArr[0];
				String from = expreArr[2];
				Object res = null;
				if(from.contains(".")) {
					String[] complexO = from.split("\\.");
					res = resourceMap.get(complexO[0]);
					for(int i = 1; i < complexO.length; i++) {
						res = getFromAttr(res, complexO[i]);
					}
				}else {
					res = resourceMap.get(from);
				}
				if(res == null) {
					continue;
				}
				String content = pFor.group(2);
				StringBuffer buffer = new StringBuffer();
				if(content.contains("$foreach")) {
					content = matchAndReplace(resource, resourceMap);
				}else {
					Collection coll = (Collection) res;
					
					for(Object o : coll) {
						resourceMap.put(item, o);
						String newBlock = valReplace(content, resourceMap);
						buffer.append(newBlock);
					}
				}
//				copy = pFor.replaceFirst(buffer.toString());//也可以直接截断的方式；
//				pFor = pattern.matcher(copy);
				int pos = pFor.start();
				int pos2 = pFor.end();
				copy = copy.substring(0, pos) + buffer.toString() + copy.substring(pos2);
				resource = copy;
				pFor = pForeach.matcher(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resource = copy;
		resource = valReplace(resource, resourceMap);
		return resource;
	}

	private String valReplace(String resource, Map<String, Object> resourceMap) {
		try {
			Matcher matcher = pattern.matcher(resource);
			while(matcher.find()) {
				String name = matcher.group(1);
				String key = name;
				String attrName = "";
				Object val;
				//name特殊
				if(name.contains(".")) {//反射方式获取属性
					String[] arr = name.split("\\.");
					key = arr[0];
					attrName = arr[1];
					val = resourceMap.get(key);
					val = getFromAttr(val, attrName);
					int index = 2;
					while(arr.length > index) {
						val = getFromAttr(val, arr[index++]);
					}
				}else {
					val = resourceMap.get(key);
				}
				if(val != null) {
					Class<? extends Object> type = val.getClass();
					if(type.getName().endsWith("List")) {//多个值表示
						List list = (List) val;
						String content = "";
						for(Object obj : list) {
							content += obj.toString() + "<\\br>";
						}
						resource = resource.replace("${" + name + "}", content);
					}else if(type.getName().endsWith("Map")){
						Map map = (Map) val;
						String content = "";
						for(Object keyx : map.keySet()) {
							content += keyx + " - " + map.get(keyx)+ "<\br>";
						}
						resource = resource.replace("${" + name + "}", content);
					}else {
						resource = resource.replace("${" + name + "}", val.toString());
					}
				}else {
					//异常处理：还是替换
					resource = resource.replace("${" + name + "}", "暂无");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}
	
	private Object getFromAttr(Object val, String attrName) {
		try {
			
			Field f = val.getClass().getDeclaredField(attrName);
			f.setAccessible(true);
			return f.get(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\$\\{(\\S+?)\\}");
		String rs = "<td>${jmap}</td><td>${jstack}</td><td>${jstat}</td>";
		Matcher matcher = pattern.matcher(rs);
		String sss = rs;
//		while(matcher.find()) {
//			System.out.println(matcher.group(1));
//			int pos = matcher.start();
//			int pos2 = matcher.end();
//			System.out.println(pos + "," + pos2);
//			sss = matcher.replaceFirst("bobo");
//			matcher = pattern.matcher(sss);
//			System.out.println(sss);
//		}
		//直接分割为3半;
		String xx = rs;
		while(matcher.find()) {
			System.out.println(matcher.group(1));
			int pos = matcher.start();
			int pos2 = matcher.end();
			xx = xx.substring(0, pos) + "new content" + xx.substring(pos2);
			rs = xx;
			matcher = pattern.matcher(rs);
			System.out.println(rs);
			System.out.println(pos + "," + pos2);
			
		}
	}
}
