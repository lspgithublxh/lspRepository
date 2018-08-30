package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * 情形1: Ez/Ex = f(x,y)类型  可以知道所有点    。。。。不需要其他约束
 * 情形2:  a * Ez/Ey + b * Ez/Ex = f(x,y) 类型，不需要另一个即：c *Ez/Ey + d * Ez/Ex = g(x,y).....形成A*P = F 类型 。。
 * 		  而需要一个横轴上的所有z值计算约束：比如z = f(x) 且y = 0    （初始点的值，而不是关系）这样就可以。。。。且可以相下求：即向y轴的负半轴计算
 * 
 * @ClassName:PWFEqtion
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月29日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class PWFEqtion extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		List<Item> gx = new ArrayList<>();
		gx.add(new Item(1, 1, 10, 1));
		List<Item> gy = new ArrayList<>();
		gy.add(new Item(1, 1, 10, 1));
		IComputerable z_fx = new IComputerable() {
			@Override
			public double compute(double y, double t) {
				double z = Math.sin(Math.sqrt(y * y + t * t));
				return z;
			}
		};
		IComputerable changshux = new IComputerable() {
			@Override
			public double compute(double y, double t) {
//				double z = 0.5 * Math.sin(Math.sqrt(y * y + t * t));
//				System.out.println("z:" + z);
//				return z;
				return 0.05;
			}
		};
		List<PointVal> lis = oneG(gx, gy, z_fx, changshux);
		showPointVal(arg0, lis);
	}

	static double step = 2;//0.2
	
	/**
	 * 一阶偏微分方程， 一圈一圈往外算，deltX deltY = 固定值的所有点;;;; 根据两个点的类型，而计算第三个点：这种方式在第一象限 只有2种类型
	 * 只能 取  右边 和 上边  作为 导数值 的计算
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月28日
	 * @Package com.bj58.im.client.mediaTest
	 * @return List<Point2D>
	 */
	private static List<PointVal> oneG(List<Item> gx, List<Item> gy, IComputerable z_fx, IComputerable changshux){
		double x = 0;
		double y = 0;
		int count = 0;
		
		double z = 0;
		List<PointVal> rs = new ArrayList<PointVal>();
		List<PointVal> conditionLine = new ArrayList<PointVal>();
		List<PointVal> temp = new ArrayList<PointVal>();
		while(true) {
			if( x > 600) {
				System.out.println("break" + x + "," + y);
				break;
			}
			z = z_fx.compute(x);
			conditionLine.add(new PointVal(x, y, z));
			x += step;
//			System.out.println("x=0, " + x + "," + y);
		}
		rs.addAll(conditionLine);//TODO 
		count = 0;
		
		while(true) {
			x = 0;
			y += step;
			if( y > 600) {//count++ > 200 ||
//				System.out.println("break" + x + "," + y);
				break;
			}
			System.out.println(conditionLine.size() + "beforesize");
			//第二行开始，都由上一行计算出来
			for(int i = 0; i < conditionLine.size() - 1; i++) {
//				System.out.println("y > 0, " + x + "," + y);
				double Ez_Ex = (conditionLine.get(i + 1).getZ() - conditionLine.get(i).getZ()) / step;
				double Ez_Ey = (changshux.compute(conditionLine.get(i).getY(), conditionLine.get(i).getX()) - Ez_Ex * gx.get(0).getXishu()) / gy.get(0).getXishu();
				z = Ez_Ey * step + conditionLine.get(i).getZ();
				temp.add(new PointVal(x, y, z));
				x += step;
			}
			temp.add(new PointVal(x, y, z));
			System.out.println(conditionLine.size() + "aftersize");
			rs.addAll(temp);
			conditionLine.clear();
			conditionLine.addAll(temp);
			temp.clear();
		}
		//计算初始2个点
//		double v1_0 = v0_0 + gx.get(0).getVal() * step;//定义上计算
//		//已知0处导数，计算 其他地方的值
//		double yp0_0 = (changshux.compute(y, x) - gx.get(0).getVal() * gx.get(0).getXishu()) / gy.get(0).getXishu();
//		double v0_1 = yp0_0 * step  + v0_0;
//		int distance = 2;
//		while(true) {
//			if(count++ > 20000 || x > 600 || y > 600) {
//				System.out.println("break" + x + "," + y);
//				break;
//			}
//			//deltY = distance
//			for(int deltX = 0; deltX <= distance; deltX++) {
//				x = step * deltX;
//				y = step * distance;
//				
//			}
//			//deltX = distance
//			for(int deltY = 0; deltY <= distance; deltY++) {
//				
//			}
//		}
		return rs;
		
	}
	
	private void showPointVal(Stage stage, List<PointVal> list) {
		double min = 10000;
		double max = -10000;
		for(PointVal p : list) {
			if(p.getZ() > max) {
				max = p.getZ();
			}
			if(p.getZ() < min) {
				min = p.getZ();
			}
//			System.out.println(p.getZ());
		}
		double fanwei = max - min;
		List<Color> colors = ColorDitu.getColorDidu();
		System.out.println(max + ", " + min + " , colors.size=" + colors.size());

		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		int size = colors.size() - 1;
		for(PointVal p : list) {
			Rectangle rec = new Rectangle(p.getX() + 10, p.getY() + 100, step, step);//step
			double s = (p.getZ() - min) * size;
			s = s >= 768 ? 766 : s;
//			double s = (p.getZ() - min) % size ;
//			System.out.println("color : " + s);
			rec.setFill(colors.get((int) Math.floor(s)));
			group.getChildren().add(rec);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	private Point2D getPointByPoints(Point2D a, Point2D b, int type) {
		if(type == 1) {//对顶点 计算 相交点
			//
			
		}else if(type == 2) {//相邻点计算另一条边上的点
			//
			
		}
		return b;
		
	}
	
}
