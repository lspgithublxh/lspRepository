package com.bj58.fang.cache.HotDataMemCache;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * 测试工具----显示remove-put之后的净增量
 * @ClassName:PHCacheTestTool
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月24日
 * @Version V1.0
 * @Package com.bj58.fang.cache
 */
public class PHCacheTestTool4<T> extends Application{

	public static void main(String[] args) {
		try {
			PuHotDataCache3<String, String> cache = new PuHotDataCache3<String, String>(new IGetValByKey<String, String>() {
				@Override
				public String getValByKey(String key) {
					return key + Math.random();
				}
			});
			CacheConfig conf = new CacheConfig();
			conf.setNumPerStatUnit(1);
			conf.setStatUnit(500 * 100);//500 * 20 平均速率:1/50ms  而20ms请求一次，共1000条， 那么每条的平均速率1/50ms 所以有50%的会被移除
			conf.setTaskPerid(500);
			conf.setMaxKeyNum(5000);
//			conf.setUpdateDelay(1);
//			conf.setUpdatePerid(1000);
			cache.configAndStartClean(conf);
			IGetValByKey<String, String> source = new IGetValByKey<String, String>() {
				@Override
				public String getValByKey(String key) {
					return key + Math.random();
				}
			};
			ErJIHotDataCache<String, String> erji = new ErJIHotDataCache<String, String>(cache, source);
			conf.setStatUnit(500 * 16);//500 * 10
			conf.setMaxKeyNum(2500);
			conf.setRateOkCount(4);
			erji.configAndStartClean(conf);
			new PHCacheTestTool4<String>().testCache(erji, cache);
		} catch (NoCallbackInterException e) {
			e.printStackTrace();
		};
		
	}

	LineChart<Number, Number> chart = null;
	LineChart<Number, Number> chart2 = null;
	LineChart<Number, Number> cleanTimeChart = null;
	LineChart<Number, Number> putTimeChart = null;
	
	private ErJIHotDataCache<String, T> source = null;
	private PuHotDataCache3<String, T> source2 = null;
	
	@SuppressWarnings("rawtypes")
	private static ErJIHotDataCache quan = null;
	@SuppressWarnings("rawtypes")
	private static PuHotDataCache3 quan2 = null;
	
	public void testCache(ErJIHotDataCache<String, T> erji, PuHotDataCache3<String, String> cache) {
		this.source = erji;
		quan = erji;
		quan2 = cache;
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
						Thread.sleep(4);//补充速度降低
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					long t1 = System.currentTimeMillis();
					T data = source.getData("" + d);
//					long t2 = System.currentTimeMillis();
//					System.out.println(String.format("test: put data: %s-%s, total take %s ms", d, data, (t2 - t1)));
				}
			}
		}).start();
		//2.展示map数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				final int[] index = {0};
				while(true) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(chart == null) {
								return;
							}
							Series<Number, Number> sex = new XYChart.Series<>();
							sex.getData().add(new Data<Number, Number>(++index[0], source.getMapSize()));
							chart.getData().add(sex);
							
							Series<Number, Number> sex2 = new XYChart.Series<>();
							sex2.getData().add(new Data<Number, Number>(++index[0], source2.getMapSize()));
							chart2.getData().add(sex2);
							
							Series<Number, Number> cleanSex = new XYChart.Series<>();
							cleanSex.getData().add(new Data<Number, Number>(++index[0], source.getAvgCleanTime()));
							cleanTimeChart.getData().add(cleanSex);
							
							Series<Number, Number> putSex = new XYChart.Series<>();
							putSex.getData().add(new Data<Number, Number>(++index[0], source.getAvgGetTime()));
							putTimeChart.getData().add(putSex);
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

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage arg0) throws Exception {
		//会new新的本类的实例，所以要静态全局
		source = quan;
		source2 = quan2;
		go();
	}
	
	private LineChart<Number, Number> initChart2() {
		final Object lock = new Object();
		final boolean[] ok = {false};
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chart2 = justChart2("二级缓存存量净增图");
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
		return chart2;
	}
	
	private LineChart<Number, Number> initChart() {
		final Object lock = new Object();
		final boolean[] ok = {false};
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chart = justChart2("一级缓存存量净增图");
				chart2 = justChart2("二级缓存存量净增图");
				cleanTimeChart = justChart2("key清除平均耗时图(μs)");
				putTimeChart = justChart2("获取平均耗时图(μs)");
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
	
	public static LineChart<Number, Number> justChart2(String name) {
		LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
		chart.autosize();
		chart.setCreateSymbols(false);
		Series<Number, Number> se = new XYChart.Series<>();
		Scene scene = new Scene(chart, 600, 400);
		chart.getData().add(se);
		Stage stage = new Stage();
		stage.setTitle(name);
		stage.setScene(scene);
		stage.show();
		return chart;
	}
}
