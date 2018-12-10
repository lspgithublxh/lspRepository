package com.bj58.fang.dynamicClass;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class SeeContent {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("package com.bj58.fang.dynamicClass; public class A{public static void main(String[] args) {System.out.println(\"hello\");}}");
		Class<?> a = test("com.bj58.fang.dynamicClass.A", builder.toString());
		System.out.println(a.getName());
		try {
			a.getMethod("main", String[].class).invoke(a, new Object[] {new String[] {"1,2"}});
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Class<?> test(String name, String content) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);
		StrObject so = new StrObject(name, content);
		String flag = "-d";
		String outDir = "";
		try {
			File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
			outDir = classPath.getAbsolutePath() + File.separator;
		}catch (Exception e) {
			e.printStackTrace();
		}
		Iterable options = Arrays.asList(flag, outDir);	
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(so);
		CompilationTask task = compiler.getTask(null, fm, null, options, null, fileObjects);
		boolean result = task.call();
		if(result) {
			try {
				Class<?> object = Class.forName(name);
				System.out.println("comiple success");
				return object;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
