package com.bj58.fang.ArBpCc;

public class AService implements IAService{

	@Override
	public int count(int a) {
		System.out.println("service call the result!:" + a);
		return a;
	}

}
