package application;
	

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 继承关系：Paint<---Color\LinearGradient
 * 		 Shape<---Ellipse
 * 		 Parent<---Group
 * 		 Effect<---DropShadow\Reflection
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
//			cicleDynamic(primaryStage);
//			shadowShow(primaryStage);
//			rectenge(primaryStage);
//			ellipse(primaryStage);
//			path(primaryStage);
//			polygon(primaryStage);//多边形快速画法,顺序给定点
//			cubicCurve(primaryStage);//三次曲线，控制点两个， 起点终点各一个
			
//			textDraw(primaryStage);
//			gradientRectangle(primaryStage);
//			textReflect(primaryStage);
//			textNextLine(primaryStage);
			
			propertyListener(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void propertyListener(Stage primaryStage) {
		Group group = before(primaryStage);
		SimpleIntegerProperty intProp = new SimpleIntegerProperty(45);
		intProp.addListener(new ChangeListener<Number>() {//必须继承Number
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(oldValue);
				System.out.println(newValue);
			}
		});
		
		primaryStage.show();
	}

	private Group before(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		return group;
	}
	
	/**
	 * 依赖反转，依赖从外部注入，而不是自己内部构造
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月22日
	 * @Package application
	 * @return void
	 */
	private void textNextLine(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 400, 400, Color.WHEAT);
		
		Text text = new Text(40, 100, "This is a great day! I have got a bad thing. I want the bad thing go away as much as possible!");
		text.setFill(Color.RED);
		Reflection rf = new Reflection(4, 0.7, 0.7, 0.1);
		text.setEffect(rf);
		Font font = new Font("微软雅黑", 35);
		text.setFont(font);
		text.setWrappingWidth(400);
		group.getChildren().add(text);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 阴影是相对于文本
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月21日
	 * @Package application
	 * @return void
	 */
	private void textReflect(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, 400, 400, Color.WHITE);
		Text text = new Text(40, 50, "北京网邻信息技术有限公司");
		Font font = new Font(30);
		text.setFont(font);
		text.setFill(Color.rgb(0xff, 0xff, 0x00, 1));
		DropShadow shadow = new DropShadow(40, 1, 1, Color.GREEN);
		text.setEffect(shadow);
		Reflection refle = new Reflection(3, 0.7, 0.9, 0.1);
		text.setEffect(refle);
		
		root.getChildren().add(text);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//文本样式：
//		StringProperty 
	}

	private void gradientRectangle(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		Rectangle rt = new Rectangle(50, 50, 100, 100);
		Stop[] stops = new Stop[] {new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		rt.setFill(gradient);
		
		Circle bar = new Circle(100, 200, 50);
		RadialGradient rgra = new RadialGradient(0, 0.1, 100, 200, 50, false, CycleMethod.NO_CYCLE, 
				new Stop(0, Color.BLACK), new Stop(1, Color.RED));
		bar.setFill(rgra);
		group.getChildren().add(rt);
		group.getChildren().add(bar);
		
		Rectangle rt2 = new Rectangle(200, 50, 60, 60);
		LinearGradient lear = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, 
				new Stop(0, Color.rgb(25, 200, 0, 0.4)),
				new Stop(1, Color.rgb(0, 0, 0, 0.1)));
		rt2.setFill(lear);
		group.getChildren().add(rt2);
		
		primaryStage.setScene(scene);
		primaryStage.show();
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
		
		group.getChildren().add(text);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void cubicCurve(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		CubicCurve curve = new CubicCurve(100, 100, 150, 50, 175, 150, 200, 100);
		curve.setStroke(Color.GREEN);
		curve.setStrokeWidth(2);
		curve.setFill(Color.WHEAT);
		
		group.getChildren().add(curve);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void polygon(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.web("0xffffff", 1));
		
		Polygon dbx = new Polygon();
		dbx.getPoints().addAll(new Double[] {0.0,0.0, 20.0,10.0, 10.0,20.0});
		dbx.setStroke(Color.GREEN);
		dbx.setStrokeWidth(2);
		dbx.setFill(Color.WHEAT);
		group.getChildren().add(dbx);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void shadowShow(Stage primaryStage) {
		Group group = new Group();
		Scene scene = new Scene(group, 300, 400, Color.web("0xffffff", 1));
		Circle circle = new Circle(100, 180, 30, Color.color(0.1, 0.2, 0.3));
		circle.setStroke(Color.GREENYELLOW);
		circle.setStrokeWidth(2);
		
		DropShadow shadow = new DropShadow(30, 4, 4, Color.GRAY);
		circle.setEffect(shadow);
		
		group.getChildren().add(circle);
		primaryStage.setScene(scene);
		primaryStage.show();
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
