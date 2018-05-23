package application;
	

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 继承关系：Object<--Paint<---Color\LinearGradient
 * 		 Object<--Node<--Shape<---Ellipse\Text
 *		 Object<--Node<--Parent<---Group\Region.Pane.HBox\Control\WebView
 *		 Object<--Node<--ImageView
 * 		 Object<--Effect<---DropShadow\Reflection
 * 		 Object<--Window<--Stage
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
			
//			propertyListener(primaryStage);
//			bingsPropperty(primaryStage);
//			loginIdentify(primaryStage);
//			listCanEvent(primaryStage);
			
//			hboxLayout(primaryStage);
//			borderPane(primaryStage);
//			menubarLayout(primaryStage);
//			gridLayout(primaryStage);//登陆界面
//			scrollLayout(primaryStage);//TitledPane 标题面板略   手风琴Accordion
			
			//标签按钮可以设置图标和文本
//			labelButtonGraph(primaryStage);
			
//			radioButton(primaryStage);
//			checkBox(primaryStage);
//			choiceBox(primaryStage);
//			contextMenu(primaryStage);//hyperlink, processbar
			
			menuBest(primaryStage);//级联菜单项
			//宽度绑定窗口的/stage的，那么会一样宽
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void menuBest(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 400, 400, Color.WHEAT);
		
		MenuBar bar = new MenuBar();
		
		Menu menu1 = new Menu("File");
		MenuItem item1 = new MenuItem("New");
		MenuItem item2 = new MenuItem("Save");
		MenuItem item3 = new MenuItem("Exit");
		item3.setOnAction(actionEvent -> Platform.exit());
		menu1.getItems().addAll(item1, item2, item3);
		
		Menu menu2 = new Menu("Web");
		CheckMenuItem checkItem1 = new CheckMenuItem("HTML");
		checkItem1.setSelected(true);
		CheckMenuItem checkItem2 = new CheckMenuItem("CSS");
		CheckMenuItem checkItem3 = new CheckMenuItem("JS");
		menu2.getItems().addAll(checkItem1, checkItem2, checkItem3);
		
		Menu menu3 = new Menu("SQL");
		ToggleGroup toggle = new ToggleGroup();
		RadioMenuItem raItem1 = new RadioMenuItem("MySQL");
		raItem1.setToggleGroup(toggle);
		RadioMenuItem raItem2 = new RadioMenuItem("Oracle");
		raItem2.setToggleGroup(toggle);
		raItem2.setSelected(true);
		
		menu3.getItems().addAll(raItem1, raItem2);
		
		//子菜单
		Menu menu4 = new Menu("Tutorial");
		menu4.getItems().addAll(new MenuItem("JAVA"), new MenuItem("JAVAFX"), new MenuItem("SWing"));
		menu3.getItems().add(menu4);
		
		//特殊标签
		CustomMenuItem citem1 = new CustomMenuItem();
		citem1.setContent(new Slider());
		citem1.setHideOnClick(false);
		menu3.getItems().add(citem1);
		//助记符
		Menu zjfMenu = new Menu("_File");
		zjfMenu.setMnemonicParsing(true);
		zjfMenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
		menu3.getItems().add(zjfMenu);
		
		bar.getMenus().addAll(menu1, menu2, menu3);
		
		pane.setTop(bar);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void contextMenu(Stage primaryStage) {
		Group group = new Group();
		
		ContextMenu menu = new ContextMenu();
		MenuItem item = new MenuItem("item1");
		MenuItem item2 = new MenuItem("item1");
		menu.getItems().addAll(item, item2);
		item.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("action event :" + arg0.getEventType().getName());
			}
		});
		menu.setOnShowing(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				System.out.println(arg0.getEventType().getName());
			}
		});
		
		TextField fie = new TextField("");
		fie.setContextMenu(menu);
		//hyperlink
		Hyperlink link = new Hyperlink("Click");
		link.setContextMenu(menu);
		link.setLayoutX(100);
		link.setLayoutY(100);
		//processBar
		final User user = new User(new SimpleStringProperty("lsp"), new ReadOnlyStringWrapper("abc"), new SimpleIntegerProperty(5));
		user.setScore(new SimpleFloatProperty(0.0f));
		ProgressBar progress = new ProgressBar(0.4);
		progress.setLayoutX(100);
		progress.setLayoutY(140);
		progress.setProgress(0.8);
		progress.progressProperty().bind(user.getScore());
		user.getScore().set(0.1f);;
		System.out.println(user.getScore().doubleValue());
		final boolean status = false;
		fie.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			boolean status = false;
			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getButton().name());
				System.out.println(event.getEventType().getName());
				if(user.getScore().floatValue() - 0.1 < 0.1) {
					status = true;
				}else if(1 - user.getScore().floatValue() < 0.1){
					status = false;
				}
				if(status) {
					user.getScore().set(0.1f + user.getScore().floatValue());
				}else {
					user.getScore().set(user.getScore().floatValue() - 0.1f);
				}
			}
		});
		//ProgressIndicator 
		ProgressIndicator indicator = new ProgressIndicator(0.1);
		indicator.setLayoutX(100);
		indicator.setLayoutY(180);
		indicator.progressProperty().bind(user.getScore());
		fie.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			boolean status = false;
			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getEventType().getName());
			}
		});
		//滚动条和滚动事件--可以忽略
//		ScrollBar
		
		//DatePicker
		DatePicker picker = new DatePicker(LocalDate.now());
		picker.setLayoutX(100);
		picker.setLayoutY(200);
		//ColorPicker略
		
		
		Button butt = new Button("show_file");
		butt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getEventType());
				//FileChoose
				FileChooser filec = new FileChooser();
				filec.setTitle("open a file");
				filec.setInitialDirectory(new File("D:\\"));
				filec.setInitialFileName("D:\\a.png");
				
				filec.selectedExtensionFilterProperty().addListener(new ChangeListener<ExtensionFilter>() {

					@Override
					public void changed(ObservableValue<? extends ExtensionFilter> observable, ExtensionFilter oldValue,
							ExtensionFilter newValue) {
						System.out.println(observable.getValue().getDescription());
//						System.out.println(oldValue.getDescription());
					}
				});
				filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ALL Images", "*.*"),
						new FileChooser.ExtensionFilter("JPG", ".jpg"),
						new FileChooser.ExtensionFilter("PNG", ".png"),
						new FileChooser.ExtensionFilter("GIF", ".gif"),
						new FileChooser.ExtensionFilter("BMP", ".bmp"));
				File file = filec.showOpenDialog(primaryStage);
				System.out.println("get file :" + file.getAbsolutePath());
//				filec.showSaveDialog(primaryStage);
			}
		});
		butt.setLayoutX(100);
		butt.setLayoutY(220);
		
		group.getChildren().add(fie);
		group.getChildren().add(link);
		group.getChildren().add(progress);
		group.getChildren().add(indicator);
		group.getChildren().add(picker);
		group.getChildren().add(butt);
		
		Scene scene = new Scene(group, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void choiceBox(Stage primaryStage) {
		VBox box = new VBox(10);
		
		ChoiceBox<String> cbox = new ChoiceBox<>(FXCollections.observableArrayList("A", "B", "C", "D"));
		
		//		cbox.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<String>>() {
//			@Override
//			public void changed(ObservableValue<? extends SingleSelectionModel<String>> arg0, SingleSelectionModel<String> arg1, SingleSelectionModel<String> arg2) {
//					System.out.println(arg0.getValue().getSelectedIndex());
//					System.out.println(arg0.getValue().getSelectedItem());
//			}
//		});
		cbox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				System.out.println("index:" + arg0.getValue().intValue());
			}
		});
		
		box.getChildren().add(cbox);
		Scene scene = new Scene(box, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void checkBox(Stage primaryStage) {
		VBox box = new VBox(10);
		CheckBox check = new CheckBox("A");
		check.setSelected(true);
		CheckBox check2 = new CheckBox("B");
		CheckBox check3 = new CheckBox("C");
		CheckBox check4 = new CheckBox("D");
		
		Tooltip tip = new Tooltip("title of tooltip");
		tip.setFont(Font.font("微软雅黑", 24));
		check.setTooltip(tip);
		check2.setTooltip(tip);
		check3.setTooltip(tip);
		check4.setTooltip(tip);
		check.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				System.out.println("current:" + arg0.getValue());
			}
		});
		
		box.getChildren().addAll(check, check2, check3, check4);
		
		Scene scene = new Scene(box, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void radioButton(Stage primaryStage) {
		HBox box = new HBox(10);
		
		ToggleGroup group = new ToggleGroup();
		
		RadioButton sele1 = new RadioButton("female");
		RadioButton sele2 = new RadioButton("male");
		RadioButton sele3 = new RadioButton("unknown");
		sele2.setSelected(true);
		sele1.setToggleGroup(group);
		sele2.setToggleGroup(group);
		sele3.setToggleGroup(group);
		sele1.setId("female");
		sele2.setId("male");
		sele3.setId("unknown");
		sele1.setUserData("Display1");
		sele2.setUserData("Display2");
		sele2.setUserData("Display3");
		
		box.getChildren().add(sele1);
		box.getChildren().add(sele2);
		box.getChildren().add(sele3);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				System.out.println(arg0.getValue().selectedProperty());//当前选择
//				System.out.println(arg1.selectedProperty() + "," + arg1.isSelected());
				System.out.println(arg2.selectedProperty() + "," + arg2.isSelected());//当前选择
//				System.out.println(arg0.getValue().isSelected());//当前选择
			}
		});
		
		Scene scene = new Scene(box, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void labelButtonGraph(Stage primaryStage) {
		StackPane pane = new StackPane();
		Button button = new Button("OK", new ImageView(new Image(getClass().getResourceAsStream("xxx2.png"))));
		button.setMaxHeight(20);
		button.setMaxWidth(20);
		DropShadow shadow = new DropShadow(2, 3, 3, Color.GREEN);
		button.setEffect(shadow);
		
		button.addEventHandler(KeyEvent.KEY_PRESSED	, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				System.out.println("key event, :" + arg0.getCode().getName());
			}
			
		});
		
		button.addEventHandler(MouseEvent.MOUSE_CLICKED	, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("mouse event:" + arg0.getX() + "," + arg0.getY());
			}
		});
		button.addEventHandler(MouseEvent.MOUSE_ENTERED	, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("mouse entered:" + arg0.getX() + "," + arg0.getY());
			}
		});
		button.addEventHandler(MouseEvent.MOUSE_EXITED	, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("mouse exited:" + arg0.getX() + "," + arg0.getY());
			}
		});
		
//		button.setStyle("width: 100px;height: 34px;color: #fff;letter-spacing: 1px;background: #3385ff;border-bottom: 1px solid #2d78f4;outline: medium;-webkit-appearance: none;-webkit-border-radius: 0;");
		button.setStyle("-fx-font: 30 arial; -fx-base: #ee2211;");
		pane.getChildren().add(button);
		Scene scene = new Scene(pane, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void scrollLayout(Stage primaryStage) {
		VBox box = new VBox(10);
		ScrollPane pane = new ScrollPane();
		pane.setPrefSize(400, 400);//决定宽高
		pane.setFitToHeight(true);
		pane.setFitToWidth(true);
//		pane.setPannable(false);
		
//		pane.autosize();
//		pane.setHmin(300);
//		pane.setVmin(300);
		//1.webbrowser
//		WebView webview = new WebView();
//		WebEngine engine = webview.getEngine();
//		engine.loadContent("<hr><h1>Hello, Tom</h1>");
//		pane.setContent(webview);
		//2.imageview
		Image image = new Image(getClass().getResourceAsStream("a.png"));
		box.getChildren().add(pane);
		pane.setContent(new ImageView(image));
		
		Scene scene = new Scene(box, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void gridLayout(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 400, 400, Color.WHEAT);
		
		GridPane gpane = new GridPane();
		gpane.setAlignment(Pos.CENTER);//会中心对齐
		gpane.setPadding(new Insets(5));
		gpane.setHgap(5);//水平间隔
		gpane.setVgap(5);//垂直间隔
		
		ColumnConstraints col1 = new ColumnConstraints(100);//宽度限制
		ColumnConstraints col2 = new ColumnConstraints(50, 150, 300);
		col2.setHgrow(Priority.ALWAYS);
		gpane.getColumnConstraints().addAll(col1, col2);//观察约束
		
		Text show = new Text("Welcome");
		show.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		GridPane.setHalignment(show, HPos.CENTER);
		gpane.add(show, 0, 0, 2, 1);//colspan含义，
		
		//标签和按钮
		Label usernameL = new Label("username");
		TextField usernameF = new TextField("");
		Label passwordL = new Label("password");
		PasswordField  passwordF = new PasswordField ();
		Button button = new Button("confirm");
		GridPane.setHalignment(usernameL, HPos.RIGHT);
		gpane.add(usernameL, 0, 1);
		GridPane.setHalignment(passwordL, HPos.RIGHT);
		gpane.add(passwordL, 0, 2);
		GridPane.setHalignment(usernameF, HPos.LEFT);
		gpane.add(usernameF, 1, 1);
		GridPane.setHalignment(passwordF, HPos.LEFT);
		gpane.add(passwordF, 1, 2);
		GridPane.setHalignment(button, HPos.RIGHT);
		gpane.add(button, 1, 3);
		
		pane.setCenter(gpane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void menubarLayout(Stage primaryStage) {
		Group group = new Group();
		MenuBar bar = new MenuBar();
		bar.setLayoutX(0);//横纵坐标
		bar.setLayoutY(0);
		bar.setMinWidth(300);
		bar.setMinHeight(30);
		bar.prefWidthProperty().bind(primaryStage.widthProperty());
		Menu menu = new Menu();
		menu.setText("menu");//css修饰样式
		
		MenuItem item = new MenuItem("item1");
		MenuItem item2 = new MenuItem("item2");
		MenuItem item3 = new MenuItem("item3");
		menu.getItems().add(item);
		menu.getItems().add(item2);
		menu.getItems().add(item3);
		bar.getMenus().add(menu);//
		item.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("action" + event.getEventType().getName());
				System.out.println("source" + event.getSource());
			}
		});
		
		Scene scene = new Scene(group, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		BorderPane pane = new BorderPane();
		pane.prefHeightProperty().bind(scene.heightProperty());
		pane.prefWidthProperty().bind(scene.widthProperty());
		pane.setTop(bar);
		
		group.getChildren().add(bar);
		
		primaryStage.show();
	}

	private void borderPane(Stage primaryStage) {
		BorderPane dp = new BorderPane();
		Text text = new Text(40, 40, "这是一个字符串");
		dp.setTop(text);
		Button button = new Button("left");
		dp.setLeft(button);
		Button button2 = new Button("right");
		button2.setLayoutX(150);
		button2.setLayoutY(150);
		button2.setScaleX(3);//规模上增大
		button2.setScaleY(3);//规模上增大
		dp.setRight(button2);
		
		Button button3 = new Button("bottom");
		dp.setBottom(button3);
		
		Button button4 = new Button("center");
		dp.setCenter(button4);
		
		Scene scene = new Scene(dp, 400, 400, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 元素只是水平拓展
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月22日
	 * @Package application
	 * @return void
	 */
	private void hboxLayout(Stage primaryStage) {
		HBox box = new HBox(20);
		Text text = new Text(40, 50, "2018年世界杯");
		box.getChildren().add(text);
		Text text2 = new Text(40, 50, "在哪里举行？");
		box.getChildren().add(text2);
		TextField field = new TextField("");
		box.getChildren().add(field);
		TextField field2 = new TextField("");
		box.getChildren().add(field2);
		
		HBox.setHgrow(field, Priority.ALWAYS);
		HBox.setHgrow(field2, Priority.ALWAYS);
		
		HBox box2 = new HBox(20);
		TextField field3 = new TextField("");
		box2.getChildren().add(field3);
		
		Scene scene = new Scene(box, 400, 400, Color.WHEAT);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void listCanEvent(Stage primaryStage) {
		List<String> alist = new ArrayList<String>();
		ObservableList<String> list = FXCollections.observableArrayList(alist);
		list.addListener(new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> c) {
				System.out.println(c.toString());
				System.out.println("有修改操作！！");
			}
		});
		list.add("first element");
	}

	private void loginIdentify(Stage primaryStage) {
		Group group = before(primaryStage);
		User user = new User(new SimpleStringProperty("lsp"),
				new ReadOnlyStringWrapper("lishaoping"), new SimpleIntegerProperty(26));
		Text text = new Text(100, 100, "input password");
		PasswordField  field = new PasswordField ();//不能绑定,没有值改变事件
		field.setLayoutX(100);
		field.setLayoutY(140);
		user.getPassword().bind(field.textProperty());//绑定,主动寻求绑定的元素值是被动的，不能主动输入
		user.getPassword().addListener(new ChangeListener<String>() {//捕获非控制字符
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				System.out.println(oldValue);
				System.out.println(newValue);
				
			}
		});
		field.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {//会捕获控制性字符的输入
				System.out.println(arg0.getEventType().getName());
			}
			
		});
		
		group.getChildren().add(text);
		group.getChildren().add(field);
		primaryStage.show();
	}

	/**
	 * 构造因变----而不用自己去手动计算----绑定之后，直接取值即可....绑定，相当于给绑者注册了一个监听器
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月22日
	 * @Package application
	 * @return void
	 */
	private void bingsPropperty(Stage primaryStage) {
		SimpleStringProperty p = new SimpleStringProperty("initial val");
		SimpleStringProperty p2 = new SimpleStringProperty("initial val2");
		p2.bindBidirectional(p);
		System.out.println(p2.get());
		p.set("新的值");
		System.out.println(p2.get());
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
		User user = new User();
		user.setAge(intProp);
		intProp.set(intProp.add(4).get());;
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
