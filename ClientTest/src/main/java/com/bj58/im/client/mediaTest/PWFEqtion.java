package com.bj58.im.client.mediaTest;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

/**
 * 情形1: Ez/Ex = f(x,y)类型  可以知道所有点    。。。。不需要其他约束
 * 情形2:  a * Ez/Ey + b * Ez/Ex = f(x,y) 类型，不需要另一个即：c *Ez/Ey + d * Ez/Ex = g(x,y).....形成A*P = F 类型 。。
 * 		  而需要一个横轴上的所有z值计算约束：比如z = f(x) 且y = 0    这样就可以。。。。且可以相下求：即向y轴的负半轴计算
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
		
	}

	/**
	 * 一阶偏微分方程， 一圈一圈往外算，deltX deltY = 固定值的所有点;;;; 根据两个点的类型，而计算第三个点：这种方式在第一象限 只有2种类型
	 * 只能 取  右边 和 上边  作为 导数值 的计算
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月28日
	 * @Package com.bj58.im.client.mediaTest
	 * @return List<Point2D>
	 */
	private static List<Point2D> oneG(List<Item> gx, List<Item> gy,IComputerable changshux){
		double x = 0;
		double y = 0;
		int count = 0;
		double step = 0.2;
		double v0_0 = 0;
		//计算初始2个点
		double v1_0 = v0_0 + gx.get(0).getVal() * step;//定义上计算
		//已知0处导数，计算 其他地方的值
		double yp0_0 = (changshux.compute(y, x) - gx.get(0).getVal() * gx.get(0).getXishu()) / gy.get(0).getXishu();
		double v0_1 = yp0_0 * step  + v0_0;
		int distance = 2;
		while(true) {
			if(count++ > 20000 || x > 600 || y > 600) {
				System.out.println("break" + x + "," + y);
				break;
			}
			//deltY = distance
			for(int deltX = 0; deltX <= distance; deltX++) {
				x = step * deltX;
				y = step * distance;
				
			}
			//deltX = distance
			for(int deltY = 0; deltY <= distance; deltY++) {
				
			}
		}
		return null;
		
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
