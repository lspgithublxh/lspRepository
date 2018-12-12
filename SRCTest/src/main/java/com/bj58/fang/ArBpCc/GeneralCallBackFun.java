package com.bj58.fang.ArBpCc;

import java.lang.reflect.Method;

import com.bj58.fang.dynamicClass.CBInterface;
import com.bj58.fang.dynamicClass.ProxySub;

/**
 * 真正handle的方法
 * @ClassName:GeneralCallBackFun
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月12日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class GeneralCallBackFun implements CBInterface{

	@Override
	public Object callback(Object instance,ProxySub proxy, Method dynamicMethod, Method superMethod, Object... args) {
		//1.
		System.out.println("to service ");
		//2.
		
		return null;
	}

	
}
