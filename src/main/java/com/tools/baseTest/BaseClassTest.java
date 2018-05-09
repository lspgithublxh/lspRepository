package com.tools.baseTest;

import java.util.HashMap;
import java.util.Map;

public class BaseClassTest {
	
	public static void main(String[] args) {
		System.out.println("hello".hashCode());
		System.out.println(new Object().hashCode());
		Map m = new HashMap<>();
		m.put("", "");
		
	}
}
