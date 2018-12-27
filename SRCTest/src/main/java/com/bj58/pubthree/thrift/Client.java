package com.bj58.pubthree.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {

	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
//			test();
			test2();
		}
	}

	private static void test2() {
		TTransport soc = new TFastFramedTransport(new TSocket("localhost", 10991));
		TProtocol proto = new TCompactProtocol(soc);
		
		HelloService.Client client = new HelloService.Client(proto);
		try {
			soc.open();
			String rs = client.sayHello("hello, eclipse");
			System.out.println("result : " + rs);
			soc.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}

	private static void test() {
		TTransport soc = new TSocket("localhost", 10991);
		TProtocol proto = new TBinaryProtocol(soc);
		
		HelloService.Client client = new HelloService.Client(proto);
		try {
			soc.open();
			String rs = client.sayHello("hello, eclipse");
			System.out.println("result : " + rs);
			soc.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		
		
	}
}
