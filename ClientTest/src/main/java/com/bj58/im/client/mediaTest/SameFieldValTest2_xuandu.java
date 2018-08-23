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

/**
 * 向量场的旋度场， 标量场的梯度场
 * @ClassName:SameFieldValTest2_xuandu
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月23日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class SameFieldValTest2_xuandu extends Application{

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		double[] a1A = {0.2,1.2,0.3,1.5};
		double[] b1B = {0.4,0.5,1.2,1.2};//第一组最完美
		for(int j = 3; j < 4; j++) {
			Stage s = new Stage();
			s.setWidth(800);
			s.setHeight(600);
			a1 = a1A[j];
			b1 = b1B[j];
			generalDrawJie(arg0, getZvalueOfBoardVectorFieldXuandu(j * 10 + 1, 1), getColorDidu());
//			Map<Integer, List<Point2D>> pMap = new HashMap<>();
//			for(int i = 0; i < 10; i++) {
//				List<Point2D> po = getFieldPoint(i * 10, 1);
//				pMap.put(i * 10, po);
//			}
//			for(int i = 0; i < 20; i++) {
//				List<Point2D> po = getFieldPoint(1, i * 10);
//				pMap.put(i * 10 + 220, po);
//			}
//			for(int i = 0; i < 20; i++) {
//				List<Point2D> po = getFieldPoint(i*10, i * 10);
//				pMap.put(i * 10 + 420, po);
//			}
//			generalMultiCubicCurve(s, pMap);
		}
	}
	
	private List<Color> getColorDidu(){
		List<Color> colorList = new ArrayList<>();
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(i, 0, 0);
			colorList.add(co);
		}
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(255, 0, 255);
			colorList.add(co);
		}
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(255, 255, 0);
			colorList.add(co);
		}
		return colorList;
	}
	
	private List<PointVal> getZvalueOfBoardVectorFieldXuandu(double startX, double startY){
		List<PointVal> valList = new ArrayList<>();
		double x = startX;
		for(;x < 600; x+= 1) {//值在递增
			double y = startY;
			for(;y < 600; y+= 1) {
				double z = -a1 / N2 * x + b1/N1 * y;//两个偏导数之和:正好是一个平面：即这个竞争的势场的旋度是个平面
				PointVal val = new PointVal(x, y, z);
				valList.add(val);
			}
		}
		return valList;
		
	}
	
	
	private void generalDrawJie(Stage stage,List<PointVal> list, List<Color> colorList) {
		double max = -10000;
		double min = 10000;
		for(PointVal p : list) {
			if(p.getZ() > max) {
				max = p.getZ();
			}
			if(p.getZ() < min) {
				min = p.getZ();
			}
		}
		double fanwei = max - min;
		int colorSize = colorList.size() - 1;
		System.out.println("fanwei:" + fanwei + "; colorSize:" + colorSize);
		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		double step = 0.2;
		
		for(PointVal p : list) {
			int index = (int) (Math.round((p.getZ() - min) / fanwei * colorSize));
			Path path = new Path();
			path.setLayoutX(100);
			path.setLayoutY(120);
			path.setStroke(colorList.get(index));
			System.out.println("x:" + p.getX() + "; y:" + p.getY() + "; z:" + p.getZ() + "color:" + colorList.get(index));
			double left = p.getX()- step;
			left = left < 0 ? p.getX() : left;
			double top = p.getY() - step;
			top = top < 0 ? p.getY() : top;
			
			path.getElements().add(new MoveTo(left, top));
			path.getElements().add(new LineTo(p.getX() + step, top));
			path.getElements().add(new LineTo(p.getX() + step, p.getY() + step));
			path.getElements().add(new LineTo(left, p.getY() + step));
			path.getElements().add(new LineTo(left, top));
			group.getChildren().add(path);
		}
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void generalMultiCubicCurve(Stage stage, Map<Integer, List<Point2D>> pMap) {
		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
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
		double[][] line = {{0, r * N2/a1, d * N1 / b1, r * N2/a1},{d * N1 / b1,0, N1/b1,r * N2/a1}};
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
	double d = 1;
	double r = 2;
	
	private List<Point2D> getFieldPoint(double startX, double startY){
		List<Point2D> list = new ArrayList<Point2D>();
		float step = 0.2f;
		int count = 0;
		while(true) {
			Point2D p = new Point2D(startX, startY);
			list.add(p);
//			double x_k = startX * (1 - startX/N1 - a1 * startY/N2);
//			double y_k = startY * (-1 + b1 * startX/N1 - startY/N2);
			double x_k = startX * (r - a1 * startY/N2);//1 - startX/N1
			double y_k = startY * (-d + b1 * startX/N1);//- startY/N2
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
			if(startX > 700 || startX < 1E-10 || startY > 400 || startY < 1E-10 || count++ > 10000) {//TODO增加额外制约条件--最小值
				break;
			}
			System.out.println(startX + ", " + startY + "," + x_k_danwei + ", " + x_k_danwei);
		}
		
		return list;
		
	}

}
