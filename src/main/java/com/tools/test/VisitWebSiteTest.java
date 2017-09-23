package com.tools.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 访问网站的爬虫
 *应当对网站做限制，否则牵连到其他网站，跑太久跑到内存爆炸
 *1.常见问题--内存垃圾回收异常，死掉一些线程后继续可以跑：Exception in thread "pool-1-thread-100" java.lang.OutOfMemoryError: GC overhead limit exceeded
 *2.常见问题2：堆栈溢出，导致内存占用暴增Exception in thread "pool-1-thread-3" java.lang.OutOfMemoryError: Java heap space 
 *@author lishaoping
 *ToolsTest
 *2017年9月22日
 */
public class VisitWebSiteTest {

	/**
	 * 本质只有两类方法，
	 * 但是特殊情况的处理不同，移除：返回null,有的抛出异常，有的阻塞；添加：返回false,抛出异常，阻塞
	 * 3个移除并返回，2个直接返回，3个添加元素
	 * 能存一亿个任务
	 */
	private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000000);//100000000
	
	private Set<String> visitedQ = new HashSet<String>();
	
	private int length = 100;
	
	private String[] permUrlArr = new String[length];
	
	private int i = 0;
		
	private String keysite = ".people.";
	
	/**
	 * 没有并发问题
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月22日
	 * @param html
	 */
	public  void putToQueue(String html) {
		try {
			queue.put(html);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月22日
	 * @param htmls
	 */
	public void putToQueue2(String[] htmls) {
//		System.out.println("-------start put to queue2-----------");
		try {
			for(String html : htmls) {
				if(null != html) {
					queue.put(html);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		System.out.println("-------end put to queue2-----------");
	}
	
	public  String getFromQueue() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}
	
//	public void putToVisitedQ(String url) {
//		try {
//			visitedQ.put(url);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		final VisitWebSiteTest test = new VisitWebSiteTest();
		test.start(test);
//		test.start2();
		
	}
	
	public void start2() {
		final VisitWebSiteTest test = this;
		String initialUrl = "http://www.people.com.cn/";
		test.putToQueue(initialUrl);
		while(true) {
			//1.取出一个url
//			System.out.println("start get url:");
			String url = test.getFromQueue();
//			System.out.println(Thread.currentThread().getName() + url);
			//1.2已经访问过的页面
			boolean visited = test.isVisited(url);
			if(visited) {
				continue;
			}
			//2.获取html
			String html = test.getPage(url);
			//3.解析页面取到sites
			String[] sites = test.getAllNewSite(html);
			//4.加入队列中
			test.putToQueue2(sites);
			
		}
	}
	
	public void start(final VisitWebSiteTest test) {
		String initialUrl = "http://www.people.com.cn/";
		int threadMount = 100;
		Executor excutor = Executors.newFixedThreadPool(threadMount);
		
		test.putToQueue(initialUrl);
		final Object lock = new Object();
		for(int i = 0; i < threadMount; i++) {
			//1.
			excutor.execute(new Runnable() {
				
				public void run() {
					while(true) {
						//1.取出一个url
//						System.out.println("start get url:");
						String url = test.getFromQueue();
//						System.out.println(Thread.currentThread().getName() + url);
						//1.2已经访问过的页面
						boolean visited = test.isVisited(url);
						if(visited) {
							continue;
						}
						//2.获取html
						String html = test.getPage(url);
						//3.解析页面取到sites
						String[] sites = test.getAllNewSite(html);
						//4.加入队列中
						test.putToQueue2(sites);
						
					}
					
				}
			});
		}
	}
	
	/**
	 * 插入数据库
	 *@author lishaoping
	 *ToolsTest
	 *2017年9月23日
	 * @param url
	 * @return
	 */
	public boolean isVisited( String url) {
		synchronized (VisitWebSiteTest.class) {
			if(url.length() > 100) {
				url = url.substring(0, 100);
			}
			if(url.contains("\r\n")) {
				url = url.substring(0, url.indexOf("\r\n"));
			}else if(url.contains("\r")) {
				url = url.substring(0, url.indexOf("\r"));
			}else if(url.contains("\n")) {
				url = url.substring(0, url.indexOf("\n"));
			}
			
			if(url.contains("\"")) {
				url = url.substring(0, url.indexOf("\""));
			}
			if(!visitedQ.contains(url) && url.contains(keysite)) {
				visitedQ.add(url);
				if(i > 0 && i % length == 0) {//1000条太多，不行
					JDBC_MYSQLTool.multipleInsert(permUrlArr);
					permUrlArr = null;
					permUrlArr = new String[length];
					i = 0;
				}
				if(url.contains("'")) {
					url = url.replace("'", "-");
				}
				permUrlArr[i++] = url;
				return false;
			}
			return true;
		}
	}
	
	public String urlTransfer(String url) {
		return null;
	}
	
	public String getPage(String url) {
//		System.out.println("---connection start----");
		String res = null;
		if(url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".webp")) {
			return res;
		}
		try {
			URL uc = new URL(url);
			URLConnection con = uc.openConnection();
			InputStream in = con.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] arr = new byte[1024];
			int len = 0;
			while((len = in.read(arr)) > 0) {
				out.write(arr, 0, len);
			}
			res = out.toString();
			out.close();
			in.close();
			
		} catch (MalformedURLException | java.net.UnknownHostException e) {
			if(e instanceof java.net.UnknownHostException) {
				System.out.println("--------unknownHost----" + url);
			}
//			e.printStackTrace();
		} catch (IOException e) {//502返回错误，有时会报   http://www.workercn.cn/
			//403错误也会有http://www.xinhuanet.com/
			//Connection timed out: connect
//			e.printStackTrace();
			if(e instanceof java.net.ConnectException) {
				System.out.println("------------timeout-----" + url);
			}
		}
//		System.out.println("-----connection end--------");
		return res;
	}
	
	private Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
	private Matcher m = null;
	/*
	 * parse html
	 */
	public String[] getAllNewSite(String html) {
//		System.out.println("-------start get all new site--------");
		if(null == html) {
			return new String[] {};
		}
		m = p.matcher(html);
		Set<String> set = new HashSet<String>();
		while(m.find()) {
			try {
				String url = m.group(1);
				set.add(url);
			}catch(IndexOutOfBoundsException | java.lang.IllegalStateException e) {
			}
			
		}
//		System.out.println("-------end get all new site--------");
		return set.toArray(new String[0]);
	}
	
	
}
