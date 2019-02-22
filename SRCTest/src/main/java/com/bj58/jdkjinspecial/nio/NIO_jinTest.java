package com.bj58.jdkjinspecial.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * selector: 就是一个端口通道信号轮询器(两次轮询的间隔时间可以定)....在所有端口(通道)听各类通道信号(accept, read-需要手动注册告知, write-需要手动注册告知)
 *  key: 就是这样的一个端口通道信号.....(socketchannel, type)
 * socketchannel: 就是通信通道
 * 
 * serversocketchannel: 服务端的通信通道
 * 
 * 所以整个流程就是：
 *  轮询所有通道直到取得信号
 *  看是哪类信号
 *  信号的通道是什么、进一步要不要注册新的信号
 *  
 *  
 *  ----------nio创新之处就是这里：改开新线程为轮询，改单字节读为块读
 * @ClassName:NIO_jinTest
 * @Description:
 * @Author lishaoping
 * @Date 2019年2月22日
 * @Version V1.0
 * @Package com.bj58.jdkjinspecial.nio
 */
public class NIO_jinTest {

	public static void main(String[] args) throws IOException {
		Selector server = Selector.open();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					 //打开服务端socketchannel
					ServerSocketChannel serverChannel = ServerSocketChannel.open();
					serverChannel.bind(new InetSocketAddress("localhost", 11234));
					serverChannel.configureBlocking(false);
					serverChannel.register(server, SelectionKey.OP_ACCEPT);
					
					while(true) {//不断轮询
						if(server.select(1) > 0) {//端口查询1ms, 值应该是key的数量
							System.out.println("server select ok:");
							Set<SelectionKey> keys = server.selectedKeys();
							Iterator<SelectionKey> keyIt = keys.iterator();
							while(keyIt.hasNext()) {
								SelectionKey sk = keyIt.next();
								System.out.println("key name:" + sk.interestOps());
								if(sk.isAcceptable()) {//连接的信号
									SocketChannel sc = serverChannel.accept();
									if(sc != null) {
										sc.configureBlocking(false);
										sc.register(server, SelectionKey.OP_READ);// | SelectionKey.OP_WRITE
									}else {
										System.out.println(" sc is null");
									}
								}else if(sk.isWritable()) {
									SocketChannel sc = (SocketChannel) sk.channel();
									Scanner scanner = new Scanner(System.in);
									String line = scanner.nextLine();
									
									ByteBuffer bc = ByteBuffer.allocate(1024);
									bc.put(line.getBytes());
									bc.flip();//从bytebuffer写出数据之前
									sc.write(bc);
									sc.register(server, SelectionKey.OP_READ);// | SelectionKey.OP_WRITE
								}else if(sk.isReadable()) {
									SocketChannel sc = (SocketChannel) sk.channel();
									ByteBuffer bc = ByteBuffer.allocate(1024);
									sc.read(bc);
									byte[] b = new byte[1024];
									bc.flip();
									bc.get(b, 0, bc.remaining());
									System.out.println("server received:" + new String(b));
									sc.register(server, SelectionKey.OP_WRITE);//SelectionKey.OP_READ | 
								}
								keyIt.remove();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//
	}
	
}
