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
public class PHCacheTestTool extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	LineChart<Number, Number> chart = null;
	
	private void go() {
		int[] index = {0};
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(chart == null) {
					return;
				}
				Series<Number, Number> sex = new XYChart.Series<>();
				System.out.println("--abc---" + index[0] + "--" + entity.getKey());
				sex.getData().add(new Data<Number, Number>(++index[0], Integer.valueOf(map.keySet().size())));
				chart.getData().add(sex);
			}
		});
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		go();
	}
}
