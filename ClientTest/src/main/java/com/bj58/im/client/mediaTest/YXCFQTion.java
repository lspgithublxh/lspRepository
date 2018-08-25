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
				double out = Math.sin(t);//自然的是余弦波
//				if(t < 0.1) {
//					t = 1;
//				}
//				double out = Math.pow(Math.E, 1/t) - 1/t * Math.pow(Math.E, 1/t);
//				double out = 2 * t * Math.pow(Math.E, 1/t / t) - 2/t * Math.pow(Math.E, 1/t/t);
				return out;
			}
		};
		List<Point2D> plist = oneG(itemList, comp);
		showGuiji(plist, arg0);
	}
}
