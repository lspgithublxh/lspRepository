package com.bj58.fang.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.quartz.CronExpression;

/**
 * 不用数组中的数组？----首次hash二次另一种映射区分--还是有相同的--就无穷无尽了---两次定位到元素----速度更快？---因为没有再次hash的条件
 * @ClassName:ThreadPool
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月4日
 * @Version V1.0
 * @Package com.bj58.fang.thread
 */
public class ThreadPool {

	public static void main(String[] args) {
//		bingxingzhixingshunxuqu();
//		bingxing();
		xianhou();
	}

	private static void xianhou() {
		long t1 = System.nanoTime();
		Calendar dar = Calendar.getInstance();
		int se = dar.get(Calendar.SECOND);
		long t2 = System.nanoTime();
		System.out.println((t2 - t1) / 1000 /1000 +"ms");
		try {
			long t3 = System.nanoTime();
			CronExpression expression = new CronExpression("0/5 * * * * ?");
			if(expression.isSatisfiedBy(new Date())) {
				System.out.println("satisfied");
			}
			long t4 = System.nanoTime();
			System.out.println((t4 - t3) / 1000 + "us---");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long ti = System.nanoTime();
		SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = fo.format(new Date());
		String x = s.substring(s.lastIndexOf(":") + 1);
		int m = Integer.valueOf(x);
		long ti3 = System.nanoTime();
		System.out.println((ti3 - ti)/1000 + "usxxxxxx");
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		service.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("fisrt" + System.currentTimeMillis());
				long ti = System.nanoTime();
				SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String s = fo.format(new Date());
				String x = s.substring(s.lastIndexOf(":") + 1);
				int m = Integer.valueOf(x);
				long ti3 = System.nanoTime();
				System.out.println((ti3 - ti)/1000 + "usxxxxxx");//700us
				long t1 = System.nanoTime();
				Calendar dar = Calendar.getInstance();
				int se = dar.get(Calendar.SECOND);
				long t2 = System.nanoTime();
				int curhour = dar.get(Calendar.HOUR_OF_DAY);
				int curmin = dar.get(Calendar.MINUTE);
				int rs = curhour * 100 + curmin;
				long t23 = System.nanoTime();
				System.out.println((t23 - t2) / 1000 + "us for hourmin" + rs);//1us
				System.out.println((t2 - t1) / 1000 +"us");//大约143us
				
				try {
					long t3 = System.nanoTime();
					CronExpression expression = new CronExpression("0/5 * * * * ?");
					if(expression.isSatisfiedBy(new Date())) {
						System.out.println("satisfied");
					}
					long t4 = System.nanoTime();
					System.out.println((t4 - t3) / 1000 + "us---");//稳定之后大约1.5ms
				} catch (ParseException e) {
					e.printStackTrace();
				}
				System.out.println(dar.get(Calendar.HOUR_OF_DAY));
				System.out.println(dar.get(Calendar.SECOND));
				if(dar.get(Calendar.HOUR_OF_DAY) > 7 && dar.get(Calendar.HOUR_OF_DAY) < 23) {
					
				}
			}
		}, 0, 1000 * 3, TimeUnit.MILLISECONDS);
		
		
		
		service.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("two" + System.currentTimeMillis());
			}
		}, 0, 1000 * 10, TimeUnit.MILLISECONDS);
		
	}

	/**
	 * 并行n次计算，顺序n次取值；    计算和取值也是并行的
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月19日
	 * @Package com.bj58.fang.thread
	 * @return void
	 */
	private static void bingxingzhixingshunxuqu() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<Future<Integer>> lis = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Future<Integer> future = service.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(1000);
					System.out.println("------" + Thread.currentThread().getName());
					String name = Thread.currentThread().getName();
					Integer n = Integer.valueOf(name.substring(name.lastIndexOf("-") + 1));
					return n;
				}
			});
			lis.add(future);
		}
		for(Future<Integer> f : lis) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 单纯并行n次执行，没有返回结果
	 * @param 
	 * @author lishaoping
	 * @Date 2018年11月19日
	 * @Package com.bj58.fang.thread
	 * @return void
	 */
	private static void bingxing() {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for(int i = 0; i < 10; i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
						System.out.println("------" + Thread.currentThread().getName());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("--main---");
	}
}
