package com.bj58.im.server.ServerTest.NIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
public class ServerBody2 extends Thread{

	public static void main(String[] args) throws IOException {
		ServerBody2 sb = new ServerBody2();
		sb.start();
		sb.write();
	}
	
	Map<String, SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();
	
	int icount = 0;
	Object lock = new Object();
	
	public void write() {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("server start write");
		try {
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String broacast = scanner.nextLine();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				for(Entry<String, SocketChannel> entry : map.entrySet()) {
					buffer.put(String.format("hello, %s, %s", entry.getKey(), broacast).getBytes("UTF-8"));
					buffer.flip();
					entry.getValue().write(buffer);
					buffer.clear();
					System.out.println(String.format("write to %s is ok",  entry.getKey()));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress("localhost", 12345), 1024);//backlog为1024
		channel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server started!!");
		boolean isPic = false;
		FileOutputStream wfile = null;
		String fileName = null;
		long lenth = 0;
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
						
						map.put(sc.getRemoteAddress().toString() + "," + System.currentTimeMillis(), sc);
						if(icount == 0) {
							icount++;
							synchronized (lock) {
								lock.notify();
							}
						}
					}
					if(key.isReadable()) {
						SocketChannel sc = (SocketChannel) key.channel();//肯定是这种channel
//						InputStream bu = sc.socket().getInputStream();
//						BufferedInputStream bu = new BufferedInputStream(sc.socket().getInputStream());
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						int len = -1;
//						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int i = 0;
						byte[] b = new byte[1024];
						while((len = sc.read(buffer)) > 0) {//因为非阻塞通信
							String txt = new String(b);
							System.out.println(txt);
							if(txt.startsWith("end")) {
								isPic = false;
							}
							if(isPic) {
								if(wfile == null) {
									File f = new File("D:\\log\\" + fileName);
									if(!f.exists()) f.createNewFile();
									else {f.delete();f.createNewFile();}
									wfile = new FileOutputStream(f, true);
								}
//								System.out.println(buffer.array().length + "," + len);
								wfile.write(b, 0, len);
								lenth += len;
//								wfile.flush();
							}else {
								
								if(txt.startsWith("write_img")) {
									isPic = true;
									fileName = txt.split("\\|")[1];
									fileName = fileName.contains("\\") ? fileName.substring(fileName.lastIndexOf("\\") + 1) : fileName.substring(fileName.lastIndexOf("/") + 1);
									System.out.println("receive fileName:" + fileName);
								}else {
									System.out.println(i++ + "read from socket:" + len + new String(b));
//									buffer.flip();//
//									out.write(buffer.array(), 0, len);
//									buffer.clear();
								}
							}
						}
						if(wfile != null) {
							if(!isPic) {
								fileName = null;
								isPic = false;
								wfile.flush();
								wfile.close();
								System.out.println("write file ok!!" + lenth);
								System.out.println(lenth);
								wfile = null;
							}

							
						}
//						System.out.println("sever read data:" + out.size() + out.toString("UTF-8"));
//						buffer.clear();
//						buffer.put("hello client".getBytes("UTF-8"));
//						buffer.flip();//将起点指针放到0位置，limit指针放到最后一个数据放的位置,来方便读
						//从buffer中获取数据，都需要flip一下
//						len = sc.write(buffer);
						System.out.println("sever write data:" + len);
					}
				}
			}
		}
	}
	
	
}
