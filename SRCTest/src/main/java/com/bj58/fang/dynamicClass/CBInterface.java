package com.bj58.fang.dynamicClass;

import java.lang.reflect.Method;

public interface CBInterface {

	/**
	 * 如果是接口，那么instance应该为空,
	 * 如果是集成，那么instance就是被继承类的this
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.dynamicClass
	 * @return Object
	 */
	public Object callback(Object instance, ProxySub proxy, Method dynamicMethod, Method superMethod, Object... args);
}
