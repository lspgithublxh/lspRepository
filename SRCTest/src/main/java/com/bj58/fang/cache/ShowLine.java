package com.bj58.fang.cache;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class ShowLine extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	public static LineChart<Number, Number> justChart2() {
		LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
		chart.autosize();
		chart.setCreateSymbols(false);
		Series<Number, Number> se = new XYChart.Series<>();
		Scene scene = new Scene(chart, 600, 400);
		chart.getData().add(se);
		Stage stage = new Stage();
		stage.setTitle("统计图");
		stage.setScene(scene);
		stage.show();
		return chart;
	}
	
	private static void justChart() {
		LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
		chart.autosize();
		chart.setCreateSymbols(false);
		Series<Number, Number> se = createSerias(chart);
		Scene scene = new Scene(chart, 600, 400);
		chart.getData().add(se);
		Stage stage = new Stage();
		stage.setTitle("统计图");
		stage.setScene(scene);
		stage.show();
		
	}
	
	private static Series<Number, Number> createSerias(LineChart<Number, Number> chart) {
		Series<Number, Number> se = new XYChart.Series<>();
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while(true) {
						System.out.println("sleep ok");
						Series<Number, Number> sex = new XYChart.Series<>();
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								chart.getData().add(sex);
							}
						});
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		se.setName("统计图");
		return se;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		justChart();
	}
}
