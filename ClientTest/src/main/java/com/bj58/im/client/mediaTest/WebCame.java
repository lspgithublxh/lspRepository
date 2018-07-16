package com.bj58.im.client.mediaTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.github.sarxos.webcam.Webcam;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WebCame extends Application{


	public static void main(String[] args) {
//		test();
		launch(args);
	}
	
	private void textDraw(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		Text text = new Text(50, 50, "2018年5月21号");
		text.setFill(Color.RED);
		text.setStroke(Color.WHEAT);
		text.setStrokeWidth(0.3);
		text.setRotate(30);
		
		Font font = Font.font("微软雅黑", FontWeight.BOLD, 15);
		text.setFont(font);
		
		Text text2 = new Text(150, 150, "2018年5月21号--------------");
		text2.setFill(Color.RED);
//		text2.prefWidth(30);
//		text2.setWrappingWidth(30);
		System.out.println(text2.getLayoutBounds().getWidth());
		text2.setWrappingWidth(30);
		System.out.println(text2.getLayoutBounds().getWidth());
		System.out.println(text2.getWrappingWidth());
		System.out.println();
		group.getChildren().add(text);
		
		group.getChildren().add(text2);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * 自动保存一张图片---且不会关闭
	 * @param 
	 * @author lishaoping
	 * @Date 2018年7月16日
	 * @Package com.bj58.im.client.mediaTest
	 * @return void
	 */
	private static void test() {
		Webcam came = Webcam.getDefault();
		came.open();
		try {
			ImageIO.write(came.getImage(), "PNG", new File("D:\\cache1\\webcame.png"));
			ImageInputStream in = ImageIO.createImageInputStream(came.getImage());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		method2(primaryStage);
//		textDraw(primaryStage);
	}

	private void method2(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Webcam came = Webcam.getDefault();
		came.open();
//		
		Map<String, Boolean> ma = new HashMap<String, Boolean>();
		ma.put("show", true);
		Object lock = new Object();
		Text text = new Text(50, 50, "2018年5月21号");
		text.setFill(Color.RED);
		text.setStroke(Color.WHEAT);
		text.setStrokeWidth(0.3);
		text.setRotate(30);
		
		Font font = Font.font("微软雅黑", FontWeight.BOLD, 15);
		text.setFont(font);
		Text t = new Text(100, 100, "Hahahahhaah");
		t.setFill(Color.RED);
//		t.setLayoutX(100);
//		t.setLayoutY(100);
		root.getChildren().add(text);
		root.getChildren().add(t);
//		Image image;
//		try {
//			image = new Image(new FileInputStream("D:\\a.png"));
//			ImageView view = new ImageView(image);
//			view.setFitHeight(100);
//			view.setFitWidth(100);
//			view.setLayoutX(100);
//			view.setLayoutY(150);
//			root.getChildren().add(view);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
//					try {
//						Thread.sleep(200);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					ByteBuffer buffer = came.getImageBytes();
//					System.out.println(buffer.limit());
//					byte[] data = new byte[buffer.limit()];
//					buffer.get(data);
//					Image image = null;
//					try {
//						image = new Image(new FileInputStream("D:\\a.png"));
//						ImageView view = new ImageView(image);
//						view.setFitHeight(100);
//						view.setFitWidth(100);
//						view.setLayoutX(100);
//						view.setLayoutY(150);
//						root.getChildren().add(view);
//					} catch (FileNotFoundException e1) {
//						e1.printStackTrace();
//					}
					Image image2 = SwingFXUtils.toFXImage(came.getImage(), new WritableImage(100, 100));
//					Image image2 = new Image(new ByteArrayInputStream(data));
//					ImageInputStream in = ImageIO.createImageInputStream(came.getImage());
					
//					Image image2 = new Image();
//					if(data.length == 1) {
//						break;
//					}
					ImageView view = new ImageView(image2);
//					root.getChildren().add(view);
					System.out.println("show-----start");
					if(ma.get("show")) {
						System.out.println("inter into");
						synchronized (lock) {
							ma.put("show", false);
							lock.notifyAll();
							System.out.println("sub thread");
						}
					}
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							if(root.getChildren().size() > 0) {
								root.getChildren().set(0, view);
							}else {
								root.getChildren().add(view);
							}
							System.out.println(root.getChildren().size());
						}
					});
					
//					break;
				}
				
			}
		}).start();
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("main show");
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
}
