package com.bj58.fang.ArBpCc;

import java.util.ArrayList;
import java.util.List;

/**
 * 读的模式可以改为读块，有多少块；；第一块为验证配置块---要读要转要验证，后面的是数据块
 * 读线程要在写线程前面
 * 
 * netty:参考：https://www.cnblogs.com/little-fly/p/8683197.html
 * 		参考2：https://www.cnblogs.com/fairjm/p/netty_common_pattern.html
 * @ClassName:Test
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月25日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class Test {

	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ARC acr = new ARC();
				acr.start();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				AS1 as1 = new AS1();
				as1.start();
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				//1.测试获取服务：配置...可以不用
				AC2 ac2 = new AC2();
				SDEntity ent = ac2.getService("aservice");
				//2.测试远程调用
				IAService serivce = ProxyVisit.proxyMethod(ent.getUrl(), IAService.class);
				int count = serivce.count(2);
				System.out.println("完成远程调用" + count);
				count = serivce.count(3);
				System.out.println("完成远程调用" + count);
				List<String> li = new ArrayList<String>();
				li.add("something");
				count = serivce.visit(li);
				System.out.println("完成远程调用" + count);
				count = serivce.vi(new SDEntity("test", "hahah/hahaha", new String[] {"localhost:8080"}));
				System.out.println("完成远程调用" + count);
			}
		}).start();

	}
}
