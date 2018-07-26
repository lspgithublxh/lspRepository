package com.bj58.im.client.mediaTest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import com.github.sarxos.webcam.Webcam;
import com.sun.javafx.iio.ImageStorage.ImageType;
import com.sun.javafx.tk.FileChooserType;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class WebCame extends Application{


	public static void main(String[] args) {
//		test();
		System.out.println(System.getProperty("com.ibm.vm.bitmode"));
		launch(args);
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
//		method2(primaryStage);
//		textDraw(primaryStage)
//		cameraPicture(primaryStage);
		cameraPictureAndSave(primaryStage);
		//抓且保存，且发送    图片 和视频    以后还有识别
		
	}

	private void cameraPictureAndSave(Stage primaryStage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Webcam came = Webcam.getDefault();//只有一个摄像头System.out.println(Webcam.getWebcams().size());
		came.open();
		Button button = new Button("takePhone");
		button.setLayoutX(100);
		button.setLayoutY(200);
		root.getChildren().add(button);
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				synchronized (lock) {//不用锁，直接getImage是否可行---会不会 读出乱的数据
					stop = true;
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ImageView view = new ImageView(cacheImage);
					view.setLayoutY(300);
					if(root.getChildren().size() == 2) {
						root.getChildren().add(view);
					}else if(root.getChildren().size() >= 3){
						root.getChildren().set(2, view);
					}
					//2.右键菜单
					if(event.isPopupTrigger()) {//表示右键触发
//						MenuBar bar = new MenuBar();
//						bar.setLayoutX(event.getSceneX());
//						bar.setLayoutY(event.getSceneY());
////						bar.setMaxWidth(30);
//						Menu menu = new Menu("menu");
//						menu.setVisible(true);
//						MenuItem item = new MenuItem("one");
//						menu.getItems().add(item);
//						bar.getMenus().add(menu);
//						root.getChildren().add(bar);
						
						ContextMenu cm = new ContextMenu();
						MenuItem item = new MenuItem("保存");
						MenuItem item1 = new MenuItem("另存为");
						item.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								
								FileChooser fcs = new FileChooser();
								fcs.setInitialDirectory(new File("D:\\cache1"));
								fcs.setInitialFileName("拍照图片" + System.currentTimeMillis() + ".jpg");
								fcs.setTitle("另存为");
								File f = fcs.showSaveDialog(primaryStage);
								System.out.println(f);
								String path = f.getAbsolutePath();
								try {
									ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(), new BufferedImage((int)view.getImage().getWidth(), (int)view.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB)), "PNG", f);
								} catch (IOException e) {
									e.printStackTrace();
								}//另一种方法：字节保存方法
							}
							
						});
						MenuItem item2 = new MenuItem("发送");
						cm.getItems().add(item);
						cm.show(primaryStage);
						
					}
					//3.打开对话框
				}
				
			}
			
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					Image image2 = SwingFXUtils.toFXImage(came.getImage(), new WritableImage(100, 100));
					isGoon(image2);
					ImageView view = new ImageView(image2);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(pane.getChildren().size() > 0) {
								pane.getChildren().set(0, view);
							}else {
								pane.getChildren().add(view);
							}
						}
					});
				}
			}

			
		}).start();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	Object lock = new Object();
	Image cacheImage = null;
	Boolean stop = false;
	
	private void cameraPicture(Stage primaryStage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Webcam came = Webcam.getDefault();//只有一个摄像头System.out.println(Webcam.getWebcams().size());
		came.open();
		Button button = new Button("takePhone");
		button.setLayoutX(100);
		button.setLayoutY(200);
		root.getChildren().add(button);
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				synchronized (lock) {//不用锁，直接getImage是否可行---会不会 读出乱的数据
					stop = true;
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ImageView view = new ImageView(cacheImage);
					view.setLayoutY(300);
					if(root.getChildren().size() == 2) {
						root.getChildren().add(view);
					}else if(root.getChildren().size() == 3){
						root.getChildren().set(2, view);
					}
					//2.右键菜单
					if(event.isPopupTrigger()) {//表示右键触发
						
					}
					//3.打开对话框
				}
				
			}
			
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					Image image2 = SwingFXUtils.toFXImage(came.getImage(), new WritableImage(100, 100));
					isGoon(image2);
					ImageView view = new ImageView(image2);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(pane.getChildren().size() > 0) {
								pane.getChildren().set(0, view);
							}else {
								pane.getChildren().add(view);
							}
						}
					});
				}
			}

			
		}).start();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void isGoon(Image image2) {
		if(stop) {
			cacheImage = image2;
			synchronized (lock) {//等到或者不等  都会在wait()之后
				lock.notify();
			}
			stop = false;
		}
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
//		Text text = new Text(50, 50, "2018年5月21号");
//		text.setFill(Color.RED);
//		text.setStroke(Color.WHEAT);
//		text.setStrokeWidth(0.3);
//		text.setRotate(30);
//		
//		Font font = Font.font("微软雅黑", FontWeight.BOLD, 15);
//		text.setFont(font);
//		Text t = new Text(100, 100, "Hahahahhaah");
//		t.setFill(Color.RED);
//		t.setLayoutX(100);
//		t.setLayoutY(100);
//		root.getChildren().add(text);
//		root.getChildren().add(t);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
//					try {
//						Thread.sleep(200);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					Image image2 = SwingFXUtils.toFXImage(came.getImage(), new WritableImage(100, 100));
//					Image image2 = new Image(new ByteArrayInputStream(data));
//					ImageInputStream in = ImageIO.createImageInputStream(came.getImage());
//					image2.getProgress()//加载程度--加载了多少
					ImageView view = new ImageView(image2);
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
