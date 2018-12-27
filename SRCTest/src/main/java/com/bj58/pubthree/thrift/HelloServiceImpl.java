package com.bj58.pubthree.thrift;

import org.apache.thrift.TException;

import com.bj58.pubthree.thrift.HelloService.Iface;

public class HelloServiceImpl implements Iface {

	@Override
	public String sayHello(String username) throws TException {
		return "hi, client:" + username;
	}

}
