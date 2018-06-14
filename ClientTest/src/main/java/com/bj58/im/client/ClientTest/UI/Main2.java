package com.bj58.im.client.ClientTest.UI;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class Main2 extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		talkingSpecial(arg0);
		//只有使用滚动pane
		//socket的程序的生成
	}

	private void scrollPane(Stage primaryStage) {
		ScrollPane pane = new ScrollPane();
		pane.setPrefHeight(200);
		
	}
	
	Map<String, Object[]> config = new HashMap<String, Object[]>();
	
	private void talkingSpecial(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("../a.png")));
		HBox hbox = new HBox();
		VBox boxleftMost = new VBox();
		boxleftMost.setPrefWidth(50);
		boxleftMost.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY	, new Insets(0))));//new BackgroundFill(Color.BLACK, CornerRadii.EMPTY	, new Insets(0))
		String[] texts  = {"联系人", "朋友圈", "公众号", "小程序"};
		for(String t : texts) {
			Label blank = new Label("");
			blank.setPrefHeight(20);
			boxleftMost.getChildren().add(blank);
			Text text = new Text(String.format("  %s  ", t));
			text.setFont(Font.font("宋体", FontWeight.BOLD, 12));
			text.setFill("联系人".equals(t) ? Color.rgb(0, 0xff, 0) : Color.WHITE);
			boxleftMost.getChildren().add(text);
			
		}
		int winWidth = 840;
		VBox boxleft = new VBox();
		boxleft.setPrefWidth(50);
		for(int i = 0; i < 4; i++) {
			Label b = new Label("");
			b.setPrefHeight(20);
			boxleft.getChildren().add(b);
			getHeadImg(boxleft, 0, 0, false);
			//增加带文本的图片，或者加文字
		}
		
//		Label b2 = new Label("");
//		b2.setPrefHeight(20);
//		boxleft.getChildren().add(b2);
//		getHeadImg(boxleft, 0, 0, false);
		hbox.getChildren().add(boxleftMost);
		hbox.getChildren().add(boxleft);
		VBox box = new VBox();
		hbox.getChildren().add(box);
		ScrollPane pane = new ScrollPane();
		pane.setPrefSize(winWidth, 400);//决定宽高
//		pane.setFitToHeight(true);
//		pane.setFitToWidth(true);
		Pane groupOut = new Pane();
		Pane group = new Pane();
		double jianPointX = 80;
		double jianPointY = 400;
		Rectangle r = new Rectangle(0, 0, winWidth, 6000);
		r.setFill(Color.WHEAT);
		group.getChildren().add(r);

		final Double[] jianPointYArr = {jianPointY};
		config.put("Tom", new Object[] {group, jianPointYArr});
		group.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				double cu = group.getLayoutY() + event.getDeltaY();
				if(cu <= 0) {// && cu >= -360
					group.setLayoutY(group.getLayoutY() + event.getDeltaY());
				}
			}
		});
		groupOut.getChildren().addAll(group);//, sc
		pane.setContent(groupOut);
//		pane.setPrefHeight(200);
		box.getChildren().add(pane);
		TextArea area = new TextArea();
		area.setPrefWidth(winWidth);
		area.setPrefHeight(100);
		final Map<String, String> pressedKeyMap = new HashMap<String, String>();
		area.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				String key = event.getCode().getName();
				if("Enter".equals(key) && "Ctrl".equals(pressedKeyMap.get("keyPressed"))) {
					writeTextMessage(group, jianPointX, jianPointYArr, area.getText());
					area.clear();
//					double old = jianPointYArr[0];
//					printInput(group, jianPointX, jianPointYArr, area);
//					
//					group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
				}
				pressedKeyMap.put("keyPressed", key);
			}
			
		});
		area.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String key = event.getCode().getName();
				if(key.equals(pressedKeyMap.get("keyPressed"))) {
					pressedKeyMap.remove("keyPressed");
				}
			}
		});
		box.getChildren().add(area);
		Button button = new Button("发送");
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				double old = jianPointYArr[0];
				printInput(group, jianPointX, jianPointYArr, area);
				
				group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
			}
			
		});
		button.setAlignment(Pos.BOTTOM_RIGHT);
		
		box.getChildren().add(button);
		Scene scene = new Scene(hbox, winWidth, 530, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * 对外提供的写接口
	 * @param 
	 * @author lishaoping
	 * @Date 2018年6月14日
	 * @Package com.bj58.im.client.ClientTest.UI
	 * @return void
	 */
	public void writeRightTextMessage(String name, String content) {
		Object[] conf = config.get(name);
		Pane group = (Pane) conf[0];
		final Double[] jianPointYArr = (Double[]) conf[1];
		double old = jianPointYArr[0];
		
		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeTextMessage(Pane group, double jianPointX, final Double[] jianPointYArr, String content) {
		double old = jianPointYArr[0];
		drawContent(group, jianPointX, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	private void printInput(Pane group, double jianPointX, final Double[] jianPointYArr, TextArea area) {
		String content = area.getText();
		drawContent(group, jianPointX, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		area.clear();
	}
	
	private void drawContent(Pane group, double jianPointX, double jianPointY, String content) {
		Font font = Font.font("宋体", 15);
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(Color.BLACK);
		double strWitdh = text.getLayoutBounds().getWidth();
		double strHeight = text.getLayoutBounds().getHeight();
		Path path = new Path();
		double width = strWitdh;//纯直线长度
		double height = strHeight;//纯直线长度
		double radius = 5;
		double jianLineWidth = 5;
		double jianLineHeight = 10;
		double angle = 90;
		
		path.getElements().add(new MoveTo(jianPointX, jianPointY));
		path.getElements().add(new LineTo(jianPointX + jianLineWidth, jianPointY - jianLineHeight));
		path.getElements().add(new LineTo(jianPointX + jianLineWidth, jianPointY - jianLineHeight - height));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius, jianPointY - jianLineHeight - height - radius, false, true));
		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius + width, jianPointY - jianLineHeight - height - radius));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius + width + radius, jianPointY - jianLineHeight - height, false, true));
		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius + width + radius, jianPointY - jianLineHeight));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius + width, jianPointY - jianLineHeight + radius, false, true));
		
		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius, jianPointY - jianLineHeight + radius));
		path.getElements().add(new LineTo(jianPointX, jianPointY));
		path.setFill(Color.rgb(0x7C, 0xCD, 0x7C));
		DropShadow shadow = new DropShadow(10, 1, 1, Color.RED);
		path.setEffect(shadow);
		
		text.setX(jianPointX + jianLineWidth + radius);
		text.setY(jianPointY - jianLineHeight - height / 2 + strHeight / 4);
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(text);
	}
	private void getHeadImg(Pane group, double jianPointX, double jianPointY, boolean right) {
		Image image;
		try {
			image = new Image(new FileInputStream("D:\\head.jpg"));
			ImageView view3 = new ImageView(image);
			view3.setFitHeight(image.getHeight() / 4);
			view3.setFitWidth(image.getWidth() / 4);
			
			Circle circle3 = new Circle(view3.getFitWidth() / 2, view3.getFitWidth() / 2, view3.getFitWidth() / 2);
			view3.setClip(circle3);
			view3.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					System.out.println(event.getEventType().getName());
				}
			});
			//新的处理方式：加阴影：
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			WritableImage wtimage = view3.snapshot(params, null);
			ImageView view4 = new ImageView(wtimage);
			DropShadow shadow = new DropShadow(20, 0, 0, Color.RED);
			view4.setEffect(shadow);
			System.out.println(circle3.getRadius());
			if(right) {
				view4.setLayoutX(jianPointX + 10);
			}else {
				view4.setLayoutX(jianPointX - circle3.getRadius() * 2 - 10);
			}
			view4.setLayoutY(jianPointY - circle3.getRadius());
			//分图片的来源组
			if(group.getClass().getName().endsWith(".VBox") && jianPointX == jianPointY && jianPointX == 0.0) {
				HBox hbox = new HBox(5);
				hbox.setPrefWidth(100);
				hbox.setPrefHeight(30);
				VBox vboxImage = new VBox();
				vboxImage.setPadding(new Insets(0, 0, 0, 5));
				vboxImage.getChildren().add(view4);
				hbox.getChildren().add(vboxImage);
				VBox vbox = new VBox();
				vbox.setPadding(new Insets(15, 0, 0, 0));
				Text text = new Text("  布冯告  ");
				text.setFill(Color.BLACK);
				text.setFont(Font.font("宋体", FontWeight.BOLD, 12));
				vbox.getChildren().add(text);
				hbox.getChildren().add(vbox);
				hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
				group.getChildren().add(hbox);
			}else {
				group.getChildren().add(view4);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	private double drawContentRight(Pane group, double jianPointX, double jianPointY, String content) {
		Font font = Font.font("宋体", 15);
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(Color.BLACK);
		double lineHeight = text.getLayoutBounds().getHeight();
		text.setWrappingWidth(30);
		double strWitdh = text.getLayoutBounds().getWidth();
		double strHeight = text.getLayoutBounds().getHeight();
		Path path = new Path();
		double width = strWitdh;//纯直线长度
		double height = strHeight;//纯直线长度
		double radius = 5;
		double jianLineWidth = 5;
		double jianLineHeight = 10;
		double angle = 90;
		jianPointY += height;
		
		path.getElements().add(new MoveTo(jianPointX, jianPointY));
		path.getElements().add(new LineTo(jianPointX - jianLineWidth, jianPointY - jianLineHeight));
		path.getElements().add(new LineTo(jianPointX - jianLineWidth, jianPointY - jianLineHeight - height));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX - jianLineWidth - radius, jianPointY - jianLineHeight - height - radius, false, false));
		path.getElements().add(new LineTo(jianPointX - jianLineWidth - radius - width, jianPointY - jianLineHeight - height - radius));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX - jianLineWidth - radius - width - radius, jianPointY - jianLineHeight - height, false, false));
		path.getElements().add(new LineTo(jianPointX - jianLineWidth - radius - width - radius, jianPointY - jianLineHeight));
		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX - jianLineWidth - radius - width, jianPointY - jianLineHeight + radius, false, false));
		
		path.getElements().add(new LineTo(jianPointX - jianLineWidth - radius, jianPointY - jianLineHeight + radius));
		path.getElements().add(new LineTo(jianPointX, jianPointY));
		path.setFill(Color.rgb(0x7C, 0xCD, 0x7C));
		DropShadow shadow = new DropShadow(10, 1, 1, Color.RED);
		path.setEffect(shadow);
		
		text.setX(jianPointX - jianLineWidth - radius - width);
		text.setY(jianPointY - jianLineHeight - height + lineHeight / 2 + radius);
		
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(text);
		
		return jianPointY;
	}
}
