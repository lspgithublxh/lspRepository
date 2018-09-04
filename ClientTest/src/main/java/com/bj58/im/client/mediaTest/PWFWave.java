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
//		pointMove(primaryStage);
//		pointMoveXZongWave(primaryStage);
//		pointMoveLuoxuan(primaryStage);
		pointMoveMultipleZongWave(primaryStage);
	}
	
	double a = 0.1;
	double b = 1;
	
	private void pointMoveMultipleZongWave(Stage stage) {
		Group group = new Group();
		Path p = new Path();
		double startX = 300;
		double startY = 300;
		p.getElements().add(new MoveTo(startX,0));
		p.getElements().add(new LineTo(startX,startY));
		p.getElements().add(new LineTo(0,startY));
		p.setStroke(Color.rgb(100, 10, 10));
		p.setStrokeWidth(0.2);
		group.getChildren().add(p);
		int count = 30;
		for(int i = -48; i < 48; i += 3) {
			
			int[] ia = {i, i};
			double theta = ia[0] / (double)count;
			for(int j = -10; j < 10; j++) {
				Rectangle rect  = new Rectangle(5, 5, Color.RED);
//				rect.setLayoutX(startX);
//				rect.setLayoutY(startY);
				group.getChildren().add(rect);
				
				ia[1] = j;
				
				double deltX = 20 * j * Math.cos(theta);
				double deltY = 20 * j * Math.sin(theta);
				double x = startX + deltX;//ia[0] * 10 + 
				double y = startY + deltY;
				double[] xy = {x, y};
				new Thread(new Runnable() {
					@Override
					public void run() {//ia[0] * 10 + 100, 100
						justGetDataMultipleXZW(rect, xy[0], xy[1], ia[0] / (double)count,  ia[1]*0.2);//会无限运行
					}
				}).start();
			}
		}
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		stage.setScene(scene);
		stage.show();
	}
	
	private void pointMoveXZongWave(Stage stage) {
		Group group = new Group();
		for(int i = 0; i < 30; i++) {
			Rectangle rect  = new Rectangle(5, 5, Color.RED);
			rect.setLayoutX(100);
			rect.setLayoutY(100);
			group.getChildren().add(rect);
			int[] ia = {i, i};
			new Thread(new Runnable() {
				@Override
				public void run() {
					justGetDataXZW(rect, ia[0]*20 + 20, ia[0]*0.2);//会无限运行
				}
			}).start();
		}
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		stage.setScene(scene);
		stage.show();
	}
	
	private void pointMoveLuoxuan(Stage stage) {
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
					justGetDataLuoxuan(rect, ia[0]*8, ia[0]*0.2);//会无限运行
				}

			}).start();
		}
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		stage.setScene(scene);
		stage.show();
	}
	
	private void justGetDataLuoxuan(Rectangle rect, int x_axis, double t_) {
		double[] s = {0,0,t_,0.2};
		double step = 0.2;
		while(true) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					double theta = s[2];
					double p = theta;
					s[0] = p * Math.cos(p);
					s[1] = p * Math.sin(p);
					rect.setLayoutX(s[0] + 200);
					rect.setLayoutY(s[1] + 200);
//					s[1] = Math.sin(s[2] + s[0] * a) * 10;
//					rect.setLayoutX(x_axis + s[1]);
					s[2] += step;
					System.out.println("t:" + s[2] + "axis_:" + s[1]);
				}
			});
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
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
	
	private void justGetDataMultipleXZW(Rectangle rect, double x, double y, double theta, double t_) {
		double t = 0;
		double step = 0.2;
		double[] s = {x,y,t_, theta,0.2};
		rect.setLayoutY(100);
		while(true) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.out.println(Math.cos(theta) +"   "+ s[2]);
					double r = Math.sin(s[2]) * 10;
					double deltaX = r * Math.cos(theta);
					double deltaY = r * Math.sin(theta);
					rect.setLayoutX(x + deltaX);
					rect.setLayoutY(y + deltaY);
					s[2] += step;
//					System.out.println("t:" + s[2] + "axis_:" + s[1]);
				}
			});
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	private void justGetDataXZW(Rectangle rect, double x_axis, double t_) {
		double x = 0;
		double y = 0;
		double t = 0;
		double step = 0.2;
		double[] s = {0,0,t_,0.2};
		rect.setLayoutY(100);
		while(true) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					s[1] = Math.sin(s[2] + s[0] * a) * 10;
					rect.setLayoutX(x_axis + s[1]);
					s[2] += step;
					System.out.println("t:" + s[2] + "axis_:" + s[1]);
				}
			});
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
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
					rect.setLayoutX(x_axis + 100);//震动
//					rect.setLayoutX(x_axis + s[2] * 10 + 100);//平面螺旋运动
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
