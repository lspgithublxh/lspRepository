package com.bj58.fang.dynamicClass;

import java.net.URL;
import java.net.URLClassLoader;

public class DynamicClassLoader extends URLClassLoader{

	public DynamicClassLoader(ClassLoader loader) {
		super(new URL[0], loader);
	}

	public Class<?> loadClass(String name, StrObject2 obj) throws ClassNotFoundException {
		byte[] data = obj.getBytes();
		return super.defineClass(name, data, 0, data.length);
	}
	
	protected Class<?> findClassByClassName(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}
}
