package com.bj58.fang.dynamicClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class SeeContent2 {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("package com.bj58.fang.dynamicClass; public class B{public static void main(String[] args) {System.out.println(\"hello\");}}");
		Class<?> a = javaCodeToClass("com.bj58.fang.dynamicClass.B", builder.toString());
		System.out.println(a.getName());
		Object b;
		try {
			b = a.newInstance();
			System.out.println(b.getClass().getName());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static Class<?> javaCodeToClass(String name, String content) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagno = new DiagnosticCollector<JavaFileObject>();
		ClassFileManager fileM = new ClassFileManager(compiler.getStandardFileManager(diagno, null, null));
		List<StrObject> flist = new ArrayList<>();
		flist.add(new StrObject(name, content));
		
		List<String> options = new ArrayList<>();
		options.add("-encoding");
		options.add("UTF-8");
		options.add("-classpath");
		String outDir = "";
		try {
			File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
			outDir = classPath.getAbsolutePath() + File.separator;
		}catch (Exception e) {
			e.printStackTrace();
		}
		options.add(outDir);
		CompilationTask task = compiler.getTask(null, fileM, diagno, options, null, flist);
		boolean rs = task.call();
		StrObject2 obj = fileM.getObject();
		DynamicClassLoader dc = new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
		try {
			Class<?> o = dc.loadClass(name, obj);
			return o;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
