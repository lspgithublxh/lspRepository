package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//		Point2D[] a = provideTuoRoundXuanzhuanPoint();
//		generalCubicCurve(stage, a);
		
		provideDenggaoPoint(stage);
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
	
	private void provideDenggaoPoint(Stage stage) {
		//0.数据点的生成
		//1.数据点重排  
		//2.一圈一圈的拟合
		//3.曲线不重合----曲线碰撞检测---而重新拟合--直到没有碰撞---不容易---所以剔除这种错误数据点  ： 或者增大间隔
		List<String> sl = generateScoreList();
		Map<Integer, List<Point2D>> pMap = scoreSort(sl, 1);
		
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		double step = 2 * Math.PI / 360;
		for(float theta = 0; theta < 2 * Math.PI * 10; theta += step) {//弧度制度
			li.add(new Point2D(theta * Math.cos(theta)  + 100 , theta * Math.sin(theta) )); 
		}
		generalMultiCubicCurve(stage, pMap);
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
	 * 分值获取
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月17日
	 * @Package com.bj58.im.client.mediaTest
	 * @return List<String>
	 */
	private List<String> generateScoreList() {
		List<String> sl = new ArrayList<>();
		double a = 100;
		for(int i = 0; i < 5; i++) {
			double b = a / 2;
			Point2D[] points = provideTuoRoundXuanzhuanPoint2(a, b, 30);
			generalCubicCurve(points);
			a = a - (i + 1) * 10;
			for(Point2D p : points) {
				String sc = p.getX() + "," + p.getY() + ";" + i + 10;
				sl.add(sc);
			}
		}
		return sl;
	}
	
	/**
	 * 分值筛选-排序-重排 - 拟合 ： 碰撞检测而重新拟合
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月17日
	 * @Package com.bj58.im.client.mediaTest
	 * @return Map<String,Point2D>
	 */
	private Map<Integer, List<Point2D>> scoreSort(List<String> pvlist, double score_distance) {
		//1.分值 圈
		Map<Integer, List<Point2D>> map = new HashMap<>();
		for(String pv : pvlist) {
 			String[] p_v = pv.split(";");
			Integer score = Integer.valueOf(p_v[1]);
			boolean containK = false;
			for(Integer key : map.keySet()) {
				if(Math.abs(key - score) <= score_distance) {
					containK = true;
					String[] xy = p_v[0].split(",");
					map.get(key).add(new Point2D(Double.valueOf(xy[0]), Double.valueOf(xy[1])));
					break;//首次包含就退出
				}
			}
			if(!containK) {
				List<Point2D> lis = new ArrayList<>();
				String[] xy = p_v[0].split(",");
				lis.add(new Point2D(Double.valueOf(xy[0]), Double.valueOf(xy[1])));
				map.put(score, lis);
			}
		}
		//2.对每圈进行处理
		for(Integer k : map.keySet()) {
			List<Point2D> vaList = map.get(k);
//			generalCubicCurve(vaList.toArray(new Point2D[] {}));//正常
			Collections.sort(vaList, new Comparator<Point2D>() {
				@Override
				public int compare(Point2D arg0, Point2D arg1) {
					if(arg0.getX() < arg0.getX()) {
						return -1;
					}else if(arg0.getX() > arg0.getX()) {
						return 1;
					}
					return 0;
				}
			});
			//排好后重排
			Point2D old1 = null;
			List<Point2D> uppe = new ArrayList<>();
			List<Point2D> down = new ArrayList<>();
			boolean hasDive = false;
			Point2D first = vaList.get(0);
			uppe.add(first);
			for(int i = 1; i < vaList.size(); i++) {
				Point2D now = vaList.get(i);
				//分开
				if(!hasDive && uppe.get(uppe.size() - 1).getY() != now.getY()) {
					hasDive = true;
					down.add(now);
					continue;
				}
				if(!hasDive) {
					uppe.add(now);
					continue;
				}
				//开始比较
				Point2D uJ = uppe.get(uppe.size() - 1);
				Point2D dJ = down.get(down.size() - 1);
				double distanceU = Math.sqrt(Math.pow((uJ.getX() - now.getX()), 2) + Math.pow(uJ.getY() - now.getY(), 2));
				double distanceD = Math.sqrt(Math.pow((dJ.getX() - now.getX()), 2) + Math.pow(dJ.getY() - now.getY(), 2));
				if(distanceD < distanceU) {
					down.add(now);
				}else {
					uppe.add(now);
				}
			}
			//组合
			List<Point2D> nlist = new ArrayList<>();
			nlist.addAll(uppe);
			for(int i = down.size() - 1; i >=0; i--) {
				nlist.add(down.get(i));
			}
			vaList.clear();
			vaList.addAll(nlist);
			
		}
		//3元祖
		return map;
	}
	
	private Point2D[] provideTuoRoundXuanzhuanPoint2(double a, double b, double angle) {
		List<Point2D> li = new ArrayList<>();
		int k = 0;
		for(float i = 0; i <= 200; i += 0.2f) {
			double y = b *  Math.sqrt(1 - (i - 100)*(i - 100) / (a * a));
			double r = Math.sqrt(i * i + y * y);
			double theta = Math.atan2(- y , i);///y,x
			double theta2 = theta - angle / 360.0;//绕x轴旋转30度
			if("NaN".equals(theta + "")) continue;
			System.out.println(a + "," + b + "," + theta + ", " + theta2);
			double xN = r * Math.cos(theta2);
			double yN = r * Math.sin(theta2);
			li.add(new Point2D(xN, yN)); 
		}
		for(float i = 200; i >= 0; i -= 0.2f) {
			double y = b *  Math.sqrt(1 - (i - 100)*(i - 100) / (a * a));
			double r = Math.sqrt(i * i + y * y);
			double theta = Math.atan2(y , i);///y,x
			double theta2 = theta - angle / 360.0;
			if("NaN".equals(theta + "")) continue;
			System.out.println(a + "," + b + "," + theta + ", " + theta2);
			double xN = r * Math.cos(theta2);
			double yN = r * Math.sin(theta2);
			li.add(new Point2D(xN, yN)); 
		}
		return li.toArray(new Point2D[] {});
	}
	
	
	/**
	 * 倾斜的椭圆
	 * 点的旋转
	 * 分值的拟合---使得热力图可以实现
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
	
	private void generalMultiCubicCurve(Stage stage, Map<Integer, List<Point2D>> pMap) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		for(Integer score : pMap.keySet()) {
			Point2D[] a = pMap.get(score).toArray(new Point2D[] {});
			Path path = new Path();
			path.setLayoutX(10);
			path.setLayoutY(120);
			path.setStroke(Color.rgb(100, 10, score * 10 % 256));
			path.getElements().add(new MoveTo(a[0].getX(), a[0].getY()));
			for(int i = 0; i < a.length - 1; i++) {
				double centerX = (a[i].getX() + a[i+1].getX())/2;
				path.getElements().add(new CubicCurveTo(centerX, a[i].getY(),
						centerX, a[i+1].getY(), a[i+1].getX(), a[i+1].getY()));
			}
			group.getChildren().add(path);
			
		}
		stage.setScene(scene);
		stage.show();
	}

	
	private void generalCubicCurve(Point2D[] a) {
		if (a.length == 0) return;
		Stage stage = new Stage();
		stage.setTitle("xxx");
		stage.setWidth(500);
		stage.setHeight(400);
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

	Stage stage = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		test(primaryStage);
	}
}
