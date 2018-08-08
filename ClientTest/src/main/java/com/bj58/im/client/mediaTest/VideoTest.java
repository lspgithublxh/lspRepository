package com.bj58.im.client.mediaTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * lame.exe
 * 可以成功检测到波形
 * @ClassName:VideoTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月7日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class VideoTest extends Application{

	static byte[] buffer;
	
	public static void main(String[] args) {
//		audioTest();
		launch(args);
	}

	private static void barchar2() {
		BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis());
		Series<String, Number> se = createSerias2();
		Scene scene = new Scene(chart, 600, 400);
		chart.getData().add(se);
		Stage stage = new Stage();
		stage.setTitle("统计图");
		stage.setScene(scene);
		stage.show();
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

	private static Series<String, Number> createSerias2() {
		Series<String, Number> se = new XYChart.Series<>();
		AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
		TargetDataLine td;
		try {
			td = (TargetDataLine) AudioSystem.getLine(info);
			td.open(af);
			td.start();
			byte[] da = new byte[1024];
			td.read(da, 0, da.length);
			int index = 0;
			for(byte d : da) {
				se.getData().add(new Data<String, Number>(++index + "", d));
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		se.setName("统计图");
		return se;
	}
	
	private static Series<Number, Number> createSerias(LineChart<Number, Number> chart) {
		Series<Number, Number> se = new XYChart.Series<>();
		AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
		TargetDataLine td;
		try {
			td = (TargetDataLine) AudioSystem.getLine(info);
			td.open(af);
			td.start();
			byte[] da = new byte[10240];
			td.read(da, 0, da.length);
			int index = 0;
			for(byte d : da) {
				se.getData().add(new Data<Number, Number>(++index, d));
			}
			int[] sc = {index};
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("sleep ok");
						Series<Number, Number> sex = new XYChart.Series<>();
						td.read(da, 0, da.length);
						for(byte d : da) {
							sex.getData().add(new Data<Number, Number>(++sc[0], d));
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								chart.getData().add(sex);
							}
						});
					}
				}
			}).start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		se.setName("统计图");
		return se;
	}

	private static void audioTest() {
		AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
		try {
			//麦克风---是数据源
			TargetDataLine td = (TargetDataLine) AudioSystem.getLine(info);
			td.open(af);
			td.start();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					byte[] da = new byte[10240];
					while(true) {
						td.read(da, 0, da.length);
						showdata(da);
						//准备工作
						AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(da), af, da.length / af.getFrameSize());
						DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, af);
						try {
							//是音响，是数据目的地
							SourceDataLine sd = (SourceDataLine) AudioSystem.getLine(info2);
							sd.open(af);
							sd.start();
							buffer = new byte[10240];
							int len = ais.read(buffer, 0, buffer.length);
							//写入sd就会播放数据
							if(len > 0) {
								sd.write(buffer, 0, len);
							}
						} catch (LineUnavailableException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
				}

				private void showdata(byte[] da) {
					for(byte d : da) {
						System.out.print(d + ",");
					}
					System.out.println();
				}
			}).start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		justChart();
//		barchar2();
	}
}
