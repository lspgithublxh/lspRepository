package com.bj58.fang.ArBpCc;

import java.lang.reflect.Method;
import java.util.List;

public class Dynamic2005567569 implements IAService {
	private com.bj58.fang.dynamicClass.CBInterface cb;

	public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb) {
		this.cb = cb;
	}

	@Override
	public int count(int args0) {
		Method m1 = null;
		Method m2 = null;
		try {
			m1 = this.getClass().getDeclaredMethod("count", int.class);
			m2 = super.getClass().getDeclaredMethod("count", int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return (int) cb.callback(null, null, m1, m2, args0);
	}

	@Override
	public int visit(List<String> asli) {
		// TODO Auto-generated method stub
		return 0;
	}
}