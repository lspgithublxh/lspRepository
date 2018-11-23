package com.bj58.fang.cache;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 支持cpu级别的CAS指令(乐观锁)的数据结构AtomicLong
 * @ClassName:CASHandle_cpu_commend
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月22日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class CASHandle_cpu_commend {

	public static void main(String[] args) {
		test();
	}

	/**
	 * 自增快1倍
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月22日
	 * @Package com.bj58.fang.cache
	 * @return void
	 */
	private static void test() {
		AtomicLong a = new AtomicLong(111);
	}
}
