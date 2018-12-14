package com.bj58.fang.ArBpCc;

import com.bj58.fang.dynamicClass.DmicImplements;

/**
 * 维护服务的地址：socket
 * @ClassName:ProxyVisit
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月14日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class ProxyVisit {

	@SuppressWarnings("unchecked")
	public static <T> T proxyMethod(String url, Class<T> cl) {
		Object proxy = null;
		try {
			//获取服务
			String serviceName = url.substring(0, url.indexOf("/"));
			String interName = url.substring(url.indexOf("/") + 1);
			Class<?> c = DmicImplements.getInstance().getImplements(cl);
			proxy = c.newInstance();
			DmicImplements.getInstance().setDmicInstance(new GeneralCallBackFun(serviceName, interName), c);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) proxy;
	}
	
	
}
