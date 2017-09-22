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
 * ������վ������
 *
 *@author lishaoping
 *ToolsTest
 *2017��9��22��
 */
public class VisitWebSiteTest {

	/**
	 * ����ֻ�����෽����
	 * ������������Ĵ���ͬ���Ƴ�������null,�е��׳��쳣���е���������ӣ�����false,�׳��쳣������
	 * 3���Ƴ������أ�2��ֱ�ӷ��أ�3�����Ԫ��
	 */
	private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
	
	private Set<String> visitedQ = new HashSet<String>();
	
	/**
	 * û�в�������
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
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
	 *2017��9��22��
	 * @param htmls
	 */
	public void putToQueue2(String[] htmls) {
		System.out.println("-------start put to queue2-----------");
		try {
			for(String html : htmls) {
				if(null != html) {
					queue.put(html);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-------end put to queue2-----------");
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
	
	/**
	 * ������û������
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		for(int i = 0; i < 10000; i++) {
			queue.put(i+ "");
		}
		final List<String> list = new ArrayList<String>();
		//1.������
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						String x = queue.take();
						System.out.print(x + ",");
//						list.add(x);
//						System.out.println("read:" + Thread.currentThread().getName() + "-----------" + x);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		//֤��list��������������⣬��ȡ����Ԫ��,���ᶪ��һЩԪ��
//		Thread.currentThread().sleep(5000);
////		Collections.sort(list); //�п�Ԫ��
//		System.out.println("-------------" + list);
//		int c = 0;
//		for(int i = 0; i< 10000; i ++) {
//			if(!list.contains(i + "")) {
//				c++;
//				System.out.println(i);
//			}
//		}
//		System.out.println(c);
	}
	
	/**
	 * ����дҲû������
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.����д
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {//1�������
			final int  j = i;
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						queue.put(j + "");
//						System.out.println("write:" + Thread.currentThread().getName() + "-----------" + j);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
//
		final Set<String> list = new HashSet<String>();
		Thread.currentThread().sleep(5000);
//		Collections.sort(list); //�п�Ԫ��
//		System.out.println("-------------" + list);
		System.out.println(queue.size());
		int c = 0;
		int s = queue.size();
		for(int i = 0; i< s; i ++) {
			String x = queue.take();
				
				if(list.contains(x)) {
					System.out.println("---------------");
				}
				list.add(x);
				System.out.print(x + ",");
			if(x == null) {
				System.out.println("error" + x);
			}
		}
		System.out.println(list.size());	
		System.out.println(list);
	}
	
	/**
	 * ��������д��������null,Ҳû���ظ�����
	 *@author lishaoping
	 *ToolsTest
	 *2017��9��22��
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10000);
		
		//1.����д�Ͷ�
		Executor excutor = Executors.newFixedThreadPool(100);
		for(int i = 0; i < 10000; i++) {
			final int  j = i;
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						queue.put(j + "");
						System.out.print("*" + j);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			excutor.execute(new Runnable() {
				
				public void run() {
					try {
						System.out.print("&" + queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		final VisitWebSiteTest test = new VisitWebSiteTest();
//		test.start(test);
		test.start2();
		
	}
	
	public void start2() {
		final VisitWebSiteTest test = this;
		String initialUrl = "http://www.people.com.cn/";
		test.putToQueue(initialUrl);
		while(true) {
			//1.ȡ��һ��url
			System.out.println("start get url:");
			String url = test.getFromQueue();
			System.out.println(Thread.currentThread().getName() + url);
			//1.2�Ѿ����ʹ���ҳ��
			boolean visited = test.isVisited(url);
			if(visited) {
				continue;
			}
			//2.��ȡhtml
			String html = test.getPage(url);
			//3.����ҳ��ȡ��sites
			String[] sites = test.getAllNewSite(html);
			//4.���������
			test.putToQueue2(sites);
			
		}
	}
	
	public void start(final VisitWebSiteTest test) {
		String initialUrl = "http://www.people.com.cn/";
		int threadMount = 100;
		Executor excutor = Executors.newFixedThreadPool(threadMount);
		
		test.putToQueue(initialUrl);
		for(int i = 0; i < threadMount; i++) {
			//1.
			excutor.execute(new Runnable() {
				
				public void run() {
					while(true) {
						//1.ȡ��һ��url
						System.out.println("start get url:");
						String url = test.getFromQueue();
						System.out.println(Thread.currentThread().getName() + url);
						//1.2�Ѿ����ʹ���ҳ��
						boolean visited = test.isVisited(url);
						if(visited) {
							continue;
						}
						//2.��ȡhtml
						String html = test.getPage(url);
						//3.����ҳ��ȡ��sites
						String[] sites = test.getAllNewSite(html);
						//4.���������
						test.putToQueue2(sites);
						
					}
					
				}
			});
		}
	}
	
	public synchronized boolean isVisited( String url) {
		if(!visitedQ.contains(url)) {
			visitedQ.add(url);
			return false;
		}
		return true;
	}
	
	public String urlTransfer(String url) {
		return null;
	}
	
	public String getPage(String url) {
		System.out.println("---connection start----");
		String res = null;
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
		} catch (MalformedURLException | java.net.UnknownHostException e) {
			if(e instanceof java.net.UnknownHostException) {
				System.out.println("--------unknownHost----" + url);
			}
//			e.printStackTrace();
		} catch (IOException e) {//502���ش�����ʱ�ᱨ   http://www.workercn.cn/
			//403����Ҳ����http://www.xinhuanet.com/
			//Connection timed out: connect
//			e.printStackTrace();
			if(e instanceof java.net.ConnectException) {
				System.out.println("------------timeout-----" + url);
			}
		}
		System.out.println("-----connection end--------");
		return res;
	}
	
	private Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
	private Matcher m = null;
	/*
	 * parse html
	 */
	public String[] getAllNewSite(String html) {
		System.out.println("-------start get all new site--------");
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
		System.out.println("-------end get all new site--------");
		return set.toArray(new String[0]);
	}
	
	
}
