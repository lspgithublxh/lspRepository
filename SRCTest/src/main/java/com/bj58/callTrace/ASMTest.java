package com.bj58.callTrace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import aj.org.objectweb.asm.Opcodes;

/**
 * 查看两个类的class文件的TraceClassVisitor 看到真正的指令过程
 * @ClassName:ASMTest
 * @Description:
 * @Author lishaoping
 * @Date 2019年2月20日
 * @Version V1.0
 * @Package com.bj58.callTrace
 */
public class ASMTest {

	public static void main(String[] args) {
		generateAClass();
//		updateClassFile();
//		testC();
//		test();
	}

	private static void test() {
		Runtime.getRuntime().traceMethodCalls(true);
	}

	private static void testC() {
		C c = new C();
		try {
			c.m();
			Class cc = c.getClass();
		    System.out.println(cc.getField("timer").get(c));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
       
	}

	private static void updateClassFile() {
		try {
			ClassReader cr = new ClassReader("com.bj58.callTrace.C");
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			
			ClassAdapter classAdapter = new AddTimeAdapter(cw);
			cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
			
			byte[] data = cw.toByteArray();
			File file = new File("D:\\project\\my_project\\SRCTest\\src\\main\\java\\com\\bj58\\callTrace\\C.class");
			try {
				FileOutputStream out = new FileOutputStream(file);
				out.write(data, 0, data.length);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateAClass() {
		//定义一个类
		ClassWriter cw = new ClassWriter(0);
		cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
				"com/asm3/Comparable", null, "java/lang/Object", new String[] {"com/asm3/Mesurable"});
		cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC , 
				"LESS", "I", null, new Integer(-1)).visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
				                 "EQUAL", "I", null, new Integer(0)).visitEnd();
		cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
				                 "GREATER", "I", null, new Integer(1)).visitEnd();
		cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT, "compareTo","(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		
		//将cw转换成字节数组写到文件里面去
		byte[] data = cw.toByteArray();
		File file = new File("D:\\project\\my_project\\SRCTest\\src\\main\\java\\com\\bj58\\callTrace\\Comparable.class");
		try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(data, 0, data.length);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
