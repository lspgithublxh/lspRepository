package com.bj58.fang.dynamicClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class DmicImplements {
	
	private static DmicImplements instanc = new DmicImplements();
	private static List<String> typeList = null;
	static {
		typeList = Arrays.asList(new String[] {"long", "byte", "float", "double", "int"});
	}
	
	public static DmicImplements getInstance() {
		return instanc;
	}
	
	public static void main(String[] args) {
//		Class aimplements = instanc.getImplements(AConnection.class);
		Class aimplements = instanc.getSub(Aimple.class);
		try {
			Object aimple = aimplements.newInstance();
			CBInterface cb = new CBInterface() {

				@Override
				public Object callback(Object instance, ProxySub proxy, Method dynamicMethod, Method superMethod, Object... args) {
					System.out.println("hehaha");
					System.out.println(dynamicMethod.getName());
					for(Object s : args) {
						System.out.println(s);
					}
					//1.主要工作：将服务名、方法、参数序列化，然后取socke接口将方法、参数等发送到该接口，使得服务端处理并返回结果，然后反序列化
					//2.使用的数据发送和接收都是按字节进行的
//					if(instance != null) {
//						System.out.println(instance.getClass().getName());
//						try {
//							superMethod.invoke(instance, args);
//						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//							e.printStackTrace();
//						}
//					}
					proxy.proxy(dynamicMethod, instance);
					return 1;
				}
				
			};
			instanc.setDmicInstance(cb, aimple);
			System.out.println(aimple.getClass().getName());
			Aimple sub = (Aimple) aimple;
			sub.method2(null, "222");
//			sub.method1();
//			AConnection c = (AConnection)aimple;
//			c.method1();
//			c.method2(1, "2");
//			c.method3(2, 2);
//			Class sub = instanc.getSubClass(Aimple.class);
//			Object subx = sub.newInstance();
//			instanc.setDmicInstance(cb, subx);
//			Aimple e = (Aimple) subx;
//			e.method1();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public Class<?> getInvokeByCompare(Class<?> inter){
		return null;
	}

	/**
	 * 双重继承模式
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.dynamicClass
	 * @return Class<?>
	 */
	public Class<?> getSubClass(Class<?> inter){
		StringBuilder builder = new StringBuilder();
		String name = inter.getName();
		String pack = name.substring(0, name.lastIndexOf("."));
		String interName = name.substring(name.lastIndexOf(".") + 1);
		String subName = "SClas" + inter.hashCode();
		Method[] mes = inter.getDeclaredMethods();
		StringBuilder bul2 = new StringBuilder();
		for(Method m : mes) {
			String iname = m.getName();
			Class<?>[] iparams = m.getParameterTypes();
			String methca = "";
			for(Class<?> can : iparams) {
				methca += can.getName() + ".class, ";
			}
			if(methca.length() > 0) {
				methca = "," + methca.substring(0, methca.length() - 2);
			}
			Class<?> ret = m.getReturnType();
			String ms = m.toGenericString();
			boolean isAbstract = false;
			if(ms.contains(" abstract ")) {
				isAbstract = true;
			}
			ms = ms.replace(" abstract", "").replace(inter.getName() + ".", "");
			String juti = ms.substring(ms.indexOf("(") + 1, ms.indexOf(")"));
			String jutis = "";
			String dparams = "";
			if(!"".equals(juti)) {
				String[] paramx = juti.split(",");
				String[] pax = new String[paramx.length];
				int i = 0;
				dparams = "";
				for(String px : paramx) {
					String args = "args" + i;
					jutis += px + " " + args + ",";
					i++;
					dparams += args + ",";
				}
				jutis = jutis.substring(0, jutis.length() - 1);
				ms = ms.substring(0, ms.indexOf("(")) + "(" + jutis + ")";
				dparams = dparams.substring(0, dparams.length() - 1);
			}
			String methodCall = "";
			String retName = ret.getName();
			boolean hasReturn = "void".equals(retName) ? false : true;
			if(isAbstract) {
				if(hasReturn) {
					if(typeList.contains(retName)) {
						ms = String.format("@Override\r\n %s { return 0;}\r\n", ms);
					}else if("boolean".equals(retName)) {
						ms = String.format("@Override\r\n %s { return true;}\r\n", ms);
					}else {
						ms = String.format("@Override\r\n %s { return null;}\r\n", ms);
					}
				}else {
					ms = String.format("@Override\r\n %s {}\r\n", ms);
				}
			}else {
				if(hasReturn) {
					ms = String.format("@Override\r\n %s { %s return (%s)super.%s(%s);}\r\n", ms, methodCall, ret.getName(), iname, dparams);
				}else {
					ms = String.format("@Override\r\n %s { %s super.%s(%s);}\r\n", ms, methodCall, iname, dparams);
				}
			}
			
//			System.out.println(ms);
			bul2.append(ms);
		}
		//增加额外方法
		builder.append(String.format("package %s; public class %s extends %s{ protected %s get8x72xs987x(){ return this;} %s}", pack, 
				subName, interName, subName, bul2.toString()));
		System.out.println(builder.toString());
		Class<?> c = SeeContent2.javaCodeToClass(pack + "." + subName, builder.toString());
		//.开始子类继承
		Class<?> rel = getImplements(c);
		
		return rel;
	}
	
	public Class<?> getSub(Class<?> inter){
		StringBuilder builder = new StringBuilder();
		StringBuilder proxy = new StringBuilder();
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(inter.getName());
		String name = inter.getName();
		String pack = name.substring(0, name.lastIndexOf("."));
		String interName = name.substring(name.lastIndexOf(".") + 1);
		String subName = "Dynamic" + inter.hashCode();
		String methods = "";
		Method[] mes = inter.getDeclaredMethods();
		StringBuilder bul2 = new StringBuilder();
		boolean isInterface = inter.isInterface();
		boolean first = true;
		for(Method m : mes) {
			String iname = m.getName();
			String fullName = m.toGenericString();
			fullName = fullName.replace("."+inter.getSimpleName()+".", "."+ subName + ".");
			Class<?>[] iparams = m.getParameterTypes();
			String methca = "";
			String argsStr = "";
			int index = 0;
			for(Class<?> can : iparams) {
				methca += can.getName() + ".class, ";
				argsStr += String.format("(%s)args[%s],", can.getName(), index++);
			}
			if(methca.length() > 0) {
				methca = "," + methca.substring(0, methca.length() - 2);
				argsStr = argsStr.substring(0, argsStr.length() - 1);
			}
			Class<?> ret = m.getReturnType();
			
			Annotation[] iann = m.getDeclaredAnnotations();
			String ms = m.toGenericString();
			ms = ms.replace(" abstract", "").replace(inter.getName() + ".", "");
			String juti = ms.substring(ms.indexOf("(") + 1, ms.indexOf(")"));
			String jutis = "";
			String dparams = "";
			if(!"".equals(juti)) {
				String[] paramx = juti.split(",");
				String[] pax = new String[paramx.length];
				int i = 0;
				dparams = ",";
				for(String px : paramx) {
					String args = "args" + i;
					jutis += px + " " + args + ",";
					i++;
					dparams += args + ",";
				}
				jutis = jutis.substring(0, jutis.length() - 1);
				ms = ms.substring(0, ms.indexOf("(")) + "(" + jutis + ")";
				dparams = dparams.substring(0, dparams.length() - 1);
			}
			String methodCall = String.format(" ProxySub proxy = ProxySub.getInstance();\r\n Method m1 = null;\r\n Method m2 = null; try {m1 = this.getClass().getDeclaredMethod(\"%s\"%s);\r\n" + String.format(" m2 = super.getClass().getDeclaredMethod(\"%s\"%s);\r\n} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();}", iname, methca), iname, methca);
			boolean hasReturn = "void".equals(ret.getName()) ? false : true;
			if(hasReturn) {
				ms = String.format("@Override\r\n %s { %s return (%s)cb.callback(this,proxy, m1, m2 %s);}\r\n", ms, methodCall, ret.getName(), dparams);
				proxy.append(String.format("%s if(\"%s\".equals(method.toGenericString())){System.out.println(\"proxy method get in\");return super.%s(%s);}", first ? "" : "else", fullName, iname, argsStr));//个数和类型，强转
			}else {
				ms = String.format("@Override\r\n %s { %s cb.callback(this,proxy, m1, m2 %s);}\r\n", ms, methodCall, dparams);
				proxy.append(String.format("%s if(\"%s\".equals(method.toGenericString())){System.out.println(\"proxy method get in\");super.%s(%s);}", first ? "" : "else", fullName, iname, argsStr));//个数和类型，强转
			}
//			System.out.println(ms);
			bul2.append(ms);
			first = false;
		}
		//补充方法---补充一些东西
		bul2.append(String.format(" public Object proxy(Method method, BIaoji a, Object... args){%s;System.out.println(\"proxy method get out\"); return null;}\r\n", proxy.toString()));
		String impeExt = isInterface ? "implements" : "extends";
		builder.append(String.format("package %s;import java.lang.reflect.Method; public class %s %s %s{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} %s}", pack, 
				subName, impeExt, interName, bul2.toString()));
		System.out.println(builder.toString());
		Class<?> c = SeeContent2.javaCodeToClass(pack + "." + subName, builder.toString());
		return c;
		
	}
	
	/**
	 * 一个关键问题是：调用父类方法，而不是调用子类继承的方法，或者覆盖的方法----使得执行父类的方法时，this的值是父类的值
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.dynamicClass
	 * @return Class<?>
	 */
	public Class<?> getImplements(Class<?> inter){
		StringBuilder builder = new StringBuilder();
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(inter.getName());
		String name = inter.getName();
		String pack = name.substring(0, name.lastIndexOf("."));
		String interName = name.substring(name.lastIndexOf(".") + 1);
		String subName = "Dynamic" + inter.hashCode();
		String methods = "";
		Method[] mes = inter.getDeclaredMethods();
		StringBuilder bul2 = new StringBuilder();
		boolean isInterface = inter.isInterface();
		String thisGet = isInterface ? "null" : "super.get8x72xs987x()";
		for(Method m : mes) {
			String iname = m.getName();
			if("get8x72xs987x".equals(iname)) {
				continue;
			}
			Class<?>[] iparams = m.getParameterTypes();
			String methca = "";
			for(Class<?> can : iparams) {
				methca += can.getName() + ".class, ";
			}
			if(methca.length() > 0) {
				methca = "," + methca.substring(0, methca.length() - 2);
			}
			Class<?> ret = m.getReturnType();
			
			Annotation[] iann = m.getDeclaredAnnotations();
//			AnnotatedType[] expe = m.getAnnotatedExceptionTypes();
//			int i = m.getModifiers();
//			Parameter[] params = m.getParameters();
//			System.out.println(m.toGenericString());
//			System.out.println(m.toString());
			String ms = m.toGenericString();
			ms = ms.replace(" abstract", "").replace(inter.getName() + ".", "");
			String juti = ms.substring(ms.indexOf("(") + 1, ms.indexOf(")"));
			String jutis = "";
			String dparams = "";
			if(!"".equals(juti)) {
				String[] paramx = juti.split(",");
				String[] pax = new String[paramx.length];
				int i = 0;
				dparams = ",";
				for(String px : paramx) {
					String args = "args" + i;
					jutis += px + " " + args + ",";
					i++;
					dparams += args + ",";
				}
				jutis = jutis.substring(0, jutis.length() - 1);
				ms = ms.substring(0, ms.indexOf("(")) + "(" + jutis + ")";
				dparams = dparams.substring(0, dparams.length() - 1);
			}
			String methodCall = String.format("Method m1 = null;\r\n Method m2 = null; try {m1 = this.getClass().getDeclaredMethod(\"%s\"%s);\r\n" + String.format(" m2 = super.getClass().getDeclaredMethod(\"%s\"%s);\r\n} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();}", iname, methca), iname, methca);
			boolean hasReturn = "void".equals(ret.getName()) ? false : true;
			if(hasReturn) {
				ms = String.format("@Override\r\n %s { %s return (%s)cb.callback(%s,null, m1, m2 %s);}\r\n", ms, methodCall, ret.getName(),thisGet, dparams);
			}else {
				ms = String.format("@Override\r\n %s { %s cb.callback(%s,null, m1, m2 %s);}\r\n", ms, methodCall, thisGet, dparams);
			}
//			System.out.println(ms);
			bul2.append(ms);
		}
		String impeExt = isInterface ? "implements" : "extends";
		builder.append(String.format("package %s;import java.lang.reflect.Method; public class %s %s %s{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} %s}", pack, 
				subName, impeExt, interName, bul2.toString()));
		System.out.println(builder.toString());
		Class<?> c = SeeContent2.javaCodeToClass(pack + "." + subName, builder.toString());
		return c;
		
	}
	
	public void setDmicInstance(CBInterface callback, Object dinstance) {
		try {
			Method m = dinstance.getClass().getDeclaredMethod("set8y38mc", CBInterface.class);
			m.invoke(dinstance, callback);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
