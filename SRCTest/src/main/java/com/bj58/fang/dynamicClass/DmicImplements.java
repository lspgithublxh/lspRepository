package com.bj58.fang.dynamicClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DmicImplements {
	
	private static DmicImplements instanc = new DmicImplements();
	
	public static DmicImplements getInstance() {
		return instanc;
	}
	
	public static void main(String[] args) {
		Class aimplements = instanc.getImplements(AConnection.class);
		try {
			Object aimple = aimplements.newInstance();
			CBInterface cb = new CBInterface() {

				@Override
				public Object callback(Method dynamicMethod, Method superMethod, Object... args) {
					System.out.println("hehaha");
					System.out.println(dynamicMethod.getName());
					for(Object s : args) {
						System.out.println(s);
					}
					return 1;
				}
				
			};
			instanc.setDmicInstance(cb, aimple);
			System.out.println(aimple.getClass().getName());
			AConnection c = (AConnection)aimple;
			c.method1();
			c.method2(1, "2");
			c.method3(2, 2);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

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
				ms = String.format("@Override\r\n %s { %s return (%s)cb.callback(m1, m2 %s);}\r\n", ms, methodCall, ret.getName(), dparams);
			}else {
				ms = String.format("@Override\r\n %s { %s cb.callback(m1, m2 %s);}\r\n", ms, methodCall, dparams);
			}
//			System.out.println(ms);
			bul2.append(ms);
		}
		builder.append(String.format("package %s;import java.lang.reflect.Method; public class %s implements %s{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} %s}", pack, 
				subName, interName, bul2.toString()));
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
