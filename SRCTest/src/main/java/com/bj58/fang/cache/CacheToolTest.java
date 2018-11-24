package com.bj58.fang.cache;

public class CacheToolTest {
	
	public static void main(String[] args) {
		try {
			PuHotDataCache2<String> cache = new PuHotDataCache2<String>(new IGetValByKey<String>() {
				@Override
				public String getValByKey(String key) {
					return key + Math.random();
				}
			});
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//每秒50个请求处理
					while(true) {
						int d = (int) (Math.random() * 5000);
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String data = cache.getData("" + d);
						System.out.println(d + "----" + data);
					}
				}
			}).start();
		} catch (NoCallbackInterException e) {
			e.printStackTrace();
		}
	}
}
