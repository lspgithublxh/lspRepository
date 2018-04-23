package com.bj58.im.client.container;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFxTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
//		handleOne(primaryStage);
		handleTwo(primaryStage);
	}

	private void handleTwo(Stage primaryStage) {
		BorderPane root = new BorderPane();
	    Scene scene = new Scene(root, 580, 350, Color.WHITE);
	    GridPane gridpane = new GridPane();
	    gridpane.setPadding(new Insets(10));
	    gridpane.setHgap(10);
	    gridpane.setVgap(10);
	    ColumnConstraints column1 = new ColumnConstraints(100);
	    ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
	    column2.setHgrow(Priority.ALWAYS);
	    gridpane.getColumnConstraints().addAll(column1, column2);
	    Label fNameLbl = new Label("Input Field");
	    TextField fNameFld = new TextField();
	    TextArea area = new TextArea();
//	    area.setLayoutX(0);
//	    area.setLayoutY(0);
	    area.setTranslateX(200);//平移
	    area.setScaleX(4);//缩放
//	    area.setScaleY(4);
//	    area.setTranslateY(100);
//	    area.setScaleZ(50);
	    Button saveButt = new Button("Save");
	    saveButt.setOnAction(arg0 ->{
	    	System.out.println(fNameFld.getText());
	    	area.setText(fNameFld.getText());
	    });
	   
	    GridPane.setHalignment(area, HPos.RIGHT);
	    gridpane.add(area, 0, 1);
	    // First name label
	    GridPane.setHalignment(fNameLbl, HPos.RIGHT);
	    gridpane.add(fNameLbl, 0, 28);
	    // First name field
	    GridPane.setHalignment(fNameFld, HPos.LEFT);
	    gridpane.add(fNameFld, 1, 28);
	    // Save button
	    GridPane.setHalignment(saveButt, HPos.RIGHT);
	    gridpane.add(saveButt, 2, 28);
	    root.setCenter(gridpane);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	private void handleOne(Stage primaryStage) {
		//按钮和事件
		Button button = new Button();
		button.setText("百度");
		
		//文本输入框：
		TextField notification = new TextField ();
		notification.setText("Label");
		button.setOnAction(event -> {
			System.out.println(notification.getText());
		});//是一个只有一个方法的接口的匿名实现类的简单写法;;是对只有一个方法的匿名内部类的简化
//	    notification.setOnKeyPressed(args -> {
//	    	System.out.println(notification.getText());
//	    });
		StackPane pane = new StackPane();
		pane.getChildren().add(button);
		pane.getChildren().add(notification);
		Scene scene = new Scene(pane, 300, 250);
		primaryStage.setTitle("标题");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
