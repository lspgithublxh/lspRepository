package com.bj58.im.client.ClientTest.UI6;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.jim2mov.core.DefaultMovieInfoProvider;
import org.jim2mov.core.FrameSavedListener;
import org.jim2mov.core.ImageProvider;
import org.jim2mov.core.Jim2Mov;
import org.jim2mov.core.MovieInfoProvider;
import org.jim2mov.core.MovieSaveException;

import com.bj58.im.client.ClientTest.App;
import com.bj58.im.client.ClientTest.UI6.Client_PBetter.WriteThread;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.ImageUtils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 设计思想1：指令分发控制模块，   在本模块内，对UI程序提供，使得UI调用本模块时，只需要向指令分发控制模块输入“指令和参数”即可，后续工作直接让本模块完成，实现UI和本模块的完全解耦----甚至是一个指令消息队列。。。同时，分类处理+解耦让程序更清晰更容易拓展更精准拓展更便捷增删改。
 * 		·····如online:12567
 * 任务2：消息到来切换用户和新增头像
 * 事件处理3：非当前用户发送来消息切换用户
 * 事件处理4：点击切换用户
 * 5.和自己通信、点击联系人而通信、名字传输
 * 6.视频通信、文件传输、语音显示、视频播放、表情、多媒体文本
 * 7.除非二进制流--或者字符流：否则使用javafx--且不适用WebView对象做聊天UI就会很麻烦了。
 * 		--------------不必很好解决UI体验问题，但是解决后台问题
 *   ----图片展示：可做 ImageView 可编辑的容器--从而显示图片可以---否则用file形式上传图片--然后发送和显示
 *   ----视频播放：可做 MediaView
 *   ----图片-视频传输：知道大小，然后传，server读大小各字节
 *   ----一般文件传输
 *  8.局域网ip搜索：上线用户
 *  9.视频录制--本地。视频直播---使用老连接|使用新连接----不关心对方各种配置--而只需要打开新的窗口
 *  10.录音短传输--实时录音传输
 * @ClassName:Main2
 * @Description:
 * @Author lishaoping
 * @Date 2018年6月19日
 * @Version V1.0
 * @Package com.bj58.im.client.ClientTest.UI
 */
public class Main2 extends Application{

	static TargetDataLine td = null;
	static AudioFormat af;
	static {
		af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
		
		try {
			td = (TargetDataLine) AudioSystem.getLine(info);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
//		String path = App.class.getResource("").getPath();
//		path = path.replace("/target/classes", "/src/main/java") + "a.png";
//		System.out.println(path);
//		System.out.println(new FileInputStream("D:\\project\\my_project\\ClientTest\\src\\main\\java\\com\\bj58\\im\\client\\ClientTest\\a.png"));
//		System.out.println(this.getClass().getResourceAsStream("Message.class"));//
		stage = arg0;
		//初始化headimgMap
		String port = this.getParameters().getRaw().get(0);
		headImgMap.put("127.0.0.1:11345", "D:\\head.jpg");
		headImgMap.put("127.0.0.1:11567", "D:\\head2.jpg");
		headImgMap.put("127.0.0.1:11376", "D:\\head3.jpg");
		currentUser = "127.0.0.1:" + port;
		headImgMap.put("Self", currentUser);
		nameMap.put("127.0.0.1:11345", "水杯");
		nameMap.put("127.0.0.1:11567", "电源");
		nameMap.put("127.0.0.1:11376", "背包");
		//nameMap
		//可有可不有的：
		Map<String, Object> selfConfig = new HashMap<String, Object>();
		config.put(currentUser, selfConfig);
		talkingSpecial(arg0, port);
//		System.out.println(this.getParameters().getNamed());
		System.out.println(this.getParameters().getRaw().get(0));
		//只有使用滚动pane
		//socket的程序的生成
		
		Client_PBetter cp = new Client_PBetter(this);
		this.cp = cp;
		cp.startClient(port);
		System.out.println(Thread.currentThread().getClass());
		//新功能,保存聊天记录
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ObjectOutputStream out;
				while(true) {
					try {
						Thread.sleep(1000);
						out = new ObjectOutputStream(new FileOutputStream(new File("D:\\obj.txt")));
						
						out.writeObject(getNewConfig());
						Thread.sleep(1000 * 10);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}

			private Object getNewConfig() {
				Map<String, Map<String, Object>> config2 = new HashMap<String, Map<String, Object>>();
				for(String key : config.keySet()) {
					Map<String, Object> c = new HashMap<String, Object>();
					config2.put(key, c);
					Map<String, Object> c2 = config.get(key);
					for(String k2 : c2.keySet()) {
						if(!"Pane".equals(k2) && !"WriteThread".equals(k2)) {
							c.put(k2, c2.get(k2));
						}
					}
				}
				return null;
			}
		}).start();
		arg0.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
//				event.consume();
//				System.out.println(event.getSource());
//				System.out.println(event.getTarget());
//				System.out.println(event.getEventType());
				System.out.println(config);
				cp.offline();
				System.exit(1);//或者一个一个地关闭socket连接
			}
		});
	}

	private void scrollPane(Stage primaryStage) {
		ScrollPane pane = new ScrollPane();
		pane.setPrefHeight(200);
		
	}
	
	//a.自己的配置信息：只需要serverport + ip-port + 面板相关的信息             自己就是self/myself 
	//b.上线人----上线人的配置信息
	
	Map<String, Map<String, Object>> config = new HashMap<String, Map<String, Object>>();
	//c.当前用户名：CurrentUser
	String currentUser = "Self";
	
	Map<String, String> headImgMap = new HashMap<String, String>();
	
	Map<String, String> nameMap = new HashMap<String, String>();
	
	VBox boxleft = new VBox();
	
	Stage stage = null;
	
	private void talkingSpecial(Stage primaryStage, String port) throws FileNotFoundException {
		primaryStage.getIcons().add(new Image(new FileInputStream("D:\\project\\my_project\\ClientTest\\src\\main\\java\\com\\bj58\\im\\client\\ClientTest\\a.png")));//this.getClass().getResourceAsStream("../a.png"))
		HBox hbox = new HBox();
		//1.增加最左边的目录栏:空格label + 文本text方式
		VBox boxleftMost = new VBox();
		boxleftMost.setPrefWidth(50);
		boxleftMost.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY	, new Insets(0))));//new BackgroundFill(Color.BLACK, CornerRadii.EMPTY	, new Insets(0))
		//纯粹加一个头像
		justGetHeadImg(boxleftMost);
		String[] texts  = {"聊天记录", "联系人", "朋友圈", "公众号", "小程序"};
		for(String t : texts) {
			Label blank = new Label("");
			blank.setPrefHeight(20);
			boxleftMost.getChildren().add(blank);
			Text text = new Text(String.format("  %s  ", t));
			text.setFont(Font.font("宋体", FontWeight.BOLD, 12));
			text.setFill("聊天记录".equals(t) ? Color.rgb(0, 0xff, 0) : Color.WHITE);
			boxleftMost.getChildren().add(text);
			
		}
		int winWidth = 840;
		//2.增加头像栏， 根据历史对话消息增加头像;;有头像则有配置config,切换头像则强行遍历根据id或者名称找到用户对应的vbox行元素 
		
		boxleft.setPrefWidth(50);
//		for(int i = 0; i < 1; i++) {//只存自己一个而已---做一个标记
//			addHeadDuihua(boxleft, false);
//			//增加带文本的图片，或者加文字
//		}
		
//		Label b2 = new Label("");
//		b2.setPrefHeight(20);
//		boxleft.getChildren().add(b2);
//		getHeadImg(boxleft, 0, 0, false);
		hbox.getChildren().add(boxleftMost);
		hbox.getChildren().add(boxleft);
		//3.增加对话框栏
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
		Map<String, Object> configM = new HashMap<String, Object>();
		configM.put("Pane", group);
		configM.put("YPoint", jianPointYArr);
		configM.put("serverPort", port);
		config.put("Self", configM);//一个客户端的号：serverport和Tom来定义 TODO 以后可以通过登陆界面输入
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
		//增加一个文件发射栏
		MenuBar bar = getMenuBar(primaryStage, group, jianPointX, jianPointYArr);
		
		box.getChildren().add(bar);
		TextArea area = new TextArea();
		area.setPrefWidth(winWidth);
		area.setPrefHeight(100);
		final Map<String, String> pressedKeyMap = new HashMap<String, String>();
		area.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				String key = event.getCode().getName();
				if("Enter".equals(key) && "Ctrl".equals(pressedKeyMap.get("keyPressed"))) {
					writeTextMessage(group, jianPointX, (Double[])config.get(currentUser).get("YPoint"), area.getText());
					//发送
					WriteThread wt = (WriteThread) config.get(currentUser).get("WriteThread");
					wt.writeNow(area.getText());
					System.out.println(area.getText());
					saveMessage(area.getText(), 1);
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
//				double old = jianPointYArr[0];
//				printInput(group, jianPointX, jianPointYArr, area);
//				
//				group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
				writeTextMessage(group, jianPointX, (Double[])config.get(currentUser).get("YPoint"), area.getText());
				//发送
				WriteThread wt = (WriteThread) config.get(currentUser).get("WriteThread");
				wt.writeNow(area.getText());
				saveMessage(area.getText(), 1);
				area.clear();
			}
			
		});
		button.setAlignment(Pos.BOTTOM_RIGHT);
		
		box.getChildren().add(button);
		Scene scene = new Scene(hbox, winWidth, 530, Color.WHEAT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private MenuBar getMenuBar(Stage primaryStage, Pane group, double jianPointX, final Double[] jianPointYArr) {
		MenuBar bar = new MenuBar();
		Menu menu1 = new Menu("Picture");
		MenuItem item1 = new MenuItem("Choose");
		menu1.getItems().addAll(item1);
		bar.getMenus().add(menu1);
		item1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event.getEventType());
//				System.out.println(event.getSource());
				String filePath = chooseFile(primaryStage);
				if(filePath.toLowerCase().endsWith("jpg") || filePath.toLowerCase().endsWith("png") || filePath.toLowerCase().endsWith("bmp") ||
						filePath.toLowerCase().endsWith("gif")) {
				}else {
					return;
				}
				
				System.out.println(filePath);
//				writeTextMessage(group, jianPointX, jianPointYArr, filePath);
//				writeImageMessage(group, jianPointX, jianPointYArr, filePath);
				//1.此时的jianPointYArr可能被复制了---而不是引用 所以：要currentUser方式：(Double[])config.get(currentUser).get("YPoint")
				try {
//					writeImageMessage_Data(group, jianPointX, jianPointYArr, new FileInputStream(filePath));
					writeImageMessage_Data(group, jianPointX, (Double[])config.get(currentUser).get("YPoint"), new FileInputStream(filePath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				WriteThread wt = (WriteThread) config.get(currentUser).get("WriteThread");
				//发送媒体文件
				sendMediaFile(wt, filePath);
				//保存发送信息
//				saveMessage(filePath, 2);
				ByteArrayOutputStream ou = new ByteArrayOutputStream();
				byte[] d = new byte[1024];
				int l = 0;
				FileInputStream fi;
				try {
					fi = new FileInputStream(filePath);
					while((l = fi.read(d)) > 0) {
						ou.write(d, 0, l);
					}
					saveMediaMessage("pic", 1, 2, ou.toByteArray(), "");//还是定方案为图片依然保存到本地最好！！否则聊天使得内存暴涨
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

		});
		Menu menu2 = new Menu("Video");
		MenuItem item2 = new MenuItem("Choose");
		menu2.getItems().addAll(item2);
		bar.getMenus().add(menu2);
		item2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event.getEventType());
//				System.out.println(event.getSource());
				String filePath = chooseFile(primaryStage);
				if(!filePath.toLowerCase().endsWith("mp4")) {
					return;
				}
				System.out.println(filePath);
//				writeVideoMessage(group, jianPointX, jianPointYArr, filePath);
				writeVideoMessage(group, jianPointX, (Double[])config.get(currentUser).get("YPoint"), filePath);
				WriteThread wt = (WriteThread) config.get(currentUser).get("WriteThread");
				//发送媒体文件
				sendMediaFile(wt, filePath);
				//保存发送信息
//				saveMessage(filePath, 3);
				saveMediaMessage("vid", 1, 3, null, filePath);
			}
			
		});
		
		Menu menu3 = new Menu("File");
		MenuItem item3 = new MenuItem("Choose");
		menu3.getItems().addAll(item3);
		item3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String filePath = chooseFile(primaryStage);
				try {
					writeFileMessage(group, jianPointX, (Double[])config.get(currentUser).get("YPoint"), new FileInputStream("D:\\file12.jpg"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				group.getChildren().get(group.getChildren().size() - 2).setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						String rs = chooseFile_perament(primaryStage, filePath);
					}
				});
				WriteThread wt = (WriteThread) config.get(currentUser).get("WriteThread");
				//发送媒体文件
				sendMediaFile(wt, filePath);
				saveMediaMessage("vid", 1, 4, null, filePath);
			}
		});
		bar.getMenus().add(menu3);
		
		Menu menu4 = new Menu("Camera");
		MenuItem item4 = new MenuItem("拍照");
		item4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("调用摄像头");
				stage.setHeight(500);
				stage.setWidth(500);
				cameraPictureAndSave(stage);
			}
		});
		MenuItem item42 = new MenuItem("视频录制");
		item42.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("调用摄像头");
				stage.setHeight(500);
				stage.setWidth(500);
				luzhiVideo(stage);
			}
		});
		menu4.getItems().addAll(item4, item42);
		bar.getMenus().add(menu4);
		Menu menu5 = new Menu("Live");//视频直播
		MenuItem item5 = new MenuItem("Open");
		menu5.getItems().addAll(item5);
		item5.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("视频直播");
				stage.setHeight(500);
				stage.setWidth(500);
				live(stage);
			}
		});
		bar.getMenus().add(menu5);
		
		Menu menu6 = new Menu("Audio");
		MenuItem item6 = new MenuItem("Open");
		menu6.getItems().addAll(item6);
		item6.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("录音");
				stage.setHeight(500);
				stage.setWidth(500);
				audioOperate(stage);
			}

		});
		MenuItem item7 = new MenuItem("Live");
		menu6.getItems().addAll(item7);
		item7.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("语音直播");
				stage.setHeight(500);
				stage.setWidth(500);
				audioLive(stage);
			}

		});
		bar.getMenus().add(menu6);
		
		return bar;
	}
	
	Object lock = new Object();
	Object lockLuzhi = new Object();
	Object locklive = new Object();
	Object lockVideo = new Object();
	Image cacheImage = null;
	Boolean stop = false;
	Boolean luzhi_start = false;
	Boolean live_start = false;
//	BlockingQueue<BufferedImage> imageQu = new LinkedBlockingQueue<BufferedImage>(500);
	List<BufferedImage> imageList = new ArrayList<>();
	
	
	private void audioLive(Stage stage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Button button = new Button("开始录音");
		button.setLayoutX(100);
		button.setLayoutY(200);
		Button button2 = new Button("停止录音");
		button2.setLayoutX(150);
		button2.setLayoutY(200);
		button2.setDisable(true);
		root.getChildren().addAll(button, button2);
		button.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, new Insets(0))));
		Map<String, WriteThread> swt = new HashMap<>();
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				button.setDisable(true);
				button2.setDisable(false);
				//开始传输录音数据
				if(!td.isOpen()) {
					try {
						td.open(af);
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				}
				WriteThread luyingwt = null;
				int times = 1;
				if(!swt.containsKey(currentUser)) {//缓冲是最好的
					try {
						Map<String, Object> cconfig = config.get(currentUser);
						Socket socket = new Socket((String)cconfig.get("serverIP"), Integer.valueOf((String) cconfig.get("serverPort")));
						luyingwt = cp.new WriteThread(socket.getOutputStream());
						luyingwt.start();
						swt.put(currentUser, luyingwt);
						times = 0;
					}catch (Exception e) {
					}
				}
				luyingwt = swt.get(currentUser);//TODO 同步需要
				synchronized (lockVideo) {
					td.start();
					while(true) {
						System.out.println("continue");
						if(button2.isDisable()) {
							break;
						}
						byte[] da = new byte[6144];
						int len = td.read(da, 0, da.length);//发送得少，实时也要发送
						//直接发送,一帧一帧的传
						sendVideoLive(da, luyingwt, "video_live", (times + 1) + "", len, times == 0);//"videoLive" + System.currentTimeMillis() + ".mp3"
						times++;
					}
					td.stop();
				}
			}
		});
		button2.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, new Insets(0))));
		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				button2.setDisable(true);
				button.setDisable(false);
				//中断传输
			}
		});
		stage.setScene(scene);
		stage.show();
	}
	
	private void audioOperate(Stage stage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Button button = new Button("开始录音");
		button.setLayoutX(100);
		button.setLayoutY(200);
		root.getChildren().addAll(button);
		button.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, new Insets(0))));
		//录音设置部分
		
		String currentUser_click = currentUser;
		TargetDataLine td2 = td;
		Object readLock = new Object();
		Boolean[] readGoon = {true};
		button.setOnMousePressed(new EventHandler<MouseEvent>() {//执行完了，release事件才会执行
			@Override
			public void handle(MouseEvent e) {
				button.setBackground(new Background(new BackgroundFill(Color.SPRINGGREEN, new CornerRadii(1), new Insets(1))));
				//采集信号，读取,传输
				System.out.println("录音下一轮");
				synchronized (readLock) {//此时上一轮  这一步  还没有读完，所以不能继续
					if(!readGoon[0]) {
						return;
					}
				}
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							if(!td2.isOpen()) {
								td2.open(af);
							}
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							synchronized (lockVideo) {
								td2.start();
								while(true) {
									System.out.println("continue");
									synchronized (readLock) {
										if(!readGoon[0]) {
											readGoon[0] = true;
											break;
										}
									}
									byte[] da = new byte[3072];
									int len = td2.read(da, 0, da.length);
									out.write(da, 0, len);
								}
								td2.stop();
							}
							byte[] data = out.toByteArray();
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									//本地显示
									writeVideoPartMessage_Data((Pane)config.get(currentUser).get("Pane"), 80, (Double[])config.get(currentUser).get("YPoint"), data);
									//保存消息--同样绑定用户
									saveVideoPartMessage("videoPart", 1, 11, data, currentUser);
								}
							});
							//传输
							System.out.println(out.size());
							sendVideoPart(data, currentUser);//绑定当前用户--一个录音对应一个对方
						} catch (LineUnavailableException e1) {
							e1.printStackTrace();
						}
					}
				}).start();
			}
		});
		button.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, new Insets(0))));
				//结束读取，开始传输
				System.out.println("release");
				synchronized (readLock) {//不能施加lockVideo因为要同步
					readGoon[0] = false;
				}
			}
		});
		stage.setScene(scene);
		stage.show();
	}
	
	private void live(Stage primaryStage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Webcam came = Webcam.getDefault();//只有一个摄像头System.out.println(Webcam.getWebcams().size());
		came.open();
		Button button = new Button("直播");
		button.setLayoutX(100);
		button.setLayoutY(200);
		root.getChildren().addAll(button);
		//内部不能却不能动态加东西--无线循环
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("click listen");
				if(live_start) {
					button.setDisable(true);
					button.setText("直播");
				}else {
					button.setText("中断直播");
				}
				synchronized (locklive) {
					live_start = !live_start;	
					if(!live_start) {
						try {
							locklive.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}else {
						locklive.notify();
					}
					
				}
				button.setDisable(false);
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if(!live_start) {
						synchronized (locklive) {
							locklive.notify();
							try {
								locklive.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					System.out.println("add image");
					BufferedImage bi = came.getImage();
					Image image2 = SwingFXUtils.toFXImage(bi, new WritableImage(100, 100));
					
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
					//打开新线程， 发送图片--新线程里：是否等待/同步/异步:异步最好
					sendLiveImage(bi);
				}
			}

		}).start();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void sendVideoLive(byte[] video, WriteThread wt, String type, String filename, int length, boolean first) {
		try {
			if(first) {
				sendByteArray(wt, video, filename, String.format("%s|%s",type, headImgMap.get("Self")));//发送自己的ip
			}else {
				sendByteArray_pure(wt, video, length);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	private void sendVideoPart(byte[] video, String username) {
		Map<String, Object> cconfig = config.get(username);
		Socket socket;
		try {
			socket = new Socket((String)cconfig.get("serverIP"), Integer.valueOf((String) cconfig.get("serverPort")));
			WriteThread wt = this.cp.new WriteThread(socket.getOutputStream());
			wt.start();
			sendByteArray(wt, video, "video" + System.currentTimeMillis() + ".mp3", String.format("video_part|%s", headImgMap.get("Self")));//发送自己的ip
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendLiveImage(BufferedImage bi) {
		//先发送指令
		byte[] data = ImageUtils.toByteArray(bi, "jpg");
		Map<String, Object> cconfig = config.get(currentUser);
		Socket socket;
		try {
			socket = new Socket((String)cconfig.get("serverIP"), Integer.valueOf((String) cconfig.get("serverPort")));
			WriteThread wt = this.cp.new WriteThread(socket.getOutputStream());
			wt.start();
			sendByteArray(wt, data, "live" + System.currentTimeMillis() + ".jpg", String.format("live_image|%s", headImgMap.get("Self")));//发送自己的ip
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void luzhiVideo(Stage primaryStage) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Webcam came = Webcam.getDefault();//只有一个摄像头System.out.println(Webcam.getWebcams().size());
		came.open();
		Button button = new Button("开始录制");
		button.setLayoutX(100);
		button.setLayoutY(200);
		Button button2 = new Button("结束录制");
		button2.setLayoutX(180);
		button2.setLayoutY(200);
		button2.setDisable(true);
		Button button3 = new Button("查看文件");
		button3.setLayoutX(260);
		button3.setLayoutY(200);
		button3.setDisable(true);
		String[] fileNames = new String[] {"", ""};
		button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				FileChooser chooser = new FileChooser();
				chooser.setInitialDirectory(new File(fileNames[0]));
				chooser.setInitialFileName(fileNames[1]);
				chooser.setTitle("选择录制的视频");
				chooser.showOpenDialog(primaryStage);
			}
		});
		root.getChildren().addAll(button, button2, button3);
		long[] t1 = {0};
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				button.setDisable(true);
				button2.setDisable(false);
				luzhi_start = true;
				t1[0] = System.currentTimeMillis();
				button3.setDisable(true);
			}
		});
		button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("点击激活");
				button.setText("重新录制");
				button.setDisable(false);
				button2.setDisable(true);
				luzhi_start = false;
				//只运行一次
				String fn = "luzhi" + System.currentTimeMillis() + ".mp4";
				String filename = "file://D:/cache1/luzhi" + fn;
				fileNames[0] = "D:\\cache1\\";
				fileNames[1] = fn;
				button3.setDisable(true);
				startSaveTofile(filename, button3);
				
			}
		});
		//显示
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//
				while(true) {
					if(luzhi_start) {
						if(System.currentTimeMillis() - t1[0] > 8 * 1000) {
							luzhi_start = false;
//							button2.fireEvent(new MouseEvent(null, null, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));
						}
					}
					BufferedImage bi = came.getImage();
					Image image2 = SwingFXUtils.toFXImage(bi, new WritableImage(100, 100));
					
					ImageView view = new ImageView(image2);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if(pane.getChildren().size() > 0) {
								pane.getChildren().set(0, view);
							}else {
								pane.getChildren().add(view);
							}
							isGoon_luzhi(new ImageView(image2), root, bi);
						}
					});
				}
			}
		}).start();
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
					view.setLayoutY(250);
					view.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if(event.isPopupTrigger()) {
								createdropDownMenuHandler(primaryStage, event, view);
								//弹出小窗口 显示成功提示
							}
						}
					});
					if(root.getChildren().size() == 2) {
						root.getChildren().add(view);
					}else if(root.getChildren().size() >= 3){
						root.getChildren().set(2, view);
					}
					
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
	
	
	private void isGoon_luzhi(ImageView view, Group root, BufferedImage bi) {
		if(luzhi_start) {
			view.setLayoutY(250);
			//会耗时一点, 先加，后保存，而不是实时保存-----当然可以做到:但不好:帧数
			imageList.add(bi);//不阻塞
			System.out.println("当前元素个数：" + imageList.size());
			if(root.getChildren().size() == 4) {
				root.getChildren().add(view);//
			}else if(root.getChildren().size() >= 5){
				root.getChildren().set(4, view);
			}
			//
		}
	}
	
	private void startSaveTofile(String file, Button button3) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DefaultMovieInfoProvider p = new DefaultMovieInfoProvider(file);
				p.setFPS((float) (100 / 3.6));//帧频率  每秒钟10帧
				p.setMWidth(320);
				p.setMHeight(240);
				p.setNumberOfFrames(imageList.size());//帧数，无限
				long t1 = System.currentTimeMillis();
				try {
					Jim2Mov jm = new Jim2Mov(new ImageProvider() {
						@Override
						public byte[] getImage(int arg0) {
							System.out.println("生产第" + arg0 + "帧");
							byte[] d = null;
							d = ImageUtils.toByteArray(imageList.get(arg0), "jpg");//阻塞方式
							System.out.println(d.length);
							return d;
						}}, p, 
					new FrameSavedListener() {
						@Override
						public void frameSaved(int arg0) {
						System.out.println("保存了第" + arg0 + "帧");
					}});
					jm.saveMovie(MovieInfoProvider.TYPE_QUICKTIME_JPEG);//TYPE_QUICKTIME_JPEG
				} catch (MovieSaveException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis() - t1);
				System.out.println("保存完毕");
				button3.setDisable(false);
			}
		}).start();
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
	
	private void createdropDownMenuHandler(Stage primaryStage, MouseEvent event, ImageView view) {
		ContextMenu cm = new ContextMenu();
		MenuItem item = new MenuItem("保存");
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					File f = new File("D:\\cache1\\save" + System.currentTimeMillis() + ".jpg");
					ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(), new BufferedImage((int)view.getImage().getWidth(), (int)view.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB)), "PNG", f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		MenuItem item1 = new MenuItem("另存为");
		item1.setOnAction(new EventHandler<ActionEvent>() {
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
		item2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				BufferedImage bimage = SwingFXUtils.fromFXImage(view.getImage(), new BufferedImage((int)view.getImage().getWidth(), (int)view.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB));
				try {
					ImageIO.write(bimage, "png", output);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//output里的数据可以发送了。
				//打开新窗口， 展示
				createWindow(output.toByteArray(), primaryStage);
			}

		});
		cm.getItems().add(item);
		cm.getItems().add(item1);
		cm.getItems().add(item2);
		cm.setAnchorX(event.getScreenX());
		cm.setAnchorY(event.getScreenY());
		cm.show(primaryStage);
	}
	
	private void createWindow(byte[] data, Stage primaryStage) {
		Stage stage = new Stage();
		stage.setTitle("选择好友");
		stage.setHeight(300);
		stage.setWidth(200);
		Group root = new Group();
		
		VBox box = new VBox(10);
		Pane show_confirm = new Pane();
		show_confirm.setPrefWidth(200);
		show_confirm.setPrefHeight(300);
		HBox seletedText = new HBox(4);
		seletedText.setLayoutY(120);
		List<String> sendUserIdList = new ArrayList<String>();
		for(String keyname : config.keySet()) {
			if("Self".equals(keyname) || keyname.equals(headImgMap.get("Self"))) {
				continue;
			}
			Map<String, Object> sconfig = config.get(keyname);
			HBox user1 = new HBox(5);
			ImageView head = justGetHeadImg_pure(headImgMap.get(keyname));//获取当前圆头像 headImgMap.get(headImgMap.get("Self"))
			Text name = new Text(nameMap.get(keyname));
			VBox kong = new VBox();
			Label b = new Label(" ");
//			b.setPrefHeight(20);
			kong.getChildren().add(b);
			kong.getChildren().add(name);
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
				int clickCount = 0;
				@Override
				public void handle(MouseEvent event) {
					clickCount++;
					if(clickCount > 1) {
						return;
					}
					addUserHandle(keyname, seletedText, name);
					sendUserIdList.add(keyname);
					int index = seletedText.getChildren().size() - 1;
					Text last = (Text) seletedText.getChildren().get(index);
					last.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if(event.isPopupTrigger()) {
								clickCount = 0;
								Iterator<Node> i = seletedText.getChildren().iterator();
								while(i.hasNext()) {
									Text  n = (Text) i.next();
									if(n.getText().equals(last.getText())) {
										i.remove();
										break;
									}
								}
							}
						}
					});
				}
			};
			user1.getChildren().add(head);
			user1.getChildren().add(kong);
			user1.setOnMouseClicked(handler);
			box.getChildren().add(user1);//放后面才会生效
		}
		show_confirm.getChildren().add(box);
		Button button = new Button("确定");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("切换pane + 发送图片");
				for(String userid : sendUserIdList) {
					
					Pane group = (Pane) config.get(userid).get("Pane");
					//切换，显示，发送
					HBox hbox = (HBox) config.get(userid).get("Hbox");
					if(hbox != null) {
						if(currHBox != null) {
							currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
						hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
						changePane(hbox, userid);
					}
					writeImageMessage_Data(group, 80, (Double[])config.get(userid).get("YPoint"), new ByteArrayInputStream(data));
					
					WriteThread wt = (WriteThread) config.get(userid).get("WriteThread");
					//发送媒体文件
					sendByteArray(wt, data, "camera" + System.currentTimeMillis() + ".jpg", "trans_file");
					//保存发送信息
//					saveMessage(filePath, 2);
					saveMediaMessage("pic", 1, 2, data, "");//还是定方案为图片依然保存到本地最好！！否则聊天使得内存暴涨
					//2.发送图片
					//关闭窗口
					stage.close();
					primaryStage.close();
				}
				
			}
		});
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getSceneX());
			}
		});
		button.setLayoutY(150);
		show_confirm.getChildren().add(seletedText);
		show_confirm.getChildren().add(button);
		root.getChildren().add(show_confirm);//放后面会生效,放一个pane是对的，多了前面的pane事件不生效
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private void addUserHandle(String keyname, HBox seletedText, Text name) {
		Text t = new Text(nameMap.get(keyname));
		t.setLayoutY(name.getLayoutY());
		t.setLayoutX(seletedText.getChildren().size() * 20);//个数增加
		seletedText.getChildren().add(t);
	}
	
	private void sendByteArray_pure(WriteThread wt, byte[] data, int lenth) {
		System.out.println("start --- send data pure---:");
		for(int i = 0; i < lenth; i+= 1024) {
			int end = i + 1024;
			byte[] buf = new byte[1024];
			if(i + 1024 > data.length) {
				end = data.length;
			}
			for(int j = i; j < end; j++) {
				buf[j - i] = data[j];
			}
			wt.writeByteArray(buf);//
		}
		System.out.println("end --- send data pure");
	}
	
	private void sendByteArray(WriteThread wt, byte[] data, String filename, String type) {
		String cmd = String.format("%s|%s|%s|%s|", type, filename, "pic", data.length);
		byte[] front = new byte[1024];//限制1024
		byte[] content = cmd.getBytes();
		for(int i = 0; i < content.length; i++) {//都是正数
			front[i] = content[i];
		}
		for(int i = content.length; i < 1024; i++) {
			front[i] = 0;
		}
		//发送内容
		wt.writeNow(new String(front));
		//发送文件
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("start --- send data---:");
		for(int i = 0; i < data.length; i+= 1024) {
			int blankNeed = 0;
			int end = i + 1024;
			byte[] buf = new byte[1024];
			if(i + 1024 > data.length) {
				blankNeed = data.length - i;
				end = data.length;
			}
			for(int j = i; j < end; j++) {
				buf[j - i] = data[j];
			}
			wt.writeByteArray(buf);//
		}
//		wt.writeByteArray(data);//会不会遗漏？会
		System.out.println("end --- send ");
	}
	
	private void sendMediaFile(WriteThread wt, String filePath) {
		String type = filePath.toLowerCase().endsWith("jpg") || 
				filePath.toLowerCase().endsWith("png") || 
				filePath.toLowerCase().endsWith("bmp") ||
				filePath.toLowerCase().endsWith("gif") ? "pic" : filePath.toLowerCase().endsWith("mp4") ? "ved" : "file";
		File f = new File(filePath);
		long len = f.length();
		String cmd = String.format("trans_file|%s|%s|%s|", filePath.substring(filePath.lastIndexOf("\\") + 1), type, len);
		byte[] front = new byte[1024];//限制1024
		byte[] content = cmd.getBytes();
		for(int i = 0; i < content.length; i++) {//都是正数
			front[i] = content[i];
		}
		for(int i = content.length; i < 1024; i++) {
			front[i] = 0;
		}
//		wt.writeByteArray(front);
		wt.writeNow(new String(front));
		//发送文件
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("start --- send data---:");
		FileInputStream in;
//		wt.writeNow(new String(front));//无法收到了
//		wt.writeByteArray(front);//无法收到了
		try {
			in = new FileInputStream(f);
			int s_len = 0;
			byte[] buf = new byte[1024];
			while((s_len = in.read(buf)) > 0) {
				if(s_len < 1024) {//充0补齐
					for(int i = s_len; i < 1024; i++) {
						buf[i] = 0;
					}
				}
				wt.writeByteArray(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end --- send ");
	}

	private void writeVideoMessage(Pane group, double jianPointX, Double[] jianPointYArr, String filePath) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawVideoContent(group, jianPointX, jianPointYArr[0], filePath);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	private String chooseFile_perament(Stage primaryStage, String filePath) {
		FileChooser filec = new FileChooser();
		filec.setTitle("select the file");
		filec.setInitialDirectory(new File(filePath.substring(0, filePath.lastIndexOf("\\") + 1)));
		filec.setInitialFileName(filePath.substring(filePath.lastIndexOf("\\") + 1));
		filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("类型", filePath.substring(filePath.lastIndexOf("\\") + 1)));
		
		File file = filec.showOpenDialog(primaryStage);
		return file.getAbsolutePath();
	}
	
	private String chooseFile(Stage primaryStage) {
		FileChooser filec = new FileChooser();
		filec.setTitle("open a file");
		filec.setInitialDirectory(new File("D:\\"));
		filec.setInitialFileName("D:\\a.png");
		
		filec.selectedExtensionFilterProperty().addListener(new ChangeListener<ExtensionFilter>() {

			@Override
			public void changed(ObservableValue<? extends ExtensionFilter> observable, ExtensionFilter oldValue,
					ExtensionFilter newValue) {
				System.out.println(observable.getValue().getDescription());
//				System.out.println(oldValue.getDescription());
			}
		});
		filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("ALL Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", ".jpg"),
				new FileChooser.ExtensionFilter("PNG", ".png"),
				new FileChooser.ExtensionFilter("GIF", ".gif"),
				new FileChooser.ExtensionFilter("BMP", ".bmp"));
		File file = filec.showOpenDialog(primaryStage);
		System.out.println("get file :" + file.getAbsolutePath());
		return file.getAbsolutePath();
	}
	
	private void addHeadDuihua(VBox boxleft, boolean isRight) {
		Label b = new Label("");
		b.setPrefHeight(20);
		boxleft.getChildren().add(b);
		getHeadImg(boxleft, 0, 0, isRight);
	}
	
	private void saveVideoPartMessage(String text, int direction, int type, byte[] data, String username) {
		List<Message> mesList = (List<Message>) config.get(username).get("message");
		Message mes = new Message(mesList.size() + 1, text, type, direction);
		if(type == 11){
			mes.params.put("videoPart", data);
		}
		mesList.add(mes);
	}
	
	private void saveMediaMessage(String text, int direction, int type, byte[] data, String savePath) {
		List<Message> mesList = (List<Message>) config.get(currentUser).get("message");//TODO currentUser或许可以用text来替换
		Message mes = new Message(mesList.size() + 1, text, type, direction);
		if(type == 2) {
			mes.params.put("picData", data);
		}else if(type == 3) {
			mes.params.put("videoUrl", savePath);
		}else if(type == 11){
			mes.params.put("videoPart", data);
		}else {
			mes.params.put("picUrl", savePath);
		}
		mesList.add(mes);
	}
	
	private void saveMessage(String text, int direction) {
		List<Message> mesList = (List<Message>) config.get(currentUser).get("message");
		mesList.add(new Message(mesList.size() + 1, text, 1, direction));
	}
	
	Client_PBetter cp = null;
	Map<String, Object[]> liveConfig = new HashMap<>();
	Map<String, Object[]> videoLiveConfig = new HashMap<>();
	/**
	 * 对外提供的第二个接口
	 * @param  有问题的方法：因为80 和 drawContentRight矛盾
	 * @author lishaoping
	 * @Date 2018年6月14日
	 * @Package com.bj58.im.client.ClientTest.UI
	 * @return void
	 */
	public void writeLeftTextMessage(String username, String content) {
		//提取名称：
//		WriteThread wt = (WriteThread) config.get(username).get("WriteThread");
//		wt.writeNow("ok, ui send message!");
//		config.get(username).put("WriteThread", wt);
		Map<String, Object> conf = config.get(username);
		Pane group = (Pane) conf.get("Pane");
		final Double[] jianPointYArr = (Double[]) conf.get("YPoint");
		double old = jianPointYArr[0];
		
		jianPointYArr[0] = drawContentRight(group, 80, jianPointYArr[0], content);
		getHeadImg(group, 80, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	/**
	 * 消息处理中心,但是如果要直接执行，需要PlatForm.runlater
	 * @param 
	 * @author lishaoping
	 * @Date 2018年6月19日
	 * @Package com.bj58.im.client.ClientTest.UI2
	 * @return void
	 */
	public void cmdHandleCenter(String username, String cmd_param, Object[] entity) {
		String[] cmdParam = cmd_param.split("_");
		System.out.println("received_message:    " + cmdParam[0]);
		if("online".equals(cmdParam[0])) {
			
		}else if("videoLive".equals(cmdParam[0])) {
			boolean changePane = false;
			if(!username.equals(currentUser)) {
				changePane = true;
			}
			currentUser = username;//只changePane不生产东西
			//弹出窗口，展示波形，写入音响
			receiveVideoLive(username, changePane, (byte[])entity[0],  cmdParam[1]);
		}
		else if("videoPart".equals(cmdParam[0])){
			boolean changePane = false;
			if(!username.equals(currentUser)) {
				changePane = true;
			}
			currentUser = username;//这种设置方式并不好---会导致并发冲突--最好直接用--而不是赋值：因为可能被覆盖  TODO
			receiveVideo(username, changePane, (byte[])entity[0]);
		}else if("liveImage".equals(cmdParam[0])){
			//打开窗口，加进图片
			if(cmdParam[1].equals("1")) {//首次传图
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Stage stage_ca = new Stage();
						stage_ca.setTitle("对方直播");
						stage_ca.setHeight(500);
						stage_ca.setWidth(500);
						Pane p = liveShowReceived(stage_ca, username, entity);//TODO 修改 stage 为stage_ca
						liveConfig.put(username, new Object[] {stage_ca, p});
					}
				});
				
			}else if(liveConfig.containsKey(username)){
				Pane pane = (Pane)(((Object[])liveConfig.get(username))[0]);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ImageView view = new ImageView(new Image(new ByteArrayInputStream((byte[]) entity[0])));
						if(pane.getChildren().size() > 0) {
							pane.getChildren().set(0, view);
						}else {
							pane.getChildren().add(view);
						}
					}
					
				});
			}
		}else if("transFile".equals(cmdParam[0])){
			boolean changePane = false;
			if(currentUser != username) {
				changePane = true;
			}
			currentUser = username;
			receivedMediaMessage(username, "pic".equals(cmdParam[1]) ? (byte[])entity[0] : null,  cmdParam[2], cmdParam[1], changePane, (String) entity[1]);
		}else if("closeWindow".equals(cmdParam[0])) {//此时username是server
			//username是服务器， entity是下线的机器-客户端
			HBox hbox = (HBox) config.get((String)entity[0]).get("Hbox");//此时username是server
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					offLineOne(hbox);
				}
				
			});
			config.remove((String)entity[0]);//移除用户动态配置--剩余操作以后
		}else if("offline".equals(cmdParam[0])) {
			
		}else if("clientToMe".equals(cmdParam[0])) {//第二个ui被动接受连接
			addWriteThread(username, entity);
			receivedMessage(username, "上线提醒", false);
			System.out.println("真正知道对方的server ip-port了");
			currentUser = username;
			config.get(username).put("message", new ArrayList<Message>());
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					addHeadDuihua(boxleft, true);
				}
			});
		}else if("readClient".equals(cmdParam[0])) {//读取到另一个client发来的消息,,,以后每次对话，两方都是这里获取到数据的
			// 通信的当前对方， 已经改变了--是新用户了，那么要切换pane--当前用户为准
			boolean changePane = false;
			if(currentUser != username) {
				changePane = true;
			}
			receivedMessage(username, (String)entity[0], changePane);
		}else if("clientServerPort".equals(cmdParam[0])) {//获取到另一个客户端的server的port,并主动连接
			addWriteThread(username, entity);
			currentUser = username;
			config.get(username).put("message", new ArrayList<Message>());
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					addHeadDuihua(boxleft, true);
				}
			});
		}else if("accept".equals(cmdParam[0])) {//对方server ip-port此时还不知道
			System.out.println("new socket in");
//			addWriteThread(username, entity);
//			receivedMessage(username, "上线提醒");
//			currentUser = username;
		}
	}

	Object videoLiveReLock = new Object();
	Object imageShowLock = new Object();
	SourceDataLine sd;
	
	
	private void receiveVideoLive(String username, boolean changePane, byte[] bs, String times) {
		//打开窗口，加进图片
		System.out.println("times:" + times);
		if(times.equals("1")) {//首次传图
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, af);
					try {
						sd = (SourceDataLine) AudioSystem.getLine(info2);
						if(!sd.isOpen()) {
							sd.open(af);
						}
						synchronized (videoLiveReLock) {
							System.out.println("写入音响");
							sd.start();
							sd.write(bs, 0, bs.length);
							sd.close();
						}
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
					
					Stage stage_ca = new Stage();
					stage_ca.setTitle("对方直播");
					stage_ca.setHeight(500);
					stage_ca.setWidth(500);
					Pane p = videoLiveShowReceived(stage_ca, username, bs);
					videoLiveConfig.put(username, new Object[] {stage_ca, p});
				}
			});
		}else if(videoLiveConfig.containsKey(username)){//肯定不是第一次传数据了
			synchronized (videoLiveReLock) {
				System.out.println("写入音响");
				sd.start();
				sd.write(bs, 0, bs.length);
				sd.close();
			}
			//画图耗费-
			Pane pane = (Pane)(((Object[])videoLiveConfig.get(username))[1]);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					synchronized (imageShowLock) {
						LineChart<Number, Number> chart = (LineChart<Number, Number>) pane.getChildren().get(0);
//						chart.getData().clear();//先不累加
						Series<Number, Number> se = new XYChart.Series<>();
						ObservableList old = chart.getData();
						int sc = 0;
						for(byte d : bs) {
							if(sc > 100) break;//生长曲线更好看
							se.getData().add(new Data<Number, Number>(++sc, d));
						}
						System.out.println("remove data for chart");
						chart.getData().removeAll(old);
						System.out.println("add data for chart");
						chart.getData().add(se);
//						try {
//							Thread.sleep(1000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
					}
					
//					chart.getData().removeAll(old);
//					if(pane.getChildren().size() > 0) {
//						pane.getChildren().set(0, chart);
//					}else {
//						pane.getChildren().add(chart);
//					}
					
				}
				
			});
		}
	}

	private void receiveVideo(String username, boolean changePane, byte[] bs) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("------changePane:" + changePane);
				if(changePane) {
					HBox hbox = (HBox) config.get(username).get("Hbox");
					if(hbox != null) {
						if(currHBox != null) {
							currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
						hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
						changePane(hbox, username);
					}
				}
				writeRightMediaMessage(username, null, bs, "videoPart", null);
				saveVideoPartMessage(username, 2, 11, bs, username);
			}
		});
	}

	private Pane audioShowReceived(Stage stage2, String username, Object[] entity) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		ByteArrayInputStream ins = new ByteArrayInputStream((byte[]) entity[0]);
//		AudioInputStream ais = new AudioInputStream(ins, af, da.length / af.getFrameSize());
		return null;

	}
	
	private Pane videoLiveShowReceived(Stage stage2, String username, byte[] data) {
		//先写入麦克风
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		
		if(pane.getChildren().size() > 0) {
			LineChart<Number, Number> chart = (LineChart<Number, Number>) pane.getChildren().get(0);
			chart.getData().clear();//先不累加
			Series<Number, Number> se = new XYChart.Series<>();
			int sc = 0;
			for(byte d : data) {
				se.getData().add(new Data<Number, Number>(++sc, d));
			}
			chart.getData().add(se);
			pane.getChildren().set(0, chart);
		}else {
			LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
			chart.autosize();
			chart.setCreateSymbols(false);
			Series<Number, Number> se = new XYChart.Series<>();
			int sc = 0;
			for(byte d : data) {
				se.getData().add(new Data<Number, Number>(++sc, d));
			}
			chart.getData().add(se);
			pane.getChildren().add(chart);
		}
		stage2.setScene(scene);
		try {
			stage2.getIcons().add(new Image(new FileInputStream(headImgMap.get(username))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stage2.show();
		return pane;
	}
	
	private Pane liveShowReceived(Stage stage2, String username, Object[] entity) {
		Group root = new Group();
		Pane pane = new Pane();
		pane.setMaxHeight(300);
		root.getChildren().add(pane);
		Scene scene = new Scene(root, 500, 600, Color.rgb(0x11, 0x11, 0x11, 0.1));
		ByteArrayInputStream ins = new ByteArrayInputStream((byte[]) entity[0]);
		ImageView view = new ImageView(new Image(ins));
		if(pane.getChildren().size() > 0) {
			pane.getChildren().set(0, view);
		}else {
			pane.getChildren().add(view);
		}
		stage2.setScene(scene);
		try {
			stage2.getIcons().add(new Image(new FileInputStream(headImgMap.get(username))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		stage2.show();
		return pane;
	}

	private void offLineOne(HBox hbox) {
		VBox box = (VBox) hbox.getParent();
		int i = 0;
		HBox hx = null;
		for(; i < box.getChildren().size() - 1; i += 2) {
//			Node label = box.getChildren().get(i);
			hx = (HBox) box.getChildren().get(i + 1);
			if(hx == hbox) {
				box.getChildren().remove(i);
				box.getChildren().remove(i);//此时就是下一个元素
				break;
			}
		}
		//移除用户会话config
		//相同才需要切换---不同不用
		if(box.getChildren().size() == 0) {
			Pane p = (Pane) config.get("Self").get("Pane");
			p.getChildren().clear();
			Rectangle r = new Rectangle(0, 0, 840, 6000);
			r.setFill(Color.WHEAT);
			p.getChildren().add(r);
		}
		if(hx != currHBox) {
			return;
		}
		//切换pane, 找到下一个hbox高亮显示
		HBox curHb = null;
		if(box.getChildren().size() >= i + 2) {
			//直接取i + 1作为 下一个hbox
			curHb = (HBox) box.getChildren().get(i + 1);
		}else if(box.getChildren().size() > 0) {
			//选取 length -1作为hbox
			curHb = (HBox) box.getChildren().get(box.getChildren().size() - 1);
		}else {
			return;
		}
		curHb.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
		//
		VBox svbox = (VBox) curHb.getChildren().get(1);
		Text stext = (Text) svbox.getChildren().get(0);
		changePane(curHb, stext.getId());
	}
	
	private void receivedMediaMessage(String username, byte[] data, String fileName, String type, boolean changePane, String savePath) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(changePane) {
					HBox hbox = (HBox) config.get(username).get("Hbox");
					if(hbox != null) {
						if(currHBox != null) {
							currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
						hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
						changePane(hbox, username);
					}
				}
				writeRightMediaMessage(username, fileName, data, type, savePath);
				if("pic".equals(type)) {
					//加入消息文件存储
					saveMediaMessage(fileName, 2, 2, data, savePath);
				}else if("ved".equals(type)){
					//加入消息文件存储
					saveMediaMessage(fileName, 2, 3, data, savePath);
				}else {
					saveMediaMessage(fileName, 2, 4, data, savePath);
				}
				
				
			}
		});
	}
	
	private void receivedMessage(String username, String message, boolean changePane) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(changePane) {
					HBox hbox = (HBox) config.get(username).get("Hbox");
					if(hbox != null) {
						if(currHBox != null) {
							currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
						hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
						changePane(hbox, username);
					}
				}
				writeRightTextMessage(username, message);
				if(!"上线提醒".equals(message)) {
					//加入消息文件存储
					saveMessage(message, 2);
				}
			}
		});
	}

	private void addWriteThread(String username, Object[] entity) {
		if(config.containsKey(username)) {
			config.get(username).put("WriteThread", entity[0]);
		}else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("WriteThread", entity[0]);
			config.put(username, params);
		}
		//只在首次会调用
		config.get(username).put("message", entity[0]);
	}
	
	/**
	 * 切换pane用
	 * @param 
	 * @author lishaoping
	 * @Date 2018年7月14日
	 * @Package com.bj58.im.client.ClientTest.UI5
	 * @return void
	 */
	public void writeRightMediaMessage_ChangePane(Pane group,Double[] jianPointYArr, byte[] content, String type, String savePath) {//name,为用户， content为“指令-参数”
		//提取名称：
		double old = jianPointYArr[0];
		if("pic".equals(type)) {
			jianPointYArr[0] = drawImageContentRight(group, 600, jianPointYArr[0], content, "---");
		}else if("videoPart".equals(type)){
			jianPointYArr[0] = drawVideoPartContentRight(group, 600, jianPointYArr[0], content, null);
		}else if("ved".equals(type)){
			jianPointYArr[0] = drawMediaContentRight(group, 600, jianPointYArr[0], "---", savePath);
		}else {
			try {
				jianPointYArr[0] = drawImageContentRight_Path(group, 600, jianPointYArr[0], new FileInputStream("D:\\file12.jpg"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			group.getChildren().get(group.getChildren().size() - 1).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					chooseFile_perament(stage, savePath);
				}
			});
		}
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeRightMediaMessage(String username, String cmd_param, byte[] content, String type, String savePath) {//name,为用户， content为“指令-参数”
		//提取名称：
		Map<String, Object> conf = config.get(username);
		System.out.println(username);
		Pane group = (Pane) conf.get("Pane");
		final Double[] jianPointYArr = (Double[]) conf.get("YPoint");
		double old = jianPointYArr[0];
		if("pic".equals(type)) {
			jianPointYArr[0] = drawImageContentRight(group, 600, jianPointYArr[0], content, cmd_param);
		}else if("videoPart".equals(type)){
			jianPointYArr[0] = drawVideoPartContentRight(group, 600, jianPointYArr[0], content, cmd_param);
		}else if("ved".equals(type)){
			jianPointYArr[0] = drawMediaContentRight(group, 600, jianPointYArr[0], cmd_param, savePath);
		}else {
			try {
				jianPointYArr[0] = drawImageContentRight_Path(group, 600, jianPointYArr[0], new FileInputStream("D:\\file12.jpg"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			group.getChildren().get(group.getChildren().size() - 1).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					chooseFile_perament(stage, savePath);
				}
			});
			
		}
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	private Double drawVideoPartContentRight(Pane group, double jianPointX, double jianPointY, byte[] content, String cmd_param) {
		int height_img = 30;
		int width_img = 44;
		
		double strWitdh = width_img;
		double strHeight = height_img;
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
		Button videoPart = new Button("接听");
		videoPart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				videoPart.setDisable(true);
				System.out.println("写入扬声器:" + content.length);
				AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
				DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, af);
				try {
					SourceDataLine sd = (SourceDataLine) AudioSystem.getLine(info2);
					sd.open(af);
					sd.start();
					sd.write(content, 0, content.length);
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
				videoPart.setDisable(false);
			}
		});
		videoPart.setLayoutX(jianPointX - jianLineWidth - radius - width);
		videoPart.setLayoutY(jianPointY - jianLineHeight - height);
		group.getChildren().add(path);
		group.getChildren().add(videoPart);
		
		return jianPointY;
	}

	/**
	 * 对外提供的写接口
	 * @param 
	 * @author lishaoping
	 * @Date 2018年6月14日
	 * @Package com.bj58.im.client.ClientTest.UI
	 * @return void
	 */
	public void writeRightTextMessage(String username, String cmd_param) {//name,为用户， content为“指令-参数”
		//提取名称：
//		if(cp.configMap.containsKey(username)) {//第一次足够，后面都不需要了---不需要再次add
//			WriteThread wt = (WriteThread) cp.configMap.get(username)[0];
//			wt.writeNow("ok, ui received!");//又会传递过来，形成死循环
//			config.get(username).put("WriteThread", wt);
//		}
		Map<String, Object> conf = config.get(username);
		System.out.println(username);
		Pane group = (Pane) conf.get("Pane");
		final Double[] jianPointYArr = (Double[]) conf.get("YPoint");
		double old = jianPointYArr[0];
		
		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], cmd_param);
		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeRightTextMessage(Pane group, double jianPointX, final Double[] jianPointYArr, String content) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], true);
		
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeFileMessage(Pane group, double jianPointX, final Double[] jianPointYArr, InputStream data) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawImageContent_data(group, jianPointX, jianPointYArr[0], data);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeImageMessage_Data(Pane group, double jianPointX, final Double[] jianPointYArr, InputStream data) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawImageContent_data(group, jianPointX, jianPointYArr[0], data);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeVideoPartMessage_Data(Pane group, double jianPointX, final Double[] jianPointYArr, byte[] data) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawVideoPart_data(group, jianPointX, jianPointYArr[0], data);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeImageMessage(Pane group, double jianPointX, final Double[] jianPointYArr, String content) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawImageContent(group, jianPointX, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	public void writeTextMessage(Pane group, double jianPointX, final Double[] jianPointYArr, String content) {
		double old = jianPointYArr[0];
		jianPointYArr[0] = drawContent(group, jianPointX, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		//暂时不画右边
//		jianPointYArr[0] += 50;
//		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
//		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
		group.setLayoutY(group.getLayoutY() + old - jianPointYArr[0]);
	}
	
	private void printInput(Pane group, double jianPointX, final Double[] jianPointYArr, TextArea area) {
		String content = area.getText();
		drawContent(group, jianPointX, jianPointYArr[0], content);
		getHeadImg(group, jianPointX, jianPointYArr[0], false);
		//右边暂时不画
//		jianPointYArr[0] += 50;
//		jianPointYArr[0] = drawContentRight(group, 600, jianPointYArr[0], content);
//		getHeadImg(group, 600, jianPointYArr[0], true);
		jianPointYArr[0] += 50;
//		area.clear();
	}
	
//	private Double drawFileContent(Pane group, double jianPointX, Double jianPointY, String filePath){
//		Image image = null;
//		int height_img = 58;
//		int width_img = 44;
//		try {
//			image = new Image(new FileInputStream("D:\\file.jpg"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		ImageView view3 = new ImageView(image);
//		view3.setFitHeight(height_img);//image.getHeight() / 4
//		view3.setFitWidth(width_img);//"D:\\head.jpg"
//		Path path = new Path();
//		double width = width_img;//纯直线长度
//		double height = height_img;//纯直线长度
//		double radius = 5;
//		double jianLineWidth = 5;
//		double jianLineHeight = 10;
//		double angle = 90;
//		jianPointY += height;
//		
//		path.getElements().add(new MoveTo(jianPointX, jianPointY));
//		path.getElements().add(new LineTo(jianPointX + jianLineWidth, jianPointY - jianLineHeight));
//		path.getElements().add(new LineTo(jianPointX + jianLineWidth, jianPointY - jianLineHeight - height));
//		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius, jianPointY - jianLineHeight - height - radius, false, true));
//		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius + width, jianPointY - jianLineHeight - height - radius));
//		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius + width + radius, jianPointY - jianLineHeight - height, false, true));
//		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius + width + radius, jianPointY - jianLineHeight));
//		path.getElements().add(new ArcTo(radius, radius, angle, jianPointX + jianLineWidth + radius + width, jianPointY - jianLineHeight + radius, false, true));
//		
//		path.getElements().add(new LineTo(jianPointX + jianLineWidth + radius, jianPointY - jianLineHeight + radius));
//		path.getElements().add(new LineTo(jianPointX, jianPointY));
//		path.setFill(Color.rgb(0x7C, 0xCD, 0x7C));
//		DropShadow shadow = new DropShadow(10, 1, 1, Color.RED);
//		path.setEffect(shadow);
//		view3.setLayoutX(jianPointX + jianLineWidth + radius);
//		view3.setLayoutY(jianPointY - jianLineHeight - height);//+ height_img / 4
//		
//		group.getChildren().add(path);
//		group.getChildren().add(view3);
//		return jianPointY;
//		
//	}
	
	private Double drawVideoContent(Pane group, double jianPointX, Double jianPointY, String filePath){
		int height_video = 150;
		int width_video = 100;
		Media media = new Media("file:///" + filePath.replace("\\", "/"));
		MediaPlayer player = new MediaPlayer(media);
		player.setAutoPlay(false);
		player.setCycleCount(1);
		MediaView view = new MediaView(player);
		view.setFitWidth(width_video);
		view.setFitHeight(height_video);
		view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("click:" + player.statusProperty().get().name());
				if(player.getStatus() == Status.PLAYING) {
					player.pause();
				}else {
					player.play();
				}
				
			}
		});
		
		Path path = new Path();
		double width = width_video;//纯直线长度
		double height = height_video;//纯直线长度
		double radius = 5;
		double jianLineWidth = 5;
		double jianLineHeight = 10;
		double angle = 90;
		jianPointY += height;
		
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
		view.setLayoutX(jianPointX + jianLineWidth + radius);
		view.setLayoutY(jianPointY - jianLineHeight - height);//+ height_img / 4
		
		group.getChildren().add(path);
		group.getChildren().add(view);
		
		return jianPointY;
	}
	
	private double drawVideoPart_data(Pane group, double jianPointX, double jianPointY, byte[] data) {
		int height_img = 30;
		int width_img = 44;
		
		Path path = new Path();
		double width = width_img;//纯直线长度
		double height = height_img;//纯直线长度
		double radius = 5;
		double jianLineWidth = 5;
		double jianLineHeight = 10;
		double angle = 90;
		jianPointY += height;
		
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
		Button videoPart = new Button("接听");
		videoPart.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				videoPart.setDisable(true);
				System.out.println("写入扬声器:" + data.length);
				AudioFormat af = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000f, 16, 1, 16 / 8 * 1, 8000f, true);
				DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, af);
				try {
					SourceDataLine sd = (SourceDataLine) AudioSystem.getLine(info2);
					sd.open(af);
					sd.start();
					sd.write(data, 0, data.length);
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				}
				videoPart.setDisable(false);
			}
		});
		videoPart.setLayoutX(jianPointX + jianLineWidth + radius);
		videoPart.setLayoutY(jianPointY - jianLineHeight - height);//+ height_img / 4
		
		group.getChildren().add(path);
		group.getChildren().add(videoPart);
		
		return jianPointY;
	}
	
	private double drawImageContent_data(Pane group, double jianPointX, double jianPointY, InputStream data) {
		Image image;
		int height_img = 58;
		int width_img = 44;
		image = new Image(data);
		ImageView view3 = new ImageView(image);
		view3.setFitHeight(height_img);//image.getHeight() / 4
		view3.setFitWidth(width_img);//"D:\\head.jpg"
		
		Path path = new Path();
		double width = width_img;//纯直线长度
		double height = height_img;//纯直线长度
		double radius = 5;
		double jianLineWidth = 5;
		double jianLineHeight = 10;
		double angle = 90;
		jianPointY += height;
		
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
		view3.setLayoutX(jianPointX + jianLineWidth + radius);
		view3.setLayoutY(jianPointY - jianLineHeight - height);//+ height_img / 4
		
		group.getChildren().add(path);
		group.getChildren().add(view3);
		
		return jianPointY;
	}
	
	
	private double drawImageContent(Pane group, double jianPointX, double jianPointY, String filePath) {
		Image image;
		try {
			int height_img = 58;
			int width_img = 44;
			image = new Image(new FileInputStream(filePath));
			ImageView view3 = new ImageView(image);
			view3.setFitHeight(height_img);//image.getHeight() / 4
			view3.setFitWidth(width_img);//"D:\\head.jpg"
			
			Path path = new Path();
			double width = width_img;//纯直线长度
			double height = height_img;//纯直线长度
			double radius = 5;
			double jianLineWidth = 5;
			double jianLineHeight = 10;
			double angle = 90;
			jianPointY += height;
			
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
			view3.setLayoutX(jianPointX + jianLineWidth + radius);
			view3.setLayoutY(jianPointY - jianLineHeight - height);//+ height_img / 4
			
			group.getChildren().add(path);
			group.getChildren().add(view3);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return jianPointY;
	}
	
	private double drawContent(Pane group, double jianPointX, double jianPointY, String content) {
		Font font = Font.font("宋体", 15);
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(Color.BLACK);
		double rest = 0;
		if(text.getLayoutBounds().getWidth() > 250) {
			text.setWrappingWidth(250);
			rest = 10;
		}
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
		text.setY(jianPointY - jianLineHeight - height / 2 + strHeight / 4 - rest);
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(text);
		return jianPointY;
	}
	
	HBox currHBox = null;
	
	private ImageView justGetHeadImg_pure(String url) {
		try {
			Image image = new Image(new FileInputStream(url));
			ImageView view3 = new ImageView(image);
			view3.setFitHeight(58);//image.getHeight() / 4
			view3.setFitWidth(44);
			Circle circle3 = new Circle(20, 20, 20);
			view3.setClip(circle3);
			//新的处理方式：加阴影：
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			WritableImage wtimage = view3.snapshot(params, null);
			ImageView view4 = new ImageView(wtimage);
			DropShadow shadow = new DropShadow(20, 0, 0, Color.RED);
			view4.setEffect(shadow);
			return view4;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void justGetHeadImg(Pane group) {
		try {
			Image image = new Image(new FileInputStream(headImgMap.get(headImgMap.get("Self"))));
			ImageView view3 = new ImageView(image);
			view3.setFitHeight(58);//image.getHeight() / 4
			view3.setFitWidth(44);
			Circle circle3 = new Circle(20, 20, 20);
			view3.setClip(circle3);
			//新的处理方式：加阴影：
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			WritableImage wtimage = view3.snapshot(params, null);
			ImageView view4 = new ImageView(wtimage);
			DropShadow shadow = new DropShadow(20, 0, 0, Color.RED);
			view4.setEffect(shadow);
			HBox vbox = new HBox();
			Label la = new Label("  ");
			vbox.getChildren().add(la);
			vbox.getChildren().add(view4);
			group.getChildren().add(vbox);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void getHeadImg(Pane group, double jianPointX, double jianPointY, boolean right) {
		Image image;
		try {
			image = new Image(new FileInputStream(right ? headImgMap.get(currentUser) : headImgMap.get(headImgMap.get("Self"))));//"D:\\head.jpg"
			ImageView view3 = new ImageView(image);
			view3.setFitHeight(58);//image.getHeight() / 4
			view3.setFitWidth(44);//image.getWidth() / 4
			
			Circle circle3 = new Circle(22, 22, 22);//view3.getFitWidth() / 2, view3.getFitWidth() / 2, view3.getFitWidth() / 2
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
				Text text = new Text(String.format("  %s  ", nameMap.get(currentUser)));
				text.setFill(Color.BLACK);
				text.setFont(Font.font("宋体", FontWeight.BOLD, 12));
				text.setId(currentUser);//此时说明是增加对话框头图像 TODO
				vbox.getChildren().add(text);
				hbox.getChildren().add(vbox);
				if(right) {
					//也要把前一个清空
					if(currHBox != null) {
						currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
					}
					currHBox = hbox;
					hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
					
				}else {
					hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
				}
				hbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent me) {
						System.out.println("mouse over" + me.getSceneX());
						if(hbox != currHBox) {
							hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.3), CornerRadii.EMPTY, new Insets(0))));
						}
					}
				});
				hbox.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent me) {
						System.out.println("mouse out" + me.getSceneX());
						if(hbox != currHBox) {
							hbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
					}
				});
				hbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent me) {
						System.out.println("mouse clicked" + me.getSceneX());
						//对话框切换
						HBox shbox = (HBox) me.getSource();
						VBox svbox = (VBox) shbox.getChildren().get(1);
						Text stext = (Text) svbox.getChildren().get(0);
						//清空pane， 恢复初始
						if(currHBox != null) {
							currHBox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.1), CornerRadii.EMPTY, new Insets(0))));
						}
						shbox.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.5), CornerRadii.EMPTY, new Insets(0))));
						//
						changePane(shbox, stext.getId());
					}

					
				});
				config.get(currentUser).put("Hbox", hbox);
				group.getChildren().add(hbox);
			}else {
				group.getChildren().add(view4);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void changePane(HBox shbox, String username) {
		Pane pane_ = (Pane) config.get(username).get("Pane");//全局共享一个
		pane_.setLayoutY(0);
		pane_.getChildren().clear();
		Rectangle r = new Rectangle(0, 0, 840, 6000);
		r.setFill(Color.WHEAT);
		pane_.getChildren().add(r);
		double jianPointY = 400;//与消息复现有关
		final Double[] jianPointYArr = {jianPointY};
		List<Message> ms = (List<Message>) config.get(username).get("message");
		currentUser = username;//正确获取头像
		System.out.println(ms);
		for(Message s : ms) {
			if(s.getDirection() == 1) {//自己
				if(s.getType() == 1) {
					writeTextMessage(pane_, 80, jianPointYArr, s.text);
				}else if(s.getType() == 2) {
					//先统一用data方式----其实用filepath方式是可以的
					writeImageMessage_Data(pane_, 80, jianPointYArr, new ByteArrayInputStream((byte[])(s.getParams().get("picData"))));
				}else if(s.getType() == 3) {
					writeVideoMessage(pane_, 80, jianPointYArr, (String)(s.getParams().get("videoUrl")));
				}else if(s.getType() == 11){
					writeVideoPartMessage_Data(pane_, 80, jianPointYArr, (byte[])(s.getParams().get("videoPart")));
				}else {
					try {
						writeImageMessage_Data(pane_, 80, jianPointYArr, new FileInputStream("D:\\file12.jpg"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					pane_.getChildren().get(pane_.getChildren().size() - 2).setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							chooseFile_perament(stage, (String)(s.getParams().get("fileUrl")));
						}
					});
				}
				System.out.println("自己的消息");
			}else {//对方消息
				if(s.getType() == 1) {
					writeRightTextMessage(pane_, 600, jianPointYArr, s.text);
				}else if(s.getType() == 2) {
					//先统一用data方式----其实用filepath方式是可以的
//					writeRightMediaMessage(username, "", (byte[])s.getParams().get("picData"), "pic", "");
					writeRightMediaMessage_ChangePane(pane_, jianPointYArr, (byte[])s.getParams().get("picData"),  "pic", "");
//					writeImageMessage_Data(pane_, 80, jianPointYArr, new ByteArrayInputStream((byte[])(s.getParams().get("picData"))));
				}else if(s.getType() == 11) {
					writeRightMediaMessage_ChangePane(pane_, jianPointYArr, (byte[])s.getParams().get("videoPart"),  "videoPart", "");
				}else if(s.getType() == 3) {
					writeRightMediaMessage_ChangePane(pane_, jianPointYArr, null,  "ved", (String)(s.getParams().get("videoUrl")));
//					writeRightMediaMessage(username, "", (byte[])s.getParams().get("picData"), "ved", (String)(s.getParams().get("videoUrl")));
//					writeVideoMessage(pane_, 80, jianPointYArr, (String)(s.getParams().get("videoUrl")));
				}else {
					writeRightMediaMessage_ChangePane(pane_, jianPointYArr, null,  "file", (String)(s.getParams().get("picUrl")));
				}
				System.out.println("对方的消息");
			}
		}
		//更新config
		config.get(username).put("YPoint", jianPointYArr);
		currHBox = shbox;//防止hover事件改变对话框颜色
	}
	
	private double drawMediaContentRight(Pane group, double jianPointX, double jianPointY, String cmd_param, String savePath) {
		int height_video = 150;
		int width_video = 100;
		String filePath = "file:///" + savePath.replace("\\", "/");
		Media media = new Media(filePath);
		MediaPlayer player = new MediaPlayer(media);
		player.setAutoPlay(false);
		player.setCycleCount(1);
		MediaView view = new MediaView(player);
		view.setFitWidth(width_video);
		view.setFitHeight(height_video);
		view.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("click");
				if(player.getStatus() == Status.PLAYING) {
					player.pause();
				}else {
					player.play();
				}
			}
		});
		
		double strWitdh = width_video;
		double strHeight = height_video;
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
		
		view.setLayoutX(jianPointX - jianLineWidth - radius - width);
		view.setLayoutY(jianPointY - jianLineHeight - height + radius );
		
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(view);
		
		return jianPointY;
	}
	
	private double drawImageContentRight_Path(Pane group, double jianPointX, double jianPointY, InputStream filein) {
		int height_img = 58;
		int width_img = 44;
		Image image = new Image(filein);
		ImageView view3 = new ImageView(image);
		view3.setFitHeight(height_img);//image.getHeight() / 4
		view3.setFitWidth(width_img);//"D:\\head.jpg"
		
		double strWitdh = width_img;
		double strHeight = height_img;
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
		
		view3.setLayoutX(jianPointX - jianLineWidth - radius - width);
		view3.setLayoutY(jianPointY - jianLineHeight - height);
		
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(view3);
		
		return jianPointY;
	}
	
	private double drawImageContentRight(Pane group, double jianPointX, double jianPointY, byte[] content, String fileName) {
		int height_img = 58;
		int width_img = 44;
		Image image = new Image(new ByteArrayInputStream(content));
		ImageView view3 = new ImageView(image);
		view3.setFitHeight(height_img);//image.getHeight() / 4
		view3.setFitWidth(width_img);//"D:\\head.jpg"
		
		double strWitdh = width_img;
		double strHeight = height_img;
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
		
		view3.setLayoutX(jianPointX - jianLineWidth - radius - width);
		view3.setLayoutY(jianPointY - jianLineHeight - height);
		
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(view3);
		
		return jianPointY;
	}
	
	private double drawContentRight(Pane group, double jianPointX, double jianPointY, String content) {
		Font font = Font.font("宋体", 15);
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(Color.BLACK);
		double rest = 0;
		if(text.getLayoutBounds().getWidth() > 250) {
			text.setWrappingWidth(250);
			rest = 10;
		}
		double lineHeight = text.getLayoutBounds().getHeight();
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
		text.setY(jianPointY - jianLineHeight - height + lineHeight / 2 + radius - rest);
		
//		text.applyCss();
		
		group.getChildren().add(path);
		group.getChildren().add(text);
		
		return jianPointY;
	}
}
