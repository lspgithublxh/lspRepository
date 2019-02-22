package com.bj58.jdkjinspecial.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {
	static Selector client = null;
	public static void main(String[] args) {
		try {
			client = Selector.open();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("start client:");
//					SocketChannel sc = new Socket("localhost", 11234).getChannel();
					SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", 11234));
					sc.configureBlocking(false);
					sc.register(client, SelectionKey.OP_WRITE);//是否可连接状态
					while(true) {
						if(client.select(1) > 0) {
							System.out.println("client select ok");
							Set<SelectionKey> keySet = client.selectedKeys();
							Iterator<SelectionKey> keyIt = keySet.iterator();
							while(keyIt.hasNext()) {
								SelectionKey key = keyIt.next();
								if(key.isConnectable()) {
									SocketChannel ssc = (SocketChannel) key.channel();
									ssc.register(client, SelectionKey.OP_WRITE);
								}else if(key.isWritable()) {
									
									SocketChannel ssc = (SocketChannel) key.channel();
									ByteBuffer bf = ByteBuffer.allocate(1024);
									
									Scanner scanner = new Scanner(System.in);
									String line = scanner.nextLine();
									
									bf.put(line.getBytes());
									bf.flip();
									ssc.write(bf);
									ssc.register(client, SelectionKey.OP_READ);// | SelectionKey.OP_WRITE
								}else if(key.isReadable()) {
									SocketChannel ssc = (SocketChannel) key.channel();
									ByteBuffer bf = ByteBuffer.allocate(1024);
									ssc.read(bf);
									byte[] b = new byte[1024];
									bf.flip();
									bf.get(b, 0, bf.remaining());
									System.out.println("client received:" + new String(b));
									ssc.register(client, SelectionKey.OP_WRITE);// | SelectionKey.OP_READ
								}
								keyIt.remove();
							}
							
						}
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
}
