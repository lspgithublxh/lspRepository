package com.bj58.parallHandle.sychronizede;

public class B {

	public void test() {
		synchronized (this) {
			System.out.println(this);
		}
	}
}
