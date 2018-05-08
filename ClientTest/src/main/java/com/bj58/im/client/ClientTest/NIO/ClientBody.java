package com.bj58.im.client.ClientTest.NIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 读写必须在两个线程里
 * 第一个线程先连接服务端、循环监听接收消息----第二个线程（注册读----开始写）的循环---
 * @ClassName:ClientBody
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月7日
 * @Version V1.0
 * @Package com.bj58.im.client.ClientTest.NIO
 */
public class ClientBody extends Thread{

	public static void main(String[] args) throws IOException {
		ClientBody bo = new ClientBody();
		
		bo.start();
		//开始写数据
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("start write ,, client:");
		bo.write();
	}
	
	@Override
	public void run() {
		try {
			startClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Selector selector = null;
	SocketChannel socket = null;
	
	public ClientBody() {
		try {
			selector = Selector.open();
			socket = SocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startClient() throws IOException {
		socket.configureBlocking(false);
		socket.connect(new InetSocketAddress("localhost", 12345));
		socket.register(selector, SelectionKey.OP_CONNECT);
		//开始监听
		while(true) {
			System.out.println("client start select");
			int status = selector.select();//阻塞在这里,,nio不好之处，在于会错过事件？
			System.out.println("client end select");
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> ikeys = keys.iterator();
			while(ikeys.hasNext()) {
				SelectionKey key = ikeys.next();
				ikeys.remove();
				
				if(key.isValid()) {
					
					if(key.isConnectable()) {
						SocketChannel sc = (SocketChannel) key.channel();
						if(sc.finishConnect());  
		                else System.exit(1);
						
						System.out.println("connected");
						sc.register(selector, SelectionKey.OP_READ);// | SelectionKey.OP_WRITE
					}
					System.out.println("readable panduan");
					if(key.isReadable()) {
						SocketChannel sc = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int len = 0;
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						while((len = sc.read(buffer)) > 0) {
							buffer.flip();
							out.write(buffer.array(), 0, len);//数组方式，可以不用上下两句
							buffer.clear();
						}
						System.out.println("client read data:" + out.toString("UTF-8"));
//						key.cancel();
//						sc.close();
						
					}
//					System.out.println("writeable panduan");
//					if(key.isWritable()) {
//						
//					}
//					key.cancel();//千万不能有
//					System.out.println("cannel");
				}
			}
		}
	}
	
	public void write() {
		System.out.println("start write ,,, client in write()");
		try {
			while(true) {
				Scanner scanner = new Scanner(System.in);
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				buffer.put(scanner.nextLine().getBytes("UTF-8"));
				buffer.flip();//将起点指针放到0位置，limit指针放到最后一个数据放的位置,来方便读
				//从buffer中获取数据，都需要flip一下
				int len = socket.write(buffer);
				System.out.println("write to server:" + len);
//				socket.register(selector, SelectionKey.OP_READ);//这里也会阻塞
			}
			
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
