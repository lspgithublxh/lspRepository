package com.bj58.im.client.ClientTest.NIO;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
		clientUI();
	}
	
	public static void clientUI() {
		ClientBody bo = new ClientBody();
		
		bo.start();
		
		bo.write();
		
	}
	
	Object lock = new Object();
	
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
						synchronized (lock) {
							lock.notify();
						}
					}
					System.out.println("readable panduan");
					if(key.isReadable()) {
						SocketChannel sc = (SocketChannel) key.channel();
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int len = 0;
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						while((len = sc.read(buffer)) > 0) {//会抛出异常---在客户端断开连接的情况下
							buffer.flip();
							out.write(buffer.array(), 0, len);//数组方式，可以不用上下两句
							buffer.clear();
						}
						System.out.println("client read data:" + out.toString("UTF-8"));
					}
					//key.cancel();不能有，否则再也不能听到这种事件
					
				}
			}
		}
	}
	
	public void write() {
		//开始写数据
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(new byte[1024]);
		buffer.flip();
		buffer.clear();
		try {
			while(true) {
				Scanner scanner = new Scanner(System.in);
				String nextLine = scanner.nextLine();
//				ByteBuffer buffer = ByteBuffer.allocate(1024);
				buffer.clear();
				buffer.put(nextLine.getBytes("UTF-8"));
				buffer.flip();//将起点指针放到0位置，limit指针放到最后一个数据放的位置,来方便读
				//从buffer中获取数据，都需要flip一下
				
				
//				socket.socket().getOutputStream().flush();//不能阻止,因为服务端读取的时候用了bytebuffer缓存
				
//				socket.register(selector, SelectionKey.OP_READ);//这里也会阻塞
				if("1".equals(nextLine)) {
					//开发发送图片
					//发送有先后
					//对于文本，补充0发送不会影响到文本的显示,,,因为ascll码不会有这个值，，发送补充位,,目前不需要了
					buffer.clear();
					buffer.put(new byte[1024]);//"test img send".getBytes("UTF-8")
					buffer.flip();
					socket.write(buffer);
					
					//发送标记位,标记数,是1024位，保证一次都完毕
					buffer.clear();
					buffer.put("write_img".getBytes("UTF-8"));//"test img send".getBytes("UTF-8")
					buffer.put(new byte[1024 - "write_img".length()]);
					buffer.flip();
					socket.write(buffer);
					//发送图片开始
					
//					buffer.put("发送的图片正文".getBytes("UTF-8"));
					FileInputStream in = new FileInputStream("D:\\a.png");
					int l = 0;
					byte[] b = new byte[1024];
					while((l = in.read(b)) > 0) {
						buffer.clear();
						buffer.put(b, 0, l);
						buffer.flip();
						socket.write(buffer);
					}
					//结束的补充字符串
//					buffer.clear();
//					buffer.put(new byte[1024 - l]);//"test img send".getBytes("UTF-8")
//					buffer.flip();
//					socket.write(buffer);
				}else {
					int len = socket.write(buffer);
					System.out.println("write to server:" + len);
				}
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
