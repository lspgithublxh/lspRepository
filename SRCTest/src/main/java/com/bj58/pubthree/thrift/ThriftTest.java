package com.bj58.pubthree.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

/**
 * 参考文档：https://www.cnblogs.com/duanxz/p/5516558.html
 * @ClassName:ThriftTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月27日
 * @Version V1.0
 * @Package com.bj58.pubthree
 */
public class ThriftTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		TProcessor pros = new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl());
		
		try {
//			one(pros);
			//2.
//			two(pros);
			//3.
			TNonblockingServerSocket sok = new TNonblockingServerSocket(10991);
			TNonblockingServer.Args args = new TNonblockingServer.Args(sok);
			args.processor(pros);
			args.protocolFactory(new TCompactProtocol.Factory());
			args.transportFactory(new TFramedTransport.Factory());
			TServer server =  new TNonblockingServer(args);
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
		
	}

	private static void two(TProcessor pros) throws TTransportException {
		TServerSocket sok = new TServerSocket(10991);
		TThreadPoolServer.Args args = new TThreadPoolServer.Args(sok);
		args.processor(pros);
		args.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TThreadPoolServer(args);
		server.serve();
	}

	private static void one(TProcessor pros) throws TTransportException {
		TServerSocket sok = new TServerSocket(10991);
		TServer.Args args = new TServer.Args(sok);
		
		args.processor(pros);
		args.protocolFactory(new TBinaryProtocol.Factory());
		
		TServer server = new TSimpleServer(args);
		server.serve();
	}
}
