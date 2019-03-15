package com.bj58.fang.Dis_MRTaskTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 整个过程：Master----发送job到每个worker, 每个worker里自带文件(即master也分发file给worker), 使用job计算好文件后输出到一个文件，发送文件地址给master;;最后master就收集到了结果文件集合：然后查询合并
 * 	shuffle过程：每个worker在mapper-combine之后遍历键，对键按shuffle机器台数进行hash，来将该键发送给shuffle进行shuffle-reduce计算，将结果存在本机中
 * 				
 * @ClassName:Master
 * @Description:
 * @Author lishaoping
 * @Date 2019年3月13日
 * @Version V1.0
 * @Package com.bj58.fang.Dis_MRTaskTest
 */
public class DaihaoMaster {

	public static void main(String[] args) {
		start();
		listen();
	}

	private static void listen() {
		final Map<String, Object> taskResultMap = new HashMap<>();//消息进一步被消费
		ServerSocket receveResult;
		try {
			receveResult = new ServerSocket(15677);
			while(true) {
				Socket received = receveResult.accept();
				final ReadThread rt = new ReadThread(received);
				rt.start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						//异步取得结果
						byte[] data = rt.getData();
						System.out.println("received mes is:" + rt.mes);
						//处理mes, 和 data；；再放到taskResultMap里
						taskResultMap.put(rt.mes, data);
					}
				}).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void start() {
		//1.发送任务---暂时用string代替，仅仅一个名称;;任务代码在worker上
		//2.监听，直到worker发送回文件地址;读取这些块文件，放到相应本地map中，或者存到磁盘--task-filepaths，合并为一个文件输出----为下载功能
		try {
			Socket sendTask = new Socket("localhost", 111567);
			new WriteThread(sendTask, "task|id|path|name").start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
