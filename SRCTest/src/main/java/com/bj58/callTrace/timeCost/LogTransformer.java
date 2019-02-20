package com.bj58.callTrace.timeCost;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class LogTransformer implements ClassFileTransformer{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		try {
            ClassReader cr= new ClassReader(className);
            ClassWriter cw=new ClassWriter(ClassWriter.COMPUTE_MAXS);
            TimeCountAdpter timeCountAdpter=new TimeCountAdpter(cw);

            cr.accept(timeCountAdpter,ClassReader.EXPAND_FRAMES);

            return cw.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

}
