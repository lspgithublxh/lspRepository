package com.li.shao.ping.KeyListBase.datastructure.util.uid;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * 
 *
 * @author lishaoping
 * @date 2020年1月10日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.uid
 */
public class UIDUtil {

	private static AtomicInteger no = new AtomicInteger(0);
	
	public static int increNum() {
		int incre = no.incrementAndGet();
		no.compareAndSet(100000000, 0);
		return incre;
	}
}
