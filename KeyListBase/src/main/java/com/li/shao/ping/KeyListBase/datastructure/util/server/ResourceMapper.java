package com.li.shao.ping.KeyListBase.datastructure.util.server;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceMapper {
	
	public static ResourceMapper instance = new ResourceMapper();

	private Pattern pattern = Pattern.compile("\\$\\{(\\S+?)\\}");
	
	public String matchAndReplace(String resource, Map<String, Object> resourceMap) {
		//第一种情况：
		try {
			Matcher matcher = pattern.matcher(resource);
			while(matcher.find()) {
				String name = matcher.group(1);
				Object val = resourceMap.get(name);
				if(val != null) {
					resource = resource.replace("${" + name + "}", val.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resource;
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\$\\{(\\S+?)\\}");
		Matcher matcher = pattern.matcher("<td>${jmap}</td><td>${jstack}</td><td>${jstat}</td>");
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}
		
	}
}
