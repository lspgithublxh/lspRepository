/**
 * 
 */
package com.yunhai.util;

import com.google.gson.Gson;

/**
 * @author Administrator
 * 2017年3月9日
 * JsonUtil
 */
public class JsonUtil {

	public static <T> String toJson(T object){
		return new Gson().toJson(object);
	}
}