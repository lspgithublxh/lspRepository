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

/**
 * 曲线拟合，要求有一种曲线，根据3个点，进行，且同侧两个点和另外一个点拟合时曲线和另一个点拟合的相同:这种曲线不存在
 * 贝塞尔曲线  + 数据点  直接画图
 * 贝塞尔曲线  + 拉格朗日多项式 扩展之后的数据点 再画图
 * Point3D数据点
 * @ClassName:CurveNiheTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月17日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class CurveNiheTest extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	private void test(Stage stage) {
//		Point2D[] a = providePoint();
//		Point2D[] a = provideSinPoint();
//		Point2D[] a = provideThrowThingPoint();
//		Point2D[] a = provideEPoint();
//		Point2D[] a = provideRoundPoint();
//		Point2D[] a = provideLuoxuanPoint();
//		Point2D[] a = provideRectengeFieldPoint();
//		Point2D[] a = provideTuoRoundPoint();
		Point2D[] a = provideTuoRoundXuanzhuanPoint();
		generalCubicCurve(stage, a);
	}

	private Point2D[] providePoint() {
		Point2D point = new Point2D(0, 0);
		Point2D point1 = new Point2D(50, 100);
		Point2D point2 = new Point2D(100, 80);
		Point2D point3 = new Point2D(150, 60);
		Point2D point4 = new Point2D(200, 100);
		Point2D point5 = new Point2D(250, 10);
		Point2D[] a = new Point2D[] {point, point1, point2, point3, point4, point5};
		return a;
	}
	
	private Point2D[] provideRectengeFieldPoint() {
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		li.add(new Point2D(0 , 0 )); 
		for(int i = 0; i < 3; i++) {//弧度制度
			li.add(new Point2D(i  * 50 + 50 , 0 )); 
		}
		for(int i = 0; i < 3; i++) {//弧度制度
			li.add(new Point2D(150 , i * 50 + 50 )); 
		}
		for(int i = 0; i < 3; i++) {//弧度制度
			li.add(new Point2D(150 - i * 50 - 50 , 150 )); 
		}
		for(int i = 0; i < 3; i++) {//弧度制度
			li.add(new Point2D(0 , 150 - i * 50 - 50 )); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private Point2D[] provideDenggaoPoint() {
		//0.数据点的生成
		//1.数据点重排  
		//2.一圈一圈的拟合
		//3.曲线不重合----曲线碰撞检测---而重新拟合--直到没有碰撞---不容易---所以剔除这种错误数据点  ： 或者增大间隔
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		double step = 2 * Math.PI / 360;
		for(float theta = 0; theta < 2 * Math.PI * 10; theta += step) {//弧度制度
			li.add(new Point2D(theta * Math.cos(theta)  + 100 , theta * Math.sin(theta) )); 
		}
		System.out.println("end");
		return li.toArray(new Point2D[] {});
	}
	
	/**
	 * 螺旋线
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月17日
	 * @Package com.bj58.im.client.mediaTest
	 * @return Point2D[]
	 */
	private Point2D[] provideLuoxuanPoint() {
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		double step = 2 * Math.PI / 360;
		for(float theta = 0; theta < 2 * Math.PI * 10; theta += step) {//弧度制度
			li.add(new Point2D(theta * Math.cos(theta)  + 100 , theta * Math.sin(theta) )); 
		}
		System.out.println("end");
		return li.toArray(new Point2D[] {});
	}
	
	/**
	 * 倾斜的椭圆
	 * 点的旋转
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月17日
	 * @Package com.bj58.im.client.mediaTest
	 * @return Point2D[]
	 */
	private Point2D[] provideTuoRoundXuanzhuanPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i <= 200; i += 0.2f) {
			double y = 50 *  Math.sqrt(1 - (i - 100)*(i - 100) / (100 * 100));
			double r = Math.sqrt(i * i + y * y);
			double theta = Math.atan2(- y , i);///y,x
			double theta2 = theta - 30 / 360.0;//绕x轴旋转30度
			System.out.println(theta + ", " + theta2);
			double xN = r * Math.cos(theta2);
			double yN = r * Math.sin(theta2);
			li.add(new Point2D(xN, yN)); 
		}
		for(float i = 200; i >= 0; i -= 0.2f) {
			double y = 50 *  Math.sqrt(1 - (i - 100)*(i - 100) / (100 * 100));
			double r = Math.sqrt(i * i + y * y);
			double theta = Math.atan2(y , i);///y,x
			double theta2 = theta - 30 / 360.0;
			double xN = r * Math.cos(theta2);
			double yN = r * Math.sin(theta2);
			li.add(new Point2D(xN, yN)); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private Point2D[] provideTuoRoundPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i <= 200; i += 0.2f) {
			li.add(new Point2D(i, - 50 *  Math.sqrt(1 - (i - 100)*(i - 100) / (100 * 100) ))); 
		}
		for(float i = 200; i >= 0; i -= 0.2f) {
			li.add(new Point2D(i, 50 *  Math.sqrt(1 - (i - 100)*(i - 100) / (100 * 100) ))); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private Point2D[] provideRoundPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i <= 200; i += 0.2f) {
			li.add(new Point2D(i, - Math.sqrt(100 * 100 - (i - 100)*(i - 100) ))); 
		}
		for(float i = 200; i >= 0; i -= 0.2f) {
			li.add(new Point2D(i, Math.sqrt(100 * 100 - (i - 100)*(i - 100) ))); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	/**
	 * [a,b]之间取1000个点，1000个数据，用来拟合
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月17日
	 * @Package com.bj58.im.client.mediaTest
	 * @return Point2D[]
	 */
	private Point2D[] provideEPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i < 200; i += 0.2f) {
			li.add(new Point2D(i, - Math.pow(Math.E, i / 40))); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private Point2D[] provideThrowThingPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i < 200; i += 0.2f) {
			li.add(new Point2D(i, i * (i - 100) / 100)); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private Point2D[] provideSinPoint() {
		Point2D[] a = new Point2D[1000];
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i < 200; i += 0.2f) {
			li.add(new Point2D(i, Math.sin(i/10) * 50)); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	private void generalCubicCurve(Stage stage, Point2D[] a) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		Path path = new Path();
		path.setLayoutX(10);
		path.setLayoutY(120);
		path.getElements().add(new MoveTo(a[0].getX(), a[0].getY()));
		for(int i = 0; i < a.length - 1; i++) {
			double centerX = (a[i].getX() + a[i+1].getX())/2;
			path.getElements().add(new CubicCurveTo(centerX, a[i].getY(),
					centerX, a[i+1].getY(), a[i+1].getX(), a[i+1].getY()));
		}
		group.getChildren().add(path);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		test(primaryStage);
	}
}
