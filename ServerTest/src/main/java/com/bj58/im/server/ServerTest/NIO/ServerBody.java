package com.bj58.im.server.ServerTest.NIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio产品和原理
 * 应对高负载高并发
 * 本质：关键1：提供了Socket ServerSocket的通道实现：SocketChannel, ServerSocketChannel以便可以双向读写异步非阻塞通信-而不是阻塞
 * 		关键2：事件监听器Selector 一直轮询中，来找到它里面准备好的channel以及对应的网络事件（事件-channel集合的这种map）   ..单线程轮询
 * 		关键3：网络数据缓存收发：Buffer  从这里读写数据
 * 
 * 对于文件读写通道：FileChannel  网络读写通道：SelectableChannel
 * @ClassName:ServerBody
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月4日
 * @Version V1.0
 * @Package com.bj58.im.server.ServerTest.NIO
 */
public class ServerBody {

	public static void main(String[] args) throws IOException {
		new ServerBody().startServer();
	}
	
	public void startServer() throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress("localhost", 12345), 1024);//backlog为1024
		channel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server started!!");
		//开始监听事件
		while(true) {
			System.out.println("start select");
			int status = selector.select();//实际上还是会阻塞的select,可以设置时间1s如---频繁while耗费资源
			System.out.println("ready keys:" + status);
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> ikeys =  keys.iterator();
			while(ikeys.hasNext()) {//key.cannel ,key.remove key.channel.close
				SelectionKey key = ikeys.next();
				ikeys.remove();
				//TODO 下面应该有异常捕获，来让key即时释放，并且channel关闭
				if(key.isValid()) {//有效，没有channel关闭等
					if(key.isAcceptable()) {
						ServerSocketChannel ssc = (ServerSocketChannel) key.channel();//肯定是这种channel
						SocketChannel sc = ssc.accept();//对accept事件的回应
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);// | SelectionKey.OP_WRITE,写应该是想写就写
					}
//					System.out.println("readable panduan");
					if(key.isReadable()) {
						SocketChannel sc = (SocketChannel) key.channel();//肯定是这种channel
//						sc.register(selector, SelectionKey.OP_READ);//再次注册
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int len = -1;
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						while((len = sc.read(buffer)) > 0) {
							buffer.flip();//
							out.write(buffer.array(), 0, len);
							buffer.clear();
//							buffer.compact();
						}
						System.out.println("sever read data:" + out.toString("UTF-8"));
//						key.cancel();
//						sc.close();
//						SocketChannel sc = (SocketChannel) key.channel();//肯定是这种channel
//						ByteBuffer buffer = ByteBuffer.allocate(1024);
						buffer.put("hello client".getBytes("UTF-8"));
						buffer.flip();//将起点指针放到0位置，limit指针放到最后一个数据放的位置,来方便读
						//从buffer中获取数据，都需要flip一下
//						try {
//							Thread.sleep(3000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
						len = sc.write(buffer);
//						sc.socket().getOutputStream().flush();
						System.out.println("sever write data:" + len);
					}
//					System.out.println("writeable panduan");
//					if(key.isWritable()) {
						
//					}
//					key.cancel();
//					System.out.println("cannel");
				}
			}
		}
	}
	
	
}
