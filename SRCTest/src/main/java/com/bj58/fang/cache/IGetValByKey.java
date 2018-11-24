package com.bj58.fang.cache;

/**
 * 回调接口
 * @ClassName:IGetValByKey
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public interface IGetValByKey<T> {

	public T getValByKey(String key);
}
