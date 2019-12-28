package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import com.google.common.collect.MapMaker;

import lombok.extern.slf4j.Slf4j;

/**
 * ByteBuffer: 通道的缓冲区(文件通道/socket通道)
 *  >zero-copy: 是应用程序并不改变数据的情况下的一种数据从磁盘到通道的一种简化：不经过复制到用户缓冲区这一步---而直接到目标缓冲区(比如socket buffer)。
 *Filechannel: 
 * 指标： position 文件内容位置
 * --零拷贝相关：
 * 方法： transferTo() 将文件通道filechannel的数据复制到一个可写通道channel....使用了zero-copy机制：(linux上sendFile()函数)
 *  				过程：DMA: 磁盘-->readBuffer, readBuffer-->SocketBuffer, DMA:SocketBuffer-->NIC buffer 两次上下文切换3次复制。。用户不能修改数据。
 * 						DMA: 磁盘-->kernelBuffer, DMA: kernelBuffer-->NIC buffer 两次上下文切换2次复制。。用户不能修改数据
 * 
 * mmap()零拷贝：用户可以修改数据。(零拷贝，就是减少用户-内核之间的cpu拷贝)(直接转移)
 *  				用户-->内核				内核-->用户,用户读写, 用户--> 内核								内核-->用户
 *  			过程：DMA:磁盘--> kernelBuffer, 						kernelBuffer-->socketBuffer,		DMA: socketBuffer-->NIC(protocol buffer)   共4次用户切换，3次拷贝：2次DMA拷贝，1次CPU拷贝。
 * 
 * 通道的缓冲区：创建方式：ByteBuffer.allocate():实现为HeapByteBuffer：页面不对齐(与本地JNI访问，需要复制到对齐的缓冲区)，在堆中。
 * 				 ByteBuffer.allocateDirect():实现为DirectByteBuffer: 页面对齐(), 在堆外。(通过调用Unsafe.allocate分配)，要自己管理和释放。
 * 				 FileChannel.map()：实现为MappedByteBuffer：页面对齐，堆外。Full GC时可以被回收。
 * NIO操作时的内存空间：
 * 
 * @author lishaoping
 * @date 2019年12月25日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util.file
 */
@Slf4j
public class FileChannelTest {

	/**
	 * 完全读
	 */
	public void read() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			RandomAccessFile file = new RandomAccessFile("D:\\test\\a.txt", "rw");
			FileChannel channel = file.getChannel();
			log.info("before read:" + channel.position());//当前前面byte个数
			int num = channel.read(buffer,channel.position());//从指定byte个数之后开始读
			log.info("read after:" + channel.position());//当前前面byte个数
			log.info("num:" + num);
			buffer.flip();
			byte[] d = new byte[1024];
			buffer.get(d, 0, num);
			log.info(new String(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String data) {
		ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
		try {
			RandomAccessFile file = new RandomAccessFile("D:\\test\\a.txt", "rw");
			FileChannel channel = file.getChannel();
			log.info("size:" + channel.size());//byte个数
			log.info("before read:" + channel.position());//当前前面byte个数::第一次为0，并非文件末尾
			int num = channel.write(buffer, channel.size());//从末尾开始写入,追加的方式?
			log.info("write after:" + channel.position());//当前前面byte个数
			log.info("num:" + num);
//			channel.force(true);//是否不止内容而且元数据也要刷到磁盘。
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void mergeToFile(String file1, String file2) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(file1, "rw");
			RandomAccessFile file2c = new RandomAccessFile(file2, "rw");
			FileChannel channel = file.getChannel();
			FileChannel channel2 = file2c.getChannel();
//			MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, channel.size());
			channel2.position(channel2.size());
			long hasTransfer = channel.transferTo(0, channel.size(), channel2);
			System.out.println("hastransfer:" + hasTransfer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void appendToFile(String data, String file2) {
		try {
			RandomAccessFile file2c = new RandomAccessFile(file2, "rw");
			FileChannel channel2 = file2c.getChannel();
//			MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, channel.size());
			channel2.position(channel2.size());
			ByteBuffer wrap = ByteBuffer.wrap(data.getBytes());
			int num = channel2.write(wrap);
			System.out.println("append count:" + num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FileChannelTest test = new FileChannelTest();
//		test.read();
//		test.write("heexx\r\nxsss\\\rnxx");
//		test.mergeToFile("D:\\test\\b.txt", "D:\\test\\a.txt");
		test.appendToFile("hellow", "D:\\test\\a.txt");
	}
}
