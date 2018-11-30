package com.bj58.fang.cache.HotDataMemCache;

/**
 * 回调接口
 * @ClassName:IGetValByKey
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public interface IGetValByKey<K, T> {

	public T getValByKey(K key);
}
