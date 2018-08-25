package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class ChaFEQtion extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
//		List<Point2D>  list = getOneJPoints();
		List<Point2D>  list = getTwoJPoints();
		showGuiji(list, arg0);
	}
	
	
	
	private List<Point2D> getTwoJPoints(){//迭代方式获取
		List<Point2D> list = new ArrayList<Point2D>();
		double startX = 0;
		double y = 0;
		double y_1 = 0;
		double step = 10;
		int count = 0;
//		list.add(new Point2D(startX, y_1));
//		list.add(new Point2D(startX, y));
		while(true) {
			double temp = y;
			y = b - c * y_1 - a * y;
			y_1 = temp;
			list.add(new Point2D(startX, y));
			startX += step;
			if(count++ > 2000 || y > 600 || startX > 600) {//如果快速收敛，那么看不到线
				break;
			}
			System.out.println(startX + ", " + y);
		}
		return list;
		
	}
	
	double b = 10;
	double a = 0.2;
	double c = 0.3;

	private List<Point2D> getOneJPoints(){//迭代方式获取
		List<Point2D> list = new ArrayList<Point2D>();
		double startX = 0;
		double y = 0;
		double step = 10;
		int count = 0;
		while(true) {
			y = b - a * y;
			list.add(new Point2D(startX, y));
			startX += step;
			if(count++ > 2000 || y > 600 || startX > 600) {//如果快速收敛，那么看不到线
				break;
			}
			System.out.println(startX + ", " + y);
		}
		return list;
		
	}
	
	public static void showGuiji(List<Point2D> plist, Stage stage) {
		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Path path = new Path();
		path.setLayoutX(120);//决定原点起点，和x,y无关
		path.setLayoutY(120);
		path.setStroke(Color.GREEN);
		//
		double min = 10000;
		double max = -10000;
		for(Point2D p : plist) {
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
		path.getElements().add(new MoveTo(plist.get(0).getX(), plist.get(0).getY()  / fanwei * rate)); 
		for(int i = 1; i < plist.size() - 1; i++) {
			System.out.println(plist.get(i).getX() + ", " + plist.get(i).getY() * rate);
			double centerX = (plist.get(i-1).getX() + plist.get(i).getX()) /2;
//			System.out.println(plist.get(i).getX() + "," + plist.get(i).getY() / fanwei * rate);
//			path.getElements().add(new LineTo(plist.get(i).getX(), plist.get(i).getY()  / fanwei* rate));
			path.getElements().add(new CubicCurveTo(centerX, plist.get(i-1).getY()/ fanwei* rate,
						centerX, plist.get(i).getY()/ fanwei* rate, plist.get(i).getX(), plist.get(i).getY()/ fanwei* rate));
		}
		group.getChildren().add(path);
		stage.setScene(scene);
		stage.show();
	}
}
