package com.bj58.fang.dynamicClass;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class StrObject extends SimpleJavaFileObject {

	private String content = null;
	
	protected StrObject(String name, String content) {
		super(URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
		this.content = content;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//		return super.getCharContent(ignoreEncodingErrors);
		return content;
	}
	
}
