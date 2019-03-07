package com.bj58.jdkjinspecial.instrument;

import java.lang.instrument.Instrumentation;

/**
 * 提供额外的机制，在一个独立的jvm运行后，执行main方法前，先执行代理类的premain方法: 启动代理程序jar,并且获取jvm状态和改变类定义--通过Instrumentation实现改变
 * 
 * ----代理程序以-javaagent:jar包位置   这样方式启动
 * 	 >代理jar的特征：mainifest文件里有Premain-Class属性,值为代理类的全路径
 * 	 >代理类有premain(String, Instrumentation)方法
 * @ClassName:JvmAgentHelper
 * @Description:
 * @Author lishaoping
 * @Date 2019年2月25日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial.instrument
 */
public class JvmAgentHelper {

	public static void premain(String args, Instrumentation ins) {
		System.out.println("hello, premain method execute");
		ins.addTransformer(new ATransformer());
	}
}
