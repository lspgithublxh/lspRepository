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

	static SeeContent2 instanc = new SeeContent2();
	
	public static SeeContent2 getInstance() {
		return instanc;
	}
	
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("package com.bj58.fang.dynamicClass; public class B{public static void main(String[] args) {System.out.println(\"hello\");}}");
		Class<?> a = javaCodeToClass("com.bj58.fang.dynamicClass.B", builder.toString());
		System.out.println(a.getName());
		Object b;
		try {
			b = a.newInstance();
			System.out.println(b.getClass().getName());
			a = javaCodeToClass("com.bj58.fang.ArBpCc.Dynamic2005567569", "package com.bj58.fang.ArBpCc;import java.lang.reflect.Method; public class Dynamic2005567569 implements IAService{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} @Override\r\n" + 
					" public int count(int args0) { Method m1 = null;\r\n" + 
					" Method m2 = null; try {m1 = this.getClass().getDeclaredMethod(\"count\",int.class);\r\n" + 
					" m2 = super.getClass().getDeclaredMethod(\"count\",int.class);\r\n" + 
					"} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} return (int)cb.callback(null, m1, m2 ,args0);}\r\n" + 
					"}");
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
		if(rs) {
			StrObject2 obj = fileM.getObject();
			DynamicClassLoader dc = new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
			try {
				Class<?> o = dc.loadClass(name, obj);
				return o;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
