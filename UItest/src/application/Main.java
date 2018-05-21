package application;
	

import com.sun.prism.paint.Paint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;

/**
 * 继承关系：Paint<---Color
 * 		 Shape<---Ellipse
 * 		 Parent<---Group
 * @ClassName:Main
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月21日
 * @Version V1.0
 * @Package application
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			baseMethod(primaryStage);
//			flowPane(primaryStage);
//			lineGene(primaryStage);
//			lineDynamic(primaryStage);//画任意图形  , 可以动态生成，从而作为聊天的外层壳包装
			cicleDynamic(primaryStage);
//			rectenge(primaryStage);
//			ellipse(primaryStage);
//			path(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void cicleDynamic(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.web("0x4081D6", 1));
		Arc arc = new Arc(100, 100, 40, 30, 45, 270);//角度和长度，都是角度为值，
		arc.setFill(Color.WHEAT);
		arc.setStroke(Color.GREEN);
		arc.setStrokeWidth(2);
		arc.setType(ArcType.ROUND);
		
		group.getChildren().add(arc);
		
		Circle circle = new Circle(100, 180, 30, Color.color(0.1, 0.2, 0.3));
		circle.setStroke(Color.GREENYELLOW);
		circle.setStrokeWidth(2);
		group.getChildren().add(circle);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void lineDynamic(Stage primaryStage) {
		VBox box = new VBox();
		Path path = new Path();
		path.getElements().add(new MoveTo(30, 30));
		path.getElements().add(new LineTo(50, 40));
		path.getElements().add(new LineTo(30, 50));
		path.getElements().add(new LineTo(30, 30));
		Scene scene = new Scene(box, 300, 300, Color.WHEAT);
		box.getChildren().add(path);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void path(Stage primaryStage) {
		// TODO Auto-generated method stub
		Ellipse ellipse = new Ellipse(50, 50, 40, 30);//EllipseBuilder.create()
		ellipse.setStroke(Color.AZURE);
		ellipse.setStrokeWidth(2);
		ellipse.setFill(Color.AQUA);
		
		Ellipse ellipse2 = new Ellipse(50, 50, 20, 10);//EllipseBuilder.create()
		ellipse2.setStroke(Color.AZURE);
		ellipse2.setStrokeWidth(2);
		ellipse2.setFill(Color.BLUE);
		
		Shape shape = Path.subtract(ellipse, ellipse2);//
		shape.setFill(Color.BLUE);
		shape.setStroke(Color.RED);
		shape.setStrokeWidth(2);
		
		Group root = new Group();
		root.getChildren().add(shape);
		Scene scene = new Scene(root, 400, 400, Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void ellipse(Stage primaryStage) {
		primaryStage.setTitle("椭圆画法");
		Ellipse ellipse = new Ellipse(50, 50, 40, 30);//EllipseBuilder.create()
		ellipse.setStroke(Color.AZURE);
		ellipse.setStrokeWidth(2);
		ellipse.setFill(Color.AQUA);
		
		Ellipse ellipse2 = new Ellipse(50, 50, 20, 10);//EllipseBuilder.create()
		ellipse2.setStroke(Color.AZURE);
		ellipse2.setStrokeWidth(2);
		ellipse2.setFill(Color.BLUE);
		
		Group root = new Group();
		root.getChildren().add(ellipse);
		root.getChildren().add(ellipse2);
		Scene scene = new Scene(root, 400,400,Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 可以给正方形加事件，比如点击事件
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月19日
	 * @Package application
	 * @return void
	 */
	private void rectenge(Stage primaryStage) {
		Group root = new Group();
		
		Scene scene = new Scene(root, 500, 200);
		Rectangle r = new Rectangle(30, 30, 50, 50);
		r.setArcWidth(10);
		r.setArcHeight(10);
		r.setStroke(Color.RED);
		r.setStrokeWidth(5);
		r.setStrokeLineCap(StrokeLineCap.BUTT);
		r.setFill(Color.ALICEBLUE);//
		
		
		root.getChildren().add(r);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void lineGene(Stage primaryStage) {
		VBox box = new VBox();
		final Scene scene = new Scene(box, 500, 300);
		scene.setFill(null);
		Line line = new Line(0, 0, 100, 100);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		box.getChildren().add(line);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void flowPane(Stage primaryStage) {
		//1.定义panel
		FlowPane root = new FlowPane();
		root.setHgap(10);
		root.setVgap(20);
		root.setPadding(new Insets(15,15,15,15));
		
		//2.增减模型
		Button button = new Button("button2");
		button.setPrefSize(100, 100);
		root.getChildren().add(button);
		CheckBox box = new CheckBox("Check box");
		root.getChildren().add(box);
		
		Scene scene = new Scene(root, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void baseMethod(Stage primaryStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
