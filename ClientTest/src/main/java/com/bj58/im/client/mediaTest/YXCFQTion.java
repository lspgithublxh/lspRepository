package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.List;

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
 * 相关接口---比如并行执行任务的方法，可以看看Job-Task
 * @ClassName:YXCFQTion
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月24日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class YXCFQTion extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	private static List<Point2D> nG(List<Item> gx,IComputerable changshux){
		List<Point2D> lis = new ArrayList<>();
		int count = 0;
		double[] ngVal = new double[gx.size()];
		double[] nextngVal = new double[gx.size()];
		double x = 0;
		double y = gx.get(0).getVal();
		double step = 0.2;
		for(int i = 0; i < gx.size(); i++) {//0表示1次项， 1位置 表示2次项 ；；2次方程 只需知道2次的系数--不需要知道初始具体取值
			ngVal[i] = gx.get(i).getVal();
		}
		lis.add(new Point2D(x,y));
		while(true) {
			if(count++ > 20000 || x > 600 || y > 600) {
				System.out.println("break" + x + "," + y);
				break;
			}
			System.out.println(x + ", " + y);
			//1.定义 计算
			for(int i = 1; i < ngVal.length - 1; i++) {
				nextngVal[i-1] = ngVal[i] * step + ngVal[i-1];
			}
			//约束关系计算最后一个值---直接算最高阶导数 整体值
			double temp = changshux.compute(y, x);
			for(int i = 0; i < ngVal.length - 1; i++) {//
				temp -= ngVal[i] * gx.get(i).getXishu();
			}
			nextngVal[nextngVal.length - 2] =  temp * step / gx.get(gx.size() - 1).getXishu() + ngVal[ngVal.length - 2];
			y += ngVal[0] * step;//新y
			x += step;//新x
			lis.add(new Point2D(x,y));	
			ngVal = nextngVal;//新导数
		}
		return lis;
	}

	/**
	 * 阶数越多，逐层向上计算;知道y(t) y(t + deltT) y(t + 2* deltT)....
	 * 对于2阶方程:知道t处0-1阶的y值，那么从导数定义知道t+deltT处的0阶y值，再根据方程关系可以知道t+deltT处的1阶y值，从而定义知道y+2deltT处的0阶y值，从而关系知道 y+2deltT处的1阶y值.......
	 * 对于3阶方程：也是如此，知道t处的0-2阶y值就可以了。
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月25日
	 * @Package com.bj58.im.client.mediaTest
	 * @return List<Point2D>
	 */
	private static List<Point2D> twoG(List<Item> gx,IComputerable changshux) {
		List<Point2D> lis = new ArrayList<>();
		int count = 0;
		double startX = 0;
		double y0_0 = gx.get(0).getVal();
		double y0_1 = gx.get(0).getVal();
		double step = 0.2;
		double y1_0 = 0;
		double y1_1 = 0;
		while(true) {
			if(count++ > 2000 || startX > 600 || y0_0 > 600) {
				break;
			}
//			startY = startY + changshux.compute(startY, startX) * step / gx.get(0).getXishu();
			lis.add(new Point2D(startX, y0_0));
			System.out.println(startX + ", " + y0_0);
			y1_0 = y0_1 * step + y0_0;//定义
			y1_1 = (changshux.compute(y0_0, startX) - gx.get(0).getXishu() * y0_1) * step / gx.get(1).getXishu() + y0_1;
			y0_0 = y1_0;
			y0_1 = y1_1;
			startX = startX + step;//step就是deltaT
		}
		return lis;
		
	}
	
	/**
	 * 初始值+关系，得出下一个值
	 * 
	 * @param 
	 * @author lishaoping
	 * @return 
	 * @Date 2018年8月24日
	 * @Package com.bj58.im.client.mediaTest
	 * @return void
	 */
	private static List<Point2D> oneG(List<Item> gx,IComputerable changshux) {//左导右常
		double next = 0;
		int count = 0;
		double startX = 0;
		double startY = gx.get(0).getVal();
		double step = 0.2;
		List<Point2D> lis = new ArrayList<>();
		while(true) {
			lis.add(new Point2D(startX, startY));
			if(count++ > 2000 || startX > 600 || startY > 600) {
				break;
			}
			startY = startY + changshux.compute(startY, startX) * step / gx.get(0).getXishu();
			startX = startX + step;//step就是deltaT
		}
		return lis;
	}
	
	private static void showGuiji(List<Point2D> plist, Stage stage) {
		Group group = new Group();
		Scene scene = new Scene(group, 800, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Path path = new Path();
		path.setLayoutX(100);
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
		path.getElements().add(new MoveTo(plist.get(0).getX(), plist.get(0).getY() / fanwei * rate)); 
		for(int i = 1; i < plist.size() - 1; i++) {
//			double centerX = (plist.get(i).getX() + plist.get(i+1).getX())/2;
//			System.out.println(plist.get(i).getX() + "," + plist.get(i).getY() / fanwei * rate);
			path.getElements().add(new LineTo(plist.get(i).getX(), plist.get(i).getY() / fanwei * rate));
//			path.getElements().add(new CubicCurveTo(centerX, plist.get(i).getY(),
//						centerX, plist.get(i+1).getY(), plist.get(i+1).getX(), plist.get(i+1).getY()));
		}
		group.getChildren().add(path);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item(1, 1, 10, 1));
		itemList.add(new Item(1, 2, 12, 0.1));
//		IComputerable comp = getOneJC();
//		List<Point2D> plist = oneG(itemList, comp);
		
//		IComputerable comp = getTwoJC();
//		List<Point2D> plist = twoG(itemList, comp);
		
//		IComputerable comp = getTwoJC();
//		List<Point2D> plist = nG(itemList, comp);
		double[][] xishu = {{10,12,200,100,200},{10,12,100,100,200}};//震荡收敛和  发散的情形 ， 系数太小 震荡会很厉害
		IComputerable comp = getNGC();
		itemList.add(new Item(1, 3, 200, 0.1));
		itemList.add(new Item(1, 4, 100, 0.1));
		itemList.add(new Item(1, 5, 200, 0.1));
		List<Point2D> plist = nG(itemList, comp);
		
		showGuiji(plist, arg0);
	}

	private IComputerable getNGC() {
		IComputerable comp = new IComputerable() {
			@Override
			public double compute(double input) {
				double out = 1 / input;
				return out;
			}

			@Override
			public double compute(double y, double t) {
//				double out = 1;
				double out = Math.sin(t);//自然的是余弦波
				return out;
			}
		};
		return comp;
	}
	
	private IComputerable getTwoJC() {
		IComputerable comp = new IComputerable() {
			@Override
			public double compute(double input) {
				double out = 1 / input;
				return out;
			}

			@Override
			public double compute(double y, double t) {
//				double out = 1;
				double out = Math.sin(t);//自然的是余弦波
				return out;
			}
		};
		return comp;
	}
	
	private IComputerable getOneJC() {
		IComputerable comp = new IComputerable() {
			@Override
			public double compute(double input) {
				double out = 1 / input;
//				double out = input;//可以
//				double out = Math.sin(input);//增而稳定
//				double out = 1 + (input * 2 + 3*input * input + 4 * Math.pow(input, 3)) / Math.pow(Math.E, input);// - input * Math.pow(Math.E, input)
				return out;
			}

			@Override
			public double compute(double y, double t) {
//				double out = y* Math.exp(-t);
				double out = y;
//				double out = Math.sin(t);//自然的是余弦波
//				if(t < 0.1) {
//					t = 1;
//				}
//				double out = Math.pow(Math.E, 1/t) - 1/t * Math.pow(Math.E, 1/t);
//				double out = 2 * t * Math.pow(Math.E, 1/t / t) - 2/t * Math.pow(Math.E, 1/t/t);
				return out;
			}
		};
		return comp;
	}
}
