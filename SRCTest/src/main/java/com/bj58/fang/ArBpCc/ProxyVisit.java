package com.bj58.fang.ArBpCc;

import com.bj58.fang.dynamicClass.DmicImplements;

public class ProxyVisit {

	public <T> T proxyMethod(String url, Class<T> cl) {
		Object proxy = null;
		try {
			Class c = DmicImplements.getInstance().getImplements(cl);
			proxy = c.newInstance();
			DmicImplements.getInstance().setDmicInstance(new GeneralCallBackFun(), c);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) proxy;
	}
}
