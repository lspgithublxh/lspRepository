package com.forwf.center.base.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 其实没什么难度
 * @ClassName:FirstMapper
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月14日
 * @Version V1.0
 * @Package com.forwf.center.base.mapper
 */
public interface FirstMapper {

	@Select("select title from tuijian where title like '%${key}%'")
	String selectOne(String key);
}
