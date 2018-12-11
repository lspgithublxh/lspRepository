package com.bj58.fang.dynamicClass;

import java.lang.reflect.Method;

public interface CBInterface {

	public Object callback(Method dynamicMethod, Method superMethod, Object... args);
}
