package com.bj58.callTrace.timeCost;

import java.lang.instrument.Instrumentation;

/**
 * https://blog.csdn.net/ljz2016/article/details/84137908
 * @ClassName:UdAgent
 * @Description:
 * @Author lishaoping
 * @Date 2019年2月20日
 * @Version V1.0
 * @Package com.bj58.callTrace.timeCost
 */
public class UdAgent {

	public static void premain(String agentArgs, Instrumentation instrumentation){
        instrumentation.addTransformer(new LogTransformer());
    }
}
