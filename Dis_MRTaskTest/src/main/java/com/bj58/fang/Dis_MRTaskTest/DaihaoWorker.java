package com.bj58.fang.Dis_MRTaskTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DaihaoWorker {

	public static void main(String[] args) {
		boolean isShuffle = false;
		start();
		if(isShuffle) {
			startShuffle();
		}
	}

	private static void startShuffle() {
		//1.监听shuffle 客户端传来的请求：键值，，放到本地集合，直到完毕进行运算----或者两两计算reduce，得到结果;;;直到所有方都传来完毕的消息；；开始将结果写入文件，而把文件地址发送返回给server
		//2.
		try {
			ServerSocket shuffleTask = new ServerSocket(111568);
			while(true) {
				Socket oneTask = shuffleTask.accept();
				ReadThread rt = new ReadThread(oneTask);
				rt.start();//取得数据，然后再异步处理
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void start() {
		//1.监听master发送来的任务, 从本地找到任务，并且找到本地存储的文件块，执行而产生结果---放入文件。
			 //本地实现的是：map-combine:本地同键合并  ， 然后按照key -hash / shuffer台数  得到结果 而发送给 shuffer机器，进行异地shuffle-reduce：分布式同键合并
		//2.将结果文件路径发送回server
		///3.监听master传来的文件路径-文件请求， 返回文件流
		try {
			ServerSocket waitTask = new ServerSocket(111567);
			while(true) {
				Socket oneTask = waitTask.accept();
				new ReadThread(oneTask).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
