package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 两种形式 的 弦 振动：静态的x-t :z 色彩图   和 动态的 x :z 波形图
 * ;;步进运动--螺旋运动；点的运动
 * @ClassName:PWFWave
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月31日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class PWFWave extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		seeTheWave(primaryStage);
		pointMove(primaryStage);
	}
	
	double a = 0.1;
	double b = 1;
	
	private void pointMove(Stage stage) {
		Group group = new Group();
		for(int i = 0; i < 100; i++) {
			Rectangle rect  = new Rectangle(5, 5, Color.RED);
			rect.setLayoutX(100);
			rect.setLayoutY(100);
			group.getChildren().add(rect);
			int[] ia = {i, i};
			new Thread(new Runnable() {
				@Override
				public void run() {
					justGetData(rect, ia[0]*8, ia[0]*0.2);//会无限运行
				}
			}).start();
		}
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		stage.setScene(scene);
		stage.show();
	}
	
	
	private void justGetData(Rectangle rect, double x_axis, double t_) {
		double x = 0;
		double y = 0;
		double t = 0;
		double step = 0.2;
		double[] s = {0,0,t_,0.2};
		while(true) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					s[1] = Math.sin(s[2] + s[0] * a) * 50;
					rect.setLayoutY(s[1] + 100);
					rect.setLayoutX(x_axis + 100);
					s[2] += step;
					System.out.println("y:" + (s[1]+ 100 ) + "t:" + s[2] + "axis_:" + s[1]);
				}
			});
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 利用相位的变化
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月3日
	 * @Package com.bj58.im.client.mediaTest
	 * @return void
	 */
	private void seeTheWave(Stage primaryStage) {
		
		List<Point2D> li = new ArrayList<>();
		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		new Thread(new Runnable() {
			@Override
			public void run() {
				double t = 0;
				double stepT = 0.2;//s
				double stepX = 0.2;
				double y = 0;
				double x = 0;
				int count = 0;
				double fai = 0;
				while(true){
					x = 0;
					count = 0;
					li.clear();
					t += stepT;
					while(true) {
						if(count++ > 2000 || x > 600) {
							break;
						}
						fai = a * x + b * t;
						y = Math.sin(fai);
						li.add(new Point2D(x, y));
						x += stepX; 
					}
					//画图
					double min = 10000;
					double max = -10000;
					for(Point2D p : li) {
						if(p.getY() > max) {
							max = p.getY();
						}
						if(p.getY() < min) {
							min = p.getY();
						}
					}
					double fanwei = max - min;
					int rate = 100;
					if(fanwei < 1) {
						rate = 10;
					}
					Path path = new Path();
					path.setLayoutX(100);
					path.setLayoutY(120);
					path.setStroke(Color.GREEN);
					path.getElements().add(new MoveTo(li.get(0).getX(), li.get(0).getY() / fanwei * rate)); 
					for(int i = 1; i < li.size(); i++) {
						path.getElements().add(new LineTo(li.get(i).getX(), li.get(i).getY() / fanwei * rate));
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							group.getChildren().clear();
							group.getChildren().add(path);
						}
					});
				}
			}
		}).start();
		//自动代码变化---就厉害了。。。---控制编译--控制文本
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
