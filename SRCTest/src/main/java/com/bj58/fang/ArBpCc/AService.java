package com.bj58.fang.ArBpCc;

import java.util.List;

public class AService implements IAService{

	@Override
	public int count(int a) {
		System.out.println("service call the result!:" + a);
		return a;
	}

	@Override
	public int visit(List<String> asli) {
		System.out.println("service call the result!:" + asli.toString());
		return 0;
	}

}
