package com.bj58.jdkjinspecial.instrument;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;


public class ATransformer implements ClassFileTransformer{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("transformer execute");
		ClassReader cr = new ClassReader(classfileBuffer);
		return classfileBuffer;
	}

}
