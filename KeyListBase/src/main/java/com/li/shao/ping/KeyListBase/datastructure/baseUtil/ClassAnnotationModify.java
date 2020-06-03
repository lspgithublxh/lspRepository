package com.li.shao.ping.KeyListBase.datastructure.baseUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

//import org.springframework.cglib.proxy.Proxy;

import com.li.shao.ping.KeyListBase.datastructure.baseUtil.ana.ABnnatation;

/**
 * 直接可以修改类上的注解
 *
 * @author lishaoping
 * @date 2020年4月9日
 * @package  com.li.shao.ping.KeyListBase.datastructure.baseUtil
 */
@ABnnatation(name="xxx", value = "haode")
public class ClassAnnotationModify {

	public static void main(String[] args) {
		ClassAnnotationModify instance = new ClassAnnotationModify();
		Class<? extends ClassAnnotationModify> clsName = instance.getClass();
		ABnnatation anno = clsName.getAnnotation(ABnnatation.class);
		InvocationHandler hd = Proxy.getInvocationHandler(anno);
		try {
			Field df = hd.getClass().getDeclaredField("memberValues");
			df.setAccessible(true);
			Map map = (Map) df.get(hd);//获取代理注解对象的当前实际值
			//修改当前实际值
			map.put("name", "haoa");
			map.put("value", "val");
			//打印验证
			ABnnatation ax = clsName.getAnnotation(ABnnatation.class);
			System.out.println(ax.name());
			System.out.println(ax.value());
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
