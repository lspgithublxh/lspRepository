package com.bj58.fang.dynamicClass;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.JavaFileObject.Kind;

/**
 * http://www.cnblogs.com/eoss/p/6136943.html
 * https://blog.csdn.net/qq_35248272/article/details/73527151
 * @ClassName:ClassFileManager
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月10日
 * @Version V1.0
 * @Package com.bj58.fang.dynamicClass
 */
public class ClassFileManager extends ForwardingJavaFileManager {

	private StrObject2 object;
	
	protected ClassFileManager(StandardJavaFileManager arg0) {
		super(arg0);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling)
			throws IOException {
//		return super.getJavaFileForOutput(location, className, kind, sibling);
		if(object == null) {
			object = new StrObject2(className, kind);
		}
		return object;
	}

	public StrObject2 getObject() {
		return object;
	}
	
}
