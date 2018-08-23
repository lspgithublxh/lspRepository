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

public class SameFieldValTest2_hezuo extends Application{

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		double[] a1A = {0.2,1.2,0.3,1.5};
		double[] b1B = {0.4,0.5,1.2,1.2};//第一组最完美
		for(int j = 0; j < 4; j++) {
			Stage s = new Stage();
			s.setWidth(600);
			s.setHeight(600);
			a1 = a1A[j];
			b1 = b1B[j];
			Map<Integer, List<Point2D>> pMap = new HashMap<>();
			for(int i = 0; i < 10; i++) {
				List<Point2D> po = getFieldPoint(i * 10, 1);
				pMap.put(i * 10, po);
			}
			for(int i = 0; i < 20; i++) {
				List<Point2D> po = getFieldPoint(1, i * 10);
				pMap.put(i * 10 + 220, po);
			}
			for(int i = 0; i < 20; i++) {
				List<Point2D> po = getFieldPoint(i*10, i * 10);
				pMap.put(i * 10 + 420, po);
			}
			generalMultiCubicCurve(s, pMap);
		}
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
					double deltX = (a[i+1].getX() - a[i].getX()) * 30;
					deltX = Math.abs(deltX) < 1E-4 ? deltX > 0 ? 5 : -5: deltX;
					double x_st = a[i].getX() - deltX;
					x_st = x_st < 0 ? 0 : x_st;
					double deltY = (a[i+1].getY() - a[i].getY()) * 30;
					deltY = Math.abs(deltY) < 1E-4 ? deltY > 0 ? 5 : -5: deltY;
					double y_st = a[i+1].getY() - deltY;
					y_st = y_st < 0 ? 0 : y_st;
					path.getElements().add(new LineTo(x_st, a[i+1].getY()));//也可以用固定长度的方式：
					path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY()));
					path.getElements().add(new LineTo(a[i+1].getX(), y_st));
					path.getElements().add(new LineTo(a[i+1].getX(), a[i+1].getY()));
				}
//				path.getElements().add(new CubicCurveTo(centerX, a[i].getY(),
//						centerX, a[i+1].getY(), a[i+1].getX(), a[i+1].getY()));//直线也可以
			}
			group.getChildren().add(path);
			
		}
		//画直线：
		double[][] line = {{N1, 0, 0, N2/a1},{0,N2, N1/b1,0}};
		Color[] c = {Color.RED, Color.GREENYELLOW};
		int i = 0;
		for(double[] d : line) {
			Path path = getAPath(c[i++]);
			path.getElements().add(new MoveTo(d[0],d[1]));
			path.getElements().add(new LineTo(d[2],d[3]));
			group.getChildren().add(path);
		}
		
		stage.setScene(scene);
		stage.show();
	}

	private Path getAPath(Color color) {
		Path path = new Path();
		path.setLayoutX(100);
		path.setLayoutY(120);
		path.setStroke(color);
		return path;
	}
	
	int N1 = 200;
	int N2 = 200;
	double a1 = 1.5;
	double b1 = 0.2;
	
	private List<Point2D> getFieldPoint(double startX, double startY){
		List<Point2D> list = new ArrayList<Point2D>();
		float step = 0.2f;
		int count = 0;
		while(true) {
			Point2D p = new Point2D(startX, startY);
			list.add(p);
			double x_k = startX * (1 - startX/N1 - a1 * startY/N2);
			double y_k = startY * (1 - b1 * startX/N1 - startY/N2);
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
			if(startX > 400 || startX < 1E-10 || startY > 400 || startY < 1E-10 || count++ > 2000) {//TODO增加额外制约条件--最小值
				break;
			}
			System.out.println(startX + ", " + startY + "," + x_k_danwei + ", " + x_k_danwei);
		}
		
		return list;
		
	}

}
