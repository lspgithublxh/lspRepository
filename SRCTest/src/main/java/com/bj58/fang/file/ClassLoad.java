package com.bj58.fang.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 从jar中读取单个文件：getResource()就可以。
 * @ClassName:ClassLoad
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月8日
 * @Version V1.0
 * @Package com.bj58.fang.file
 */
public class ClassLoad {

	public static void main(String[] args) {//"D:\\software\\direct.jar"
		new ClassLoad().test(args);
	}

	private void test(String[] args) {
//		try {
//			Thread.currentThread().getContextClassLoader().loadClass("");
//			Class.forName("", true, null);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
		this.getClass().getResource("");//
		try {
			
			JarFile file = new JarFile("D:\\project\\workspace\\scf\\ajk\\service\\target\\jar\\com.bj58.fang.service.hughouselist-0.0.1-SNAPSHOT.jar");
			List<String> li = new ArrayList<>();
			for(Enumeration<JarEntry> jar = file.entries();jar.hasMoreElements();) {
				JarEntry entry = jar.nextElement();
				System.out.println(entry.getName());
				System.out.println(entry.isDirectory());
				System.out.println(entry.getComment());
				System.out.println("----------------");
				String name = entry.getName();
				if(name.startsWith("com/bj58/fang/service/hughouselist/datasource/") && !entry.isDirectory()) {
					name = name.substring(name.indexOf("/datasource/") + "/datasource/".length(), name.indexOf("."));
					if(!name.contains("/")) {
						System.out.println("com.bj58.fang.service.hughouselist.datasource." + name);
						li.add("com.bj58.fang.service.hughouselist.datasource." + name);
//						try {
//							Class cl = Class.forName("com.bj58.fang.service.hughouselist.datasource." + name);
//							System.out.println(cl.getName());
//						} catch (ClassNotFoundException e) {
//							e.printStackTrace();
//						}
						
					}
				}
			}
			System.out.println(li);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
