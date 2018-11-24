package com.bj58.fang.cache;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * 测试工具
 * @ClassName:PHCacheTestTool
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PHCacheTestTool<T> extends Application{

	public static void main(String[] args) {
		try {
			PuHotDataCache2<String> cache = new PuHotDataCache2<String>(new IGetValByKey<String>() {
				@Override
				public String getValByKey(String key) {
					return key + Math.random();
				}
			});
			CacheConfig conf = new CacheConfig();
			conf.setNumPerStatUnit(1);
			conf.setStatUnit(300);
			conf.setTaskPerid(1000);
			cache.config(conf);
			new PHCacheTestTool<String>().testCache(cache);
		} catch (NoCallbackInterException e) {
			e.printStackTrace();
		};
		
	}

	LineChart<Number, Number> chart = null;
	
	private PuHotDataCache2<T> source = null;
	
	private static PuHotDataCache2 quan = null;
	
	public void testCache(PuHotDataCache2<T> source) {
		this.source = source;
		quan = source;
		System.out.println(this);
		System.out.println(source+"--source");
		launch(new String[] {});
	}

	private void go() {
		//1.发送查询请求
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				initChart();
				//每秒50个请求处理
				while(true) {
					int d = (int) (Math.random() * 5000);
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(this);
					System.out.println(source+"--source2");
					long t1 = System.currentTimeMillis();
					T data = source.getData("" + d);
					long t2 = System.currentTimeMillis();
					System.out.println(String.format("test: put data: %s-%s, total%s", d, data, (t2 - t1)));
				}
			}
		}).start();
		//2.展示map数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				int[] index = {0};
				while(true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(chart == null) {
								return;
							}
							Series<Number, Number> sex = new XYChart.Series<>();
							System.out.println("--abc---" + index[0] + "--" + source.getMapSize());
							sex.getData().add(new Data<Number, Number>(++index[0], source.getMapSize()));
							chart.getData().add(sex);
						}
					});
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		source = quan;
		go();
	}
	
	private LineChart<Number, Number> initChart() {
		Object lock = new Object();
		boolean[] ok = {false};
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chart = ShowLine.justChart2();
				synchronized (lock) {
					lock.notify();
					ok[0] = true;
				}
			}
		});
		synchronized (lock) {
			if(!ok[0]) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return chart;
	}
}
