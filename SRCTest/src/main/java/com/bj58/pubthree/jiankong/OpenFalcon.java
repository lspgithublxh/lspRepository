package com.bj58.pubthree.jiankong;

/**
 * 1.监控指标：cpu mem disk net interface 使用率和空闲和速度延时
 * 			jvm监控：各代各对象
 * 			业务进程监控-资源消耗监控
 * @ClassName:OpenFalcon
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月30日
 * @Version V1.0
 * @Package com.bj58.pubthree.jiankong
 */
public class OpenFalcon {

	public static void main(String[] args) {
		test();
	}

	/**
	 * 由变量类型自动定方法返回类型
	 * 单变量可以是任意值
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月28日
	 * @Package com.bj58.pubthree.jiankong
	 * @return void
	 */
	private static void test() {
		Integer i = new OpenFalcon().method();
		System.out.println(i);
	}
	
	private <T> T method() {
		Integer i = 10;
		return (T) i;
	}
}
