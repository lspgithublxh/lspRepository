package com.bj58.fang.dynamicClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StrObject2 extends SimpleJavaFileObject {

	protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	
	protected StrObject2(String name, Kind kind) {
		super(URI.create("string:///" + name.replace(".", "/") + kind.extension), kind);
	}

	
	@Override
	public OutputStream openOutputStream() throws IOException {
		return bos;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		bos.close();
	}
	
	
	public byte[] getBytes() {
		return bos.toByteArray();
	}
}
