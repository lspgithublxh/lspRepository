package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public class SameFieldValTest extends Application{

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		Map<Integer, List<Point2D>> pMap = new HashMap<>();
		for(int i = 0; i < 10; i++) {
			List<Point2D> po = getFieldPoint(i * 10, 1);
			pMap.put(i * 10, po);
		}
		for(int i = 0; i < 20; i++) {
			List<Point2D> po = getFieldPoint(1, i * 10);
			pMap.put(i * 10 + 220, po);
		}
		generalMultiCubicCurve(arg0, pMap);
	}
	
	private void generalMultiCubicCurve(Stage stage, Map<Integer, List<Point2D>> pMap) {
		Group group = new Group();
		Scene scene = new Scene(group, 600, 500, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		for(Integer score : pMap.keySet()) {
			Point2D[] a = pMap.get(score).toArray(new Point2D[] {});
			Path path = new Path();
			path.setLayoutX(100);
			path.setLayoutY(120);
			path.setStroke(Color.rgb(100, 10, score * 10 % 256));
			path.getElements().add(new MoveTo(a[0].getX(), a[0].getY()));
			for(int i = 0; i < a.length - 1; i++) {
				double centerX = (a[i].getX() + a[i+1].getX())/2;
				path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY()));
				if(i % 50 == 0) {
					path.getElements().add(new LineTo(a[i].getX() - (a[i+1].getX() - a[i].getX()) * 30, a[i+1].getY()));
					path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY()));
					path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY() - (a[i+1].getY() - a[i].getY()) * 30));
					path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY()));
				}
//				path.getElements().add(new CubicCurveTo(centerX, a[i].getY(),
//						centerX, a[i+1].getY(), a[i+1].getX(), a[i+1].getY()));//直线也可以
			}
			group.getChildren().add(path);
			
		}
		stage.setScene(scene);
		stage.show();
	}
	
	int N1 = 200;
	int N2 = 200;
	
	private List<Point2D> getFieldPoint(double startX, double startY){
		List<Point2D> list = new ArrayList<Point2D>();
		float step = 0.2f;
		int count = 0;
		while(true) {
			Point2D p = new Point2D(startX, startY);
			list.add(p);
			double x_k = startX * (1 - startX/N1 - 1.5 * startY/N2);
			double y_k = startY * (1 - 0.2 * startX/N1 - startY/N2);
//			double k = y_k / x_k;//tan theta  表示颜色值：长度
			double r = Math.sqrt(x_k * x_k + y_k * y_k);//表示颜色值：长度
			if(r == 0) {
				r = 0.001;
			}
			if(x_k == 0) {
				x_k = 0.001;
			}
			if(y_k == 0) {
				y_k = 0.001;
			}
			double x_k_danwei = x_k / r;
			double y_k_danwei = y_k / r;
			startX = startX + x_k_danwei * step;
			startY = startY + y_k_danwei * step;
			if(startX > 400 || startY > 400 || count++ > 2000) {
				break;
			}
			System.out.println(startX + ", " + startY + "," + x_k_danwei + ", " + x_k_danwei);
		}
		
		return list;
		
	}

}
