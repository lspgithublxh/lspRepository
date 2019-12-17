package com.li.shao.ping.KeyListBase.datastructure.geneutil.nio;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * 重新理解nio的原理
 * 用户--->缓冲区--->通道(端口值，阻塞性；注册到选择器<keys,channel>)---->选择器(主动方;被系统调用回调)
 *                (指针：数据末尾位置p1、动态读写位置指针p2)
 *                 (flip: p1移动到p2,p2移动到0)
 *                 (compact: 将p2到p1之间的数据移动到0开始位置, p2指向这段新数据的末尾,p1=capacity)(从而可以继续写)
 *                 (clear: 设置p2=0, p1=capacity)
 *                 (mark: 设置mark=p2-1)
 *                 (reset: 设置p2=mark+1)
 *                 (rewind: 设置p2=0)
 *                 (hasRemaining: p1-p2)
 *p1: limit 初始=capacity
 *p2: position 初始=0 即指向的位置是下一个被操作的数据的位置
 * :capacity容量
 * :mark position上一个位置/初始=-1
 *
 * 通道1-通道2的连接：
 * 通道的数据源/数据池：文件/网络
 * 通道里的数据和用户交互：必须通过Buffer
 * 
 * 
 * selector: 通道有I/O事件则通知selector, select()等待直到有IO事件通知
 * 
 *优点：通道里的数据可以移动读写：
 *    非阻塞通道的操作可以立即返回。//accept().read().write()都是。但选择器不是。
 *    
 *    
 *测试：可以用户端NIO, 服务端BIO.   (客户端只是写自然的可以不用selector)
 * @author lishaoping
 * @date 2019年12月16日
 * @package  com.li.shao.ping.KeyListBase.datastructure.geneutil.nio
 */
@Slf4j
public class NServiceServerUtil {

	public void startIt() {
		try {
			
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress("127.0.0.1", 8080));
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);//同时可以传递附加对象attach
			while(true) {
				int count = selector.select();//至少一个通道selected才返回
				log.info("select get:" + count);
				if(count > 0) {
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = keys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();//不再监听
						if(key.isAcceptable()) {
							SelectableChannel serverChannel = key.channel();
							ServerSocketChannel sc = (ServerSocketChannel) serverChannel;
							SocketChannel clientN = sc.accept();
							clientN.configureBlocking(false);
							clientN.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));//附加一个对象
						}else if(key.isReadable()) {//必然只是socketChannel
							SocketChannel clientN = (SocketChannel) key.channel();
							ByteBuffer attachment = (ByteBuffer) key.attachment();
							int len = clientN.read(attachment);//假定已经读取完毕，不用while读取了
							attachment.flip();
							byte[] pool = new byte[1024];
							attachment.get(pool, 0, len);
							log.info("received:" + new String(pool));
//							key.cancel();
							//回写数据--不用等是否可写
							log.info("write data");
							ByteBuffer buffer = ByteBuffer.wrap("hello,client".getBytes());
							while(buffer.hasRemaining()) {
								int len2 = clientN.write(buffer);
							}
							//必然已经读取完毕，可以复位reset, 也可以compact移动到初始位置---这么操作后就不可写
							buffer.compact();
							
						}else if(key.isWritable()) {
							SocketChannel clientN = (SocketChannel) key.channel();
							ByteBuffer buffer = ByteBuffer.wrap("hello,client".getBytes());
							while(buffer.hasRemaining()) {
								int len = clientN.write(buffer);
							}
							//必然已经读取完毕，可以复位reset, 也可以compact移动到初始位置---这么操作后就不可写
							buffer.compact();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 都是ByteBuffer的子类，即可以和通道读写联系。
	 * 间接模式：使用堆内存：HeapByteBuffer
	 * 直接模式：使用系统内存：(虚拟内存)MappedByteBuffer (可以写入文件force(), 可以载入物理内存load())
	 */
	public void tt() {
		
	}
	
	
	public static void main(String[] args) {
		NServiceServerUtil util = new NServiceServerUtil();
		util.startIt();
	}
}
