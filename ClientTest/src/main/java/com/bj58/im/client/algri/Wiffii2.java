package com.bj58.im.client.algri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

/**
 * 最简单的实现开始：
 * 1.四连识别，构造和化解
 * 2.三连识别，牵制和防止牵制
 * 3.一看二算3决策
 * 
 * 办法2：
 * 1.当前布局 情况 查看(敌我)
 * 2（.可落子 点的优劣排名。。）判断 有没有可以导致胜利的模式(已有模式)（敌我）（敌有则阻止，我有则下一步）
 * 3.没有胜利模式，则按照 原则落子（牵制--对方必须阻止的即将形成的必胜形；；） (一步胜 > 一步阻止 > 2步必胜形构造 > 2步必胜形阻止 > 构成新2连(顶角、纵横、隔位))
 * @ClassName:Wfivezpointq
 * @Description:
 * @Author lishaoping
 * @Date 2018年10月3日
 * @Version V1.0
 * @Package com.bj58.im.client.algri
 */
public class Wiffii2 extends Application{

	static Wiffii2 ins = new Wiffii2();
	public static void main(String[] args) {
		launch(args);
//		start();
//		Matcher m = hp32.matcher("00999000009990");
//		while(m.find()) {
//			System.out.println(m.start());
//		}
//		ins.drawQJ(new int[100][100]);
//		ins.drawQJ(new Integer[100][100]);
//		try {
//			ins.duan();
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//		System.out.println("45".split("")[1]);
	}

//	private static void start() {
//		Integer[][] zi = new Integer[100][100];
//		//遍历n行n列，和斜对角线，识别连续的点
//		Map<String, List<List<Point>>> list = shibiesilian(zi, 0);
//	}
	boolean shi = true;
	int[][] start = new int[99][99];//白出1 ， 黑出9
	Group group = new Group();
	Object outerLock = new Object();
	boolean quan = true;

	private void duan(Stage primaryStage) throws Exception {
		Scene scene = new Scene(group, 400, 400, Color.rgb(0x11, 0x11, 0x11, 0.1));
		Path path = new Path();
		path.setStrokeWidth(0.5);
		for(int i = 10; i < 410; i+=10) {
			path.getElements().add(new MoveTo(i, 0));
			path.getElements().add(new LineTo(i, 400));
		}
		for(int i = 10; i < 410; i+=10) {
			path.getElements().add(new MoveTo(0,i));
			path.getElements().add(new LineTo(400, i));
		}
		group.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double y = event.getSceneX();
				double x = event.getSceneY();
				System.out.println(Math.round(x/10));
				System.out.println(Math.round(y/10));
				if(start[(int) Math.round(x/10)][(int) Math.round(y/10)] != 0) {
					System.out.println("has qizi");
					return;
				}
				start[(int) Math.round(x/10)][(int) Math.round(y/10)] = 9;
				System.out.println("hei:" + (int)Math.round(y/10) + "," + (int)Math.round(x/10));
				Rectangle rect  = new Rectangle(10, 10, Color.RED);
				rect.setLayoutX(Math.round(y/10) * 10 - 5);
				rect.setLayoutY(Math.round(x/10) * 10 - 5);
				group.getChildren().add(rect);
				synchronized (outerLock) {
					quan = true;
					outerLock.notify();
				}
			}
		});
		group.getChildren().add(path);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			try {
				Map<String, int[]> cacheMap = new HashMap<>();
				Scanner scanner = new Scanner(System.in);
				while(true) {
						Map<String, List<BJ>> bai = kanbai(start);
						Map<String, List<BJ>> hei = kanhei(start);
						int[] currPoint = null;
						//自己是白方
						//1.先看己方一步胜利，再看对方一步胜利，在看己方2步胜利，.....
						if (bai.containsKey("q_si_l") && bai.get("q_si_l").size() > 0) {
							List<BJ> qsi = bai.get("q_si_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: q_si_l");
							currPoint = chu;
						} else if (bai.containsKey("r_si_l") && bai.get("r_si_l").size() > 0) {
							List<BJ> qsi = bai.get("r_si_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: r_si_l");
							currPoint = chu;
						} else if (hei.containsKey("q_si_l") && hei.get("q_si_l").size() > 0) {//双强3连的处理没？
							List<BJ> qsi = hei.get("q_si_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: hq_si_l");
							currPoint = chu;
						} else if (hei.containsKey("r_si_l") && hei.get("r_si_l").size() > 0) {
							List<BJ> qsi = hei.get("r_si_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: hr_si_l");
							currPoint = chu;
						} else if (bai.containsKey("q_san_l") && bai.get("q_san_l").size() > 0) {//2招
							List<BJ> qsi = bai.get("q_san_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: q_san_l");
							currPoint = chu;
						} else if (cacheMap.containsKey("next1") && bai.get("next1").size() > 0) {//2招
							int[] chu = cacheMap.get("next1");
							start[chu[0]][chu[1]] = 1;
							cacheMap.remove("next1");
							System.out.println("chu: next1");
						} else if (bai.containsKey("r_san_l") && bai.get("r_san_l").size() > 0) {//2招
							List<BJ> qsi = bai.get("r_san_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: r_san_l");
							currPoint = chu;
						} else if (hei.containsKey("q_san_l") && hei.get("q_san_l").size() > 0) {//2招
							List<BJ> qsi = hei.get("q_san_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: hq_san_l");
							currPoint = chu;
						}
						//								else if(hei.containsKey("r_san_l")) {//2招 先不阻止
						//									List<BJ> qsi = bai.get("r_san_l");
						//									int[] chu = qsi.get(0).k[0];
						//									start[chu[0]][chu[1]] = 1;
						//								}
						else if (bai.containsKey("q_er_l") && bai.get("q_er_l").size() > 0) {//强2连 综合判断
							List<BJ> qsi = bai.get("q_er_l");
							//是否2个， 2个是否相交为0, 2个是否平行;3点或者可以构成4点平行

							if (qsi.size() > 1) {
								//组合序
								Map<String, int[]> luoMap = new HashMap<>();
								for (int i = 0; i < qsi.size() - 1; i++) {
									BJ one = qsi.get(i);
									for (int j = i + 1; j < qsi.size(); j++) {
										BJ two = qsi.get(j);
										int pan = relatation(one, two, start, luoMap);
										if (luoMap.containsKey("two_step1")) {//必胜
											luoMap.put("next1", luoMap.get("two_step2"));
											int[] luo = luoMap.get("two_step1");
											start[luo[0]][luo[1]] = 1;
											currPoint = luo;
											break;
										} else if (luoMap.containsKey("1")) {//也好
											int[] luo = luoMap.get("1");
											start[luo[0]][luo[1]] = 1;
											currPoint = luo;
											break;
										} else if (luoMap.containsKey("3")) {//也好
											int[] luo = luoMap.get("3");
											start[luo[0]][luo[1]] = 1;
											currPoint = luo;
											break;
										} else if (luoMap.containsKey("4")) {//也好
											int[] luo = luoMap.get("4");
											start[luo[0]][luo[1]] = 1;
											currPoint = luo;
											break;
										}
									}
								}
							} //直接出子
							int[] chu = qsi.get(0).k[0];
							if (start[chu[0]][chu[1]] == 1) {
								System.out.println("error-----compute");
								return;
							}
							start[chu[0]][chu[1]] = 1;
							currPoint = chu;
							System.out.println("chu: q_er_l");
						} else if (bai.containsKey("q_yi_l") && bai.get("q_yi_l").size() > 0) {
							List<BJ> qsi = bai.get("q_yi_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: q_yi_l");
							currPoint = chu;
						} else if (bai.containsKey("r_er_l") && bai.get("r_er_l").size() > 0) {
							List<BJ> qsi = bai.get("r_er_l");
							int[] chu = qsi.get(0).k[0];
							start[chu[0]][chu[1]] = 1;
							System.out.println("chu: r_er_l");
							currPoint = chu;
						} else if (shi) {
							shi = false;
							//白先
							start[25][25] = 1;
							System.out.println("chu: start");
							currPoint = new int[] {25,25};
						} else {
							System.out.println("not know how to luo zi");
						}
						final int[] draw = currPoint;
						final Object lock = new Object();
						final boolean[] x = new boolean[] {false};
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								System.out.println("bai:" + draw[0] + "," + draw[1]);
								ins.drawQJ2(draw);
								synchronized (lock) {
									x[0] = true;
									lock.notify();
								}
						}});
						synchronized (lock) {
							if(x[0] == false) {
								lock.wait();
							}
						}
						synchronized (outerLock) {
							if(quan) {
								outerLock.wait();
							}
							
						}
						//等待输入出动
//						String line = scanner.nextLine();
//						System.out.println("get point:" + line);
//						String[] point = line.split(",");
//						while (point.length != 2) {
//							System.out.println("input again");
//							line = scanner.nextLine();
//							point = line.split(",");
//							if (start[Integer.valueOf(point[0])][Integer.valueOf(point[1])] != 0) {
//								point = new String[] { "" };
//							}
//						}
//						int[] p = new int[] { Integer.valueOf(point[0]), Integer.valueOf(point[1]) };
//						start[p[0]][p[1]] = 9;
						//展示start的图案
//						Platform.runLater(new Runnable() {
//							@Override
//							public void run() {
//								ins.drawQJ2(draw);
//						}});
						//clear
						for (List<BJ> ls : bai.values()) {
							ls.clear();
						}
						for (List<BJ> ls : hei.values()) {
							ls.clear();
						} 
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
				}).start();
	}
		

	private void drawQJ(Integer[][] qz) {
		for(int i = 0; i <= 100; i++) {
			String qian = i < 10 ? "0" + i : i + "";
			System.out.print(qian + " ");
		}
		System.out.println();
		for(int i = 0; i < qz.length; i++) {
			for(int j = 0; j < qz[i].length; j++) {
				System.out.print(" " + qz[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------next--------------");
	}
	
	private void drawQJ2(int[] curr) {
		Rectangle rect  = new Rectangle(10, 10, Color.BLACK);
		rect.setLayoutX(curr[1] * 10 - 5);
		rect.setLayoutY(curr[0] * 10 - 5);
		group.getChildren().add(rect);
		System.out.println("bai---------------ok");
	}
	
	private void drawQJ(int[][] qz) {
		String[] lix = new String[103];
		String[] lix2 = new String[103];
		lix[0] = "-";
		lix2[0] = "-";
		lix[1] = "-";
		lix2[1] = "-";
		lix[2] = "-";
		lix2[2] = "-";
		for(int i = 0; i < 100; i++) {
			String qian = i < 10 ? "0" + i : i + "";
			String[] shuzi = qian.split("");
			lix[i+3] = shuzi[0];
			lix2[i+3] = shuzi[1];
		}
		for(String i : lix) {
			System.out.print(i);
		}
		System.out.println();
		for(String i : lix2) {
			System.out.print(i);
		}
		System.out.println();
		for(int i = 0; i < qz.length; i++) {
			System.out.print(i < 10 ? "0"+i+" " : i+" ");
			for(int j = 0; j < qz[i].length; j++) {
				System.out.print(qz[i][j]);
			}
			System.out.println();
		}
		System.out.println("-----------------next--------------");
	}
	
	private int relatation(BJ one, BJ two, int[][] start, Map<String, int[]> luoMap) throws Exception {
		//相交为0  测定距离 --直接放子；太远则先待定
		//相交为1--为白-为共同一子；则放在使得3点的共同交线，没有 则直接放在其中一条线上
		//不相交而平行--则看两线之外是否有一点 和两线上一点形成强2连，能 则放在这个位置上， 否则直接放在其中一条线上
		//相交太远或者平行太远则直接当作不好而放在其中一条线上
		
		//相交/不相交 判断方法：点横纵坐标之间--即斜率;  相交点计算方法：4种斜率下：等价为求两个二元一次方程-即计算一个方程组 或者说计算一个矩阵--对角化
		int oneK = computeK(one);
		int twoK = computeK(two);
		if(oneK - twoK == 0) {//平行
			//复制一份局势
			int[][] bsbz = findPointAndNextPoint(start, one, two);
			if(bsbz != null) {
				luoMap.put("two_step1", bsbz[0]);
				luoMap.put("two_step2", bsbz[1]);
				return 1;
			}else {
				System.out.println("error compute");
			}
			//形成的新的强2连，该点不在另一条线上，且新强2连的范围内有一空点再另一条线上
			//下一步，下几步都定了
		}else {
			int[] jd = computeJiaoD(one, two, oneK, twoK);
			int color = start[jd[0]][jd[1]];
			if(color == 1) {//我方的棋子
				//线内落子，线外不考虑
				boolean same = samePoint(jd, one.s);
				int[] endPoint1 = same ? jd : one.w;
				boolean same2 = samePoint(jd, two.s);
				int[] endPoint2 = same2 ? jd : two.w;
				int deltx = endPoint1[0] - jd[0] > 0 ? 1 : endPoint1[0] - jd[0] == 0 ? 0 : -1;
				int delty = endPoint1[1] - jd[1] > 0 ? 1 : endPoint1[1] - jd[1] == 0 ? 0 : -1;
				int startX = jd[0];
				int startY = jd[1];
				
				while(startX != endPoint1[0] || startY != endPoint1[1]) {
					int currX = startX + deltx;
					int currY = startY + delty;
					if(endPoint2[0] - currX == 0) {//可以
						boolean ok = isOk(start, currX, currY, endPoint2[0], endPoint2[1]);
						luoMap.put("3", new int[] {currX, currY});
					}
					double currK = (endPoint2[1] - currY + 0d) / (endPoint2[0] - currX);
					if(currK == 1 || currK == -1 || currK == 0) {//可以
						boolean ok = isOk(start, currX, currY, endPoint2[0], endPoint2[1]);
						luoMap.put("4", new int[] {currX, currY});
					}
				}
				return 1;
			}else if(color == 0){//无子,从过程看一定相邻的
				luoMap.put("1", jd);
				return 1;
			}else {
				System.out.println("error occurence");
			}
		}
		return -1;
	}

	private int[][] findPointAndNextPoint(int[][] start, BJ one, BJ two) throws Exception {
		int delX = one.s[0] - one.w[0];
		int delY = one.s[1] - one.w[1];
		int stepX = delX > 0 ? 1 : delX == 0 ? 0 : -1;
		int stepY = delY > 0 ? 1 : delY == 0 ? 0 : -1;
		int fstepX = stepX == 0 ? 0 : (-delX);
		int fstepY = stepY == 0 ? 0 : (-delY);
		
		int[][] target = getToSanlian(start, one);
		int[][] target2 = getToSanlian(start, two);
		int[][] tt = getTargetTwoPoint(start, one, two, target);
		if(tt == null) {
			tt = getTargetTwoPoint(start, two, one, target2);
		}
		return tt;
	}

	private int[][] getTargetTwoPoint(int[][] start, BJ one, BJ two, int[][] target) throws Exception {
		int currX = 0;
		int currY = 0;
		for(int[] curr : target) {
			currX = curr[0];
			currY = curr[1];
			if(start[currX][currY] == 0) {//可以看
				int[][] startcp = copyArr(start);
				startcp[currX][currY] = 1;
				//看强2连里有没有currY
				Map<String, List<BJ>> bai = new Wiffii2().kanbai(startcp);
				List<BJ> qsi = bai.get("q_er_l");
				for(BJ x : qsi) {
					boolean same = samePoint(x.s, new int[] {currX, currY});
					boolean same2 = samePoint(x.w, new int[] {currX, currY});
					if(same || same2) {//看本强2连x  和 原强2连 one的交点；
						int oneK = computeK(one);
						int twoK = computeK(x);
						int[] jd = computeJiaoD(one, x, oneK, twoK);
						//jd在two上,且和two构成强3连，且和x构成强3连 ; 则这个点是下一步的点
						boolean ok = isQiang3lian(startcp, jd, two.s, two.w);//3个点
						boolean ok2 = isQiang3lian(startcp, jd, x.s, x.w);//3个点
						if(ok && ok2) {
							int[][] rs = new int[][] {{currX, currY}, jd};
							return rs;
						}
					}
					
				}
			}
			
		}
		return null;
	}

	private int[][] getToSanlian(int[][] start, BJ one) {//one是强2连
		int delX = one.s[0] - one.w[0];
		int delY = one.s[1] - one.w[1];
		int stepX = delX > 0 ? 1 : delX == 0 ? 0 : -1;
		int stepY = delY > 0 ? 1 : delY == 0 ? 0 : -1;
		int[] l1 = null;
		int[] l2 = null;
		int[] l3 = null;
		l1 = new int[]{one.s[0] - stepX, one.s[1] - stepY};
		l3 = new int[] {one.w[0] + stepY, one.w[1] + stepY};
		if((Math.abs(delX) == 1 && Math.abs(delY) == 1) ||
				(Math.abs(delX) == 1 && delY == 0) ||
				(Math.abs(delY) == 1 && delX == 0)) {
		}else {//还有空格点
			l2 = new int[] {one.s[0] + stepX, one.s[1] + stepY};
		}
		return l2 == null ? new int[][] {l1, l3} : new int[][] {l1,l2,l3};
	}

	private boolean isQiang3lian(int[][] startcp, int[] jd, int[] s, int[] w) {
		//斜率相等，连续或者相隔1 两端有空
		int k = computeK(new BJ(jd, s, null));
		int k2 = computeK(new BJ(jd, w, null));
		if(k == k2) {//共线
			int[] max = jd;
			int[] min = jd;
			if(jd[0] == s[0]) {//x轴平行 直接比较y
				if(jd[1] - s[1] < 0) {
					max = s;
				}else {
					min = s;
				}
				if(w[1] - max[1] > 0) {
					max = w;
				}
				if(w[1] - min[1] < 0) {
					min = w;
				}
			}else {//直接比较x的大小，
				if(jd[0] - s[0] < 0) {
					max = s;
				}else {
					min = s;
				}
				if(w[0] - max[0] > 0) {
					max = w;
				}
				if(w[0] - min[0] < 0) {
					min = w;
				}
			}
			//开始看
			int stepX = max[0] - min[0] > 0 ? 1 : max[0] - min[0] == 0 ? 0 : -1;
			int stepY = max[1] - min[1] > 0 ? 1 : max[1] - min[1] == 0 ? 0 : -1;
			int[] curr = min;
			while(!samePoint(curr, max)) {
				curr[0] += stepX;
				curr[1] += stepY;
				if(startcp[curr[0]][curr[1]] == 0) {//有空格
					int[] less = new int[] {min[0] - stepX, min[1] - stepY};
					int[] bigger = new int[] {max[0] + stepX, max[1] + stepY};
					if(startcp[less[0]][less[1]] == 0 && startcp[bigger[0]][bigger[1]] == 0) {
						//是强3连
						return true;
					}
				}else {//无空格
					int[] less = new int[] {min[0] - stepX, min[1] - stepY};
					int[] bigger = new int[] {max[0] + stepX, max[1] + stepY};
					int[] less2 = new int[] {less[0] - stepX, less[1] - stepY};
					int[] bigger2 = new int[] {bigger[0] + stepX, bigger[1] + stepY};
					if(startcp[less[0]][less[1]] == 0 && startcp[bigger[0]][bigger[1]] == 0) {
						if(startcp[less2[0]][less2[1]] == 0 || startcp[bigger2[0]][bigger2[1]] == 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private int[][] copyArr(int[][] start) {
		int[][] cp = new int[start.length][start[0].length];
		for(int i = 0; i < start.length; i++) {
			int[] s = new int[start[i].length];
			cp[i] = s;
			for(int j = 0; j < s.length; j++) {
				cp[i][j] = start[i][j];
			}
		}
		return cp;
	}

	private boolean isOk(int[][] start, int currX, int currY, int endX, int endY) {
		int delX = endX - currX;
		int delY = endY - currY;
		if(delX == 0) {
			int step = delY > 0 ? 1 : -1;
			if(delY >= 4 || delY == 0) {
				return false;
			}
			for(int i = currY; i < endY;) {
				int ccc = start[currX][i];
				if(ccc != 0 && ccc != 1) {
					return false;
				}
				i += step;
			}
			return true;
		}else if(delY == 0) {
			int step = delX > 0 ? 1 : -1;
			if(delX >= 4) {
				return false;
			}
			for(int i = currX; i < endX;) {
				int ccc = start[i][currY];
				if(ccc != 0 && ccc != 1) {
					return false;
				}
				i += step;
			}
			return true;
		}else {
			int stepX = delX > 0 ? 1 : -1;
			int stepY = delY > 0 ? 1 : -1;
			if(delX >= 4 || delY >= 4) {
				return false;
			}
			for(int i = currX, j = currY; i < endX && j < endY;) {
				int ccc = start[i][j];
				if(ccc != 0 && ccc != 1) {
					return false;
				}
				i += stepX;
				j += stepY;
			}
			return true;
		}
	}

	private boolean samePoint(int[] jd, int[] s) {
		return jd[0] == s[0] && jd[1] == s[1];
	}

	private int[] computeJiaoD(BJ one, BJ two, int oneK, int twoK) {
		int[] jd;
		if(oneK == 100) {
			int y = (one.s[0] - two.s[0]) * twoK + two.s[1];
			jd = new int[] {one.s[0], y};
		}else if(twoK == 100) {
			int y = (two.s[0] - one.s[0]) * oneK + one.s[1];
			jd = new int[] {two.s[0], y};
		}else {
			int x = (one.s[1] + oneK * one.s[0] - two.s[1] - twoK * two.s[0]) / (oneK - twoK);
			int y = (oneK * (x - one.s[0]) + twoK * (x - two.s[0]) + one.s[1] + two.s[1]) / 2;
			jd = new int[] {x, y};
		}
		return jd;
	}

	private int computeK(BJ one) {
		int[] s = one.s;
		int[] w = one.w;
		int k = 100;
		int deltX = w[0] - s[0];
		if(deltX == 0) {
			return k;
		}
		k = (w[1] - s[1]) / deltX;
		return k;
	}

	private Map<String, List<BJ>> kanhei(int[][] zi) throws Exception {
		//i == 0 表示自己：白子  1表示黑子
		Map<String, List<BJ>> map = new HashMap<>();
		
		map.put("q_si_l", hsilian);
		map.put("r_si_l", hrsilian);
		map.put("q_san_l", hsanlian);
		map.put("r_san_l", hrsanlian);
		map.put("q_er_l", herlian);
		map.put("r_er_l", hrerlian);
		map.put("q_yi_l", hyilian);
		//1.横竖撇捺  先看自己
		for(int i = 0; i < zi.length; i++) {
			//tostring
			String str = arrToString(zi[i]);
			Matcher m5 = hp5.matcher(str);
			if(m5.find()) {
				throw new Exception("hei win");
			}
			Matcher m = hp4.matcher(str);
			while(m.find()) {
				int wei = m.start();//多次匹配，以后处理
				hsilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei},{i,wei+5}}));
			}
			Matcher m3 = hp3.matcher(str);
			Matcher m32 = hp32.matcher(str);
			Matcher m33 = hp33.matcher(str);
			Matcher m34 = hp34.matcher(str);
			Matcher m35 = hp35.matcher(str);
			boolean f1 = false;
			boolean f2 = false;
			while(m3.find()) {
				f1 = true;
				int wei = m3.start();//多次匹配，以后处理
				hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1},{i,wei+5}}));//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
			}
			if(!f1) {
				while(m32.find()) {
					f2 = true;
					int wei = m32.start();//多次匹配，以后处理
					hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1}}));
				}
			}
			if(!f1 && !f2) {
				while(m33.find()) {
					int wei = m33.start();//多次匹配，以后处理
					hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+4}}));
				}
			}
			if(m34.find()) {
				int wei = m34.start();
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
			}
			if(m35.find()) {
				int wei = m35.start();
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+2}}));
			}
			Matcher m1 = hp1.matcher(str);
			while(m1.find()) {//while的方式好点---因为可以发现很多组
				int wei = m1.start();
				int c0 = m1.group(1).length();
				int c1 = m1.group(2).length();
				if(c0 + c1 >= 5) {
					int luo = c0 == 1 ? wei+c0+1 : c0 == 4 ? wei+c0-1 : wei+c0+1;
					if(luo == wei) {
						hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}));
					}else {
						hyilian.add(new BJ(new int[] {i,wei+c0}, new int[] {i, wei+c0}, new int[][] {{i,luo}}));
					}
				}
			}
			//
			for(int j = 0; j < zi[0].length; j++) {
				//强2连的专门识别方法---其实也可以正则匹配---一个或者多个的方法
				if(j + 5 < zi[0].length) {
					int count = zi[i][j] + zi[i][j+1] + zi[i][j+2] + zi[i][j+3] + zi[i][j+4] + zi[i][j+5];
					if(count == 2 * 9) {
						int wei[] = new int[2];
						int c = 0;
						int kong[] = new int[4];
						int kc = 0;
						for(int k = 0; k < 6; k++) {
							if(zi[i][j+k] == 9) {
								wei[c++] = j+k;
								break;
							}else {
								kong[kc++] = j+k;
							}
						}
						if((wei[1] - wei[0] == 1) && (wei[0] > j) && (wei[1] < j+5)) {//是强2连，否则是也若2连-----会被后面的程序所识别出来
							//j+1 到j+4中选择
							herlian.add(new BJ(new int[] {i,j}, new int[] {i, j+5}, new int[][] {{i,kong[0]},{i,kong[1]},{i,kong[2]},{i,kong[3]}}));
						}
					}
				}
				if(j + 4 < zi[0].length) {
					int count = zi[i][j] + zi[i][j+1] + zi[i][j+2] + zi[i][j+3] + zi[i][j+4];
					if(count == 4 * 9) {//是为弱4连
						int wei = -1;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei = j+k;
								break;
							}
						}
						hrsilian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei}}));
					}else if(count == 3 * 9) {//连续5个，和为3
						int wei[] = new int[2];
						int c = 0;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei[c++] = j+k;
								break;
							}
						}
						hrsanlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]}}));
					}else if(count == 2 * 9) {
						int wei[] = new int[3];
						int c = 0;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei[c++] = j+k;
								break;
							}
						}
						hrerlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]},{i,wei[2]}}));
					}
				}
			}
			
		}
		//2.竖
		
		for(int j = 0; j < zi[0].length; j++) {
			int[] lie = new int[zi.length];
			for(int i = 0; i < zi.length; i++) {
				lie[i] = zi[i][j];
			}
			//
			String str = arrToString(lie);
			addQiangh(str, j, 2);//2
			addRuoh(lie, j, 2);
		}
		//3.撇
		for(int j = zi[0].length - 4; j >= 0 ; j--) {
			int[] lie = new int[zi[0].length - j];
			for(int k = 0; j + k < zi[0].length; k++) {
				lie[k] = zi[k][j + k];
			}
			//
			String str = arrToString(lie);
			addQiangh(str, 0, 3);//2
			addRuoh(lie, 0, 3);
		}
		for(int i = 1; i < zi.length-3; i++) {
			int[] lie = new int[zi[0].length - i];
			for(int k = 0; i + k < zi[0].length; k++) {
				lie[k] = zi[i + k][k];
			}
			//
			String str = arrToString(lie);
			addQiangh(str, 0, 3);//2
			addRuoh(lie, 0, 3);
		}
		//4.捺
		for(int i = 3; i < zi[0].length; i++) {
			int[] lie = new int[i + 1];
			for(int j = 0; i - j >= 0; j++) {
				lie[j] = zi[i - j][j];
			}
			//
			String str = arrToString(lie);
			addQiangh(str, i, 4);//2
			addRuoh(lie, i, 4);
		}
		for(int j = zi[0].length - 4; j >= 0 ; j--) {
			int[] lie = new int[zi[0].length - j];
			for(int k = 0; j + k < zi[0].length; k++) {
				lie[k] = zi[zi.length - k - 1][j + k];
			}
			//
			String str = arrToString(lie);
			addQiangh(str, zi.length - 1, 4);//2
			addRuoh(lie, zi.length - 1, 4);
		}
		return map;
	}
	
	List<BJ> hwulian = new ArrayList<>();
	List<BJ> hsilian = new ArrayList<>();
	List<BJ> hrsilian = new ArrayList<>();
	List<BJ> hsanlian = new ArrayList<>();
	List<BJ> hrsanlian = new ArrayList<>();
	List<BJ> herlian = new ArrayList<>();
	List<BJ> hrerlian = new ArrayList<>();
	List<BJ> hyilian = new ArrayList<>();
	
	List<BJ> wulian = new ArrayList<>();
	List<BJ> silian = new ArrayList<>();
	List<BJ> rsilian = new ArrayList<>();
	List<BJ> sanlian = new ArrayList<>();
	List<BJ> rsanlian = new ArrayList<>();
	List<BJ> erlian = new ArrayList<>();
	List<BJ> rerlian = new ArrayList<>();
	List<BJ> yilian = new ArrayList<>();
	/**
	 * 分成：纯4连 纯3连， 带一个null的牵制(马上成5连，成纯4连、半4连)
	 * 纯2连，纯2连之间的关系：交点空或者非空；；
	 * 落子点：没有纯2连时候，落子原则：已有单点的交点处；没有交点 则对角线处、直线处----形成新纯2连伟原则。。。如果满足这些原则下，有多个落子选择机会：则选择---既能能牵制又能构成新2连--且和另老2连相交
	 * 		有纯2连：则构成纯3连
	 *  	有纯3连：则构成纯4连
	 *  	有纯4连：则构成5连
	 *  
	 *  ------------一句话：
	 *  找出布局和关系  ：  (强4连，弱4连，强3连，弱3连，强2连，强1连) (弱3连和弱3连， 强2连合强2连， 强2连和弱3连)
	 *  判断最佳落子位置：原则：(向上向右、更强更多)
	 * @param 
	 * @author lishaoping
	 * @Date 2018年10月3日
	 * @Package com.bj58.im.client.algri
	 * @return Map<String,List<List<Point>>>
	 * @throws Exception 
	 */
	private Map<String, List<BJ>> kanbai(int[][] zi) throws Exception {
		//i == 0 表示自己：白子  1表示黑子
		Map<String, List<BJ>> map = new HashMap<>();
		
		map.put("wu_l", wulian);
		map.put("q_si_l", silian);
		map.put("r_si_l", rsilian);
		map.put("q_san_l", sanlian);
		map.put("r_san_l", rsanlian);
		map.put("q_er_l", erlian);
		map.put("r_er_l", rerlian);
		map.put("q_yi_l", yilian);
		//1.横竖撇捺  先看自己
		for(int i = 0; i < zi.length; i++) {
			//tostring
			String str = arrToString(zi[i]);
			Matcher m5 = p5.matcher(str);
			if(m5.find()) {
				throw new Exception("bai win");
			}
			Matcher m = p4.matcher(str);
			while(m.find()) {
				int wei = m.start();//多次匹配，以后处理
				silian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei},{i,wei+5}}));
			}
			Matcher m3 = p3.matcher(str);
			Matcher m32 = p32.matcher(str);
			Matcher m33 = p33.matcher(str);
			Matcher m34 = p34.matcher(str);
			Matcher m35 = p35.matcher(str);
			boolean f1 = false;
			boolean f2 = false;
			while(m3.find()) {
				f1 = true;
				int wei = m3.start();//多次匹配，以后处理
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1},{i,wei+5}}));//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
			}
			if(!f1) {
				while(m32.find()) {
					f2 = true;
					int wei = m32.start();//多次匹配，以后处理
					sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1}}));
				}
			}
			if(!f1 && !f2) {
				while(m33.find()) {
					int wei = m33.start();//多次匹配，以后处理
					sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+4}}));
				}
			}
			if(m34.find()) {
				int wei = m34.start();
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
			}
			if(m35.find()) {
				int wei = m35.start();
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+2}}));
			}
			Matcher m1 = p1.matcher(str);
			while(m1.find()) {//while的方式好点---因为可以发现很多组
				int wei = m1.start();
				int c0 = m1.group(1).length();
				int c1 = m1.group(2).length();
				if(c0 + c1 >= 5) {
//					int luo = c0 == 1 ? wei+1 : c0 == 4 ? wei+4 : wei;
					int luo = c0 == 1 ? wei + c0 + 1 : c0 == 4 ? wei + c0 - 1 : wei + c0 + 1;
					if(luo == wei) {
						yilian.add(new BJ(new int[] {i,wei + c0}, new int[] {i, wei + c0}, new int[][] {{i,wei-1},{i,wei+1}}));
					}else {
						yilian.add(new BJ(new int[] {i,wei + c0}, new int[] {i, wei + c0}, new int[][] {{i,luo}}));
					}
				}
			}
			//
			for(int j = 0; j < zi[0].length; j++) {
				//强2连的专门识别方法---其实也可以正则匹配---一个或者多个的方法
				if(j + 5 < zi[0].length) {
					int count = zi[i][j] + zi[i][j+1] + zi[i][j+2] + zi[i][j+3] + zi[i][j+4] + zi[i][j+5];
					if(count == 2) {
						int wei[] = new int[2];
						int c = 0;
						int kong[] = new int[4];
						int kc = 0;
						for(int k = 0; k < 6; k++) {
							if(zi[i][j+k] == 1) {
								wei[c++] = j+k;
								break;
							}else {
								kong[kc++] = j+k;
							}
						}
						if((wei[1] - wei[0] == 1) && (wei[0] > j) && (wei[1] < j+5)) {//是强2连，否则是也若2连-----会被后面的程序所识别出来
							//j+1 到j+4中选择
							erlian.add(new BJ(new int[] {i,j}, new int[] {i, j+5}, new int[][] {{i,kong[0]},{i,kong[1]},{i,kong[2]},{i,kong[3]}}));
						}
					}
				}
				if(j + 4 < zi[0].length) {
					int count = zi[i][j] + zi[i][j+1] + zi[i][j+2] + zi[i][j+3] + zi[i][j+4];
					if(count == 4) {//是为弱4连
						int wei = -1;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei = j+k;
								break;
							}
						}
						rsilian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei}}));
					}else if(count == 3) {//连续5个，和为3
						int wei[] = new int[2];
						int c = 0;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei[c++] = j+k;
								break;
							}
						}
						rsanlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]}}));
					}else if(count == 2) {
						int wei[] = new int[3];
						int c = 0;
						for(int k = 0; k < 5; k++) {
							if(zi[i][j+k] == 0) {
								wei[c++] = j+k;
								break;
							}
						}
						rerlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]},{i,wei[2]}}));
					}
				}
			}
			
		}
		//2.竖
		
		for(int j = 0; j < zi[0].length; j++) {
			int[] lie = new int[zi.length];
			for(int i = 0; i < zi.length; i++) {
				lie[i] = zi[i][j];
			}
			//
			String str = arrToString(lie);
			if(str.contains("1")) {
				System.out.println(str);
			}
			addQiang(str, j, 2);//2
			addRuo(lie, j, 2);
		}
		//3.撇
		for(int j = zi[0].length - 4; j >= 0 ; j--) {
			int[] lie = new int[zi[0].length - j];
			for(int k = 0; j + k < zi[0].length; k++) {
				lie[k] = zi[k][j + k];
			}
			//
			String str = arrToString(lie);
			addQiang(str, 0, 3);//2
			addRuo(lie, 0, 3);
		}
		for(int i = 1; i < zi.length-3; i++) {
			int[] lie = new int[zi[0].length - i];
			for(int k = 0; i + k < zi[0].length; k++) {
				lie[k] = zi[i + k][k];
			}
			//
			String str = arrToString(lie);
			addQiang(str, 0, 3);//2
			addRuo(lie, 0, 3);
		}
		//4.捺
		for(int i = 3; i < zi[0].length; i++) {
			int[] lie = new int[i + 1];
			for(int j = 0; i - j >= 0; j++) {
				lie[j] = zi[i - j][j];
			}
			//
			String str = arrToString(lie);
			addQiang(str, i, 4);//2
			addRuo(lie, i, 4);
		}
		for(int j = zi[0].length - 4; j >= 0 ; j--) {
			int[] lie = new int[zi[0].length - j];
			for(int k = 0; j + k < zi[0].length; k++) {
				lie[k] = zi[zi.length - k - 1][j + k];
			}
			//
			String str = arrToString(lie);
			addQiang(str, zi.length - 1, 4);//2
			addRuo(lie, zi.length - 1, 4);
		}
		return map;
	}
	
	private void addRuoh(int[] arr, int i, int direct) {
		for(int j = 0; j < arr.length; j++) {
			//强2连的专门识别方法---其实也可以正则匹配---一个或者多个的方法
			if(j + 5 < arr.length) {
				int count = arr[j] + arr[j+1] + arr[j+2] + arr[j+3] + arr[j+4] + arr[j+5];
				if(count == 2 * 9) {
					int wei[] = new int[2];
					int c = 0;
					int kong[] = new int[4];
					int kc = 0;
					for(int k = 0; k < 6; k++) {
						if(arr[j+k] == 9) {
							wei[c++] = j+k;
							break;
						}else {
							kong[kc++] = j+k;
						}
					}
					if((wei[1] - wei[0] == 9) && (wei[0] > j) && (wei[1] < j+5)) {//是强2连，否则是也若2连-----会被后面的程序所识别出来
						//j+1 到j+4中选择
						herlian.add(new BJ(new int[] {i,j}, new int[] {i, j+5}, new int[][] {{i,kong[0]},{i,kong[1]},{i,kong[2]},{i,kong[3]}})
																.zhuanzhi(direct, new int[] {5, kong[0] - j, kong[1] - j, kong[2] - j, kong[3] - j}));
					}
				}
			}
			if(j + 4 < arr.length) {
				int count = arr[j] + arr[j+1] + arr[j+2] + arr[j+3] + arr[j+4];
				if(count == 4 * 9) {//是为弱4连
					int wei = -1;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei = j+k;
							break;
						}
					}
					hrsilian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei}}).zhuanzhi(direct, new int[] {4, wei - j}));
				}else if(count == 3 * 9) {//连续5个，和为3
					int wei[] = new int[2];
					int c = 0;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei[c++] = j+k;
							break;
						}
					}
					hrsanlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]}}).zhuanzhi(direct, new int[] {4, wei[0] - j, wei[1] - j}));
				}else if(count == 2 * 9) {
					int wei[] = new int[3];
					int c = 0;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei[c++] = j+k;
							break;
						}
					}
					hrerlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]},{i,wei[2]}}).zhuanzhi(direct, new int[] {4, wei[0] - j,wei[1] - j,wei[2] - j}));
				}
			}
		}
	}
	
	private void addRuo(int[] arr, int i, int direct) {
		for(int j = 0; j < arr.length; j++) {
			//强2连的专门识别方法---其实也可以正则匹配---一个或者多个的方法
			if(j + 5 < arr.length) {
				int count = arr[j] + arr[j+1] + arr[j+2] + arr[j+3] + arr[j+4] + arr[j+5];
				if(count == 2) {
					int wei[] = new int[2];
					int c = 0;
					int kong[] = new int[4];
					int kc = 0;
					for(int k = 0; k < 6; k++) {
						if(arr[j+k] == 1) {
							wei[c++] = j+k;
							break;
						}else {
							kong[kc++] = j+k;
						}
					}
					if((wei[1] - wei[0] == 1) && (wei[0] > j) && (wei[1] < j+5)) {//是强2连，否则是也若2连-----会被后面的程序所识别出来
						//j+1 到j+4中选择
						erlian.add(new BJ(new int[] {i,j}, new int[] {i, j+5}, new int[][] {{i,kong[0]},{i,kong[1]},{i,kong[2]},{i,kong[3]}})
																.zhuanzhi(direct, new int[] {5, kong[0] - j, kong[1] - j, kong[2] - j, kong[3] - j}));
					}
				}
			}
			if(j + 4 < arr.length) {
				int count = arr[j] + arr[j+1] + arr[j+2] + arr[j+3] + arr[j+4];
				if(count == 4) {//是为弱4连
					int wei = -1;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei = j+k;
							break;
						}
					}
					rsilian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei}}).zhuanzhi(direct, new int[] {4, wei - j}));
				}else if(count == 3) {//连续5个，和为3
					int wei[] = new int[2];
					int c = 0;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei[c++] = j+k;
							break;
						}
					}
					//优化顺序
					if(wei[1] - wei[0] == 1 && wei[0] == j) {
						//交换
						jiaohuanXY(wei);
					}
					rsanlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]}}).zhuanzhi(direct, new int[] {4, wei[0] - j, wei[1] - j}));
				}else if(count == 2) {
					int wei[] = new int[3];
					int c = 0;
					for(int k = 0; k < 5; k++) {
						if(arr[j+k] == 0) {
							wei[c++] = j+k;
							break;
						}
					}
					rerlian.add(new BJ(new int[] {i,j}, new int[] {i, j+4}, new int[][] {{i,wei[0]},{i,wei[1]},{i,wei[2]}}).zhuanzhi(direct, new int[] {4, wei[0] - j,wei[1] - j,wei[2] - j}));
				}
			}
		}
	}

	private void jiaohuanXY(int[] wei) {
		int tem = wei[1];
		wei[1] = wei[0];
		wei[0] = tem;
	}

	private int[] zuobiao(int[] arr, int step, int direct) {
		if(direct == 1) {
		}else if(direct == 2) {
			int temp = arr[0];
			arr[0] = arr[1];
			arr[1] = temp;
		}else if(direct == 3) {//捺
			arr[0] += step;
		}else if(direct == 4) {//撇\\
			arr[0] -= step;
		}
		return arr;
	}
	
	private int[][] zuobiao(int[][] arrs, int step, int direct) {
		for(int[] arr : arrs) {
			if(direct == 1) {
			}else if(direct == 2) {
				int temp = arr[0];
				arr[0] = arr[1];
				arr[1] = temp;
			}else if(direct == 3) {//捺
				arr[0] += step;
			}else if(direct == 4) {//撇\\
				arr[0] -= step;
			}
		}
		return arrs;
	}
	
	private void addQiangh(String str, int i , int direct) throws Exception {
		Matcher m5 = hp5.matcher(str);
		if(m5.find()) {
			throw new Exception("hei win");
		}
		Matcher m = hp4.matcher(str);
		while(m.find()) {
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei},{i,wei+5}});
			bj.zhuanzhi(direct, new int[] {3, 0, 5});
			hsilian.add(bj);
		}
		Matcher m3 = hp3.matcher(str);
		Matcher m32 = hp32.matcher(str);
		Matcher m33 = hp33.matcher(str);
		Matcher m34 = hp34.matcher(str);
		Matcher m35 = hp35.matcher(str);
		boolean f1 = false;
		boolean f2 = false;
		while(m3.find()) {
			f1 = true;
			int wei = m3.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+6}, new int[][] {{i,wei+1},{i,wei+5}});
			bj.zhuanzhi(direct, new int[] {6, 1, 5});
			hsanlian.add(bj);//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
		}
		if(!f1) {
			while(m32.find()) {
				f2 = true;
				int wei = m32.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+5}, new int[][] {{i,wei+1}});
				bj.zhuanzhi(direct, new int[] {2, 1});
				hsanlian.add(bj);
			}
		}
		if(!f1 && !f2) {
			while(m33.find()) {
				int wei = m33.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+4}});
				bj.zhuanzhi(direct, new int[] {2, 4});
				hsanlian.add(bj);
			}
		}
		if(m34.find()) {
			int wei = m34.start();
			sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
		}
		if(m35.find()) {
			int wei = m35.start();
			sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+2}}));
		}
		//强2连
		Matcher m2 = hp2.matcher(str);
		while(m2.find()) {
			int wei = m2.start();//多次匹配，以后处理
			int c0 = m2.group(1).length();
			int c1 = m2.group(2).length();
			if(c0 + c1 >= 4) {
				int luo = c0 == 1 ? wei+ c0 +2 : c0 == 3 ? wei+c0-1 : wei+c0+2;
				if(luo == wei) {
					herlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					herlian.add(new BJ(new int[] {i,wei+c0}, new int[] {i, wei+c0+1}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
				}
			}
		}
		Matcher m1 = hp1.matcher(str);
		while(m1.find()) {//while的方式好点---因为可以发现很多组
			int wei = m1.start();
			int c0 = m1.group(1).length();
			int c1 = m1.group(2).length();
			if(c0 + c1 >= 5) {
				int luo = c0 == 1 ? wei+c0+1 : c0 == 4 ? wei+c0-1 : wei+c0+1;
				if(luo == wei) {
					hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					hyilian.add(new BJ(new int[] {i,wei+c0}, new int[] {i, wei+c0}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
				}
			}
		}
	}
	
	private void addQiang(String str, int i , int direct) throws Exception {
		Matcher m5 = p5.matcher(str);
		if(m5.find()) {
			wulian.add(new BJ(null,null,null));
			throw new Exception("bai win");
		}
		Matcher m = p4.matcher(str);
		while(m.find()) {
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei},{i,wei+5}});
			bj.zhuanzhi(direct, new int[] {3, 0, 5});
			silian.add(bj);
		}
		Matcher m3 = p3.matcher(str);
		Matcher m32 = p32.matcher(str);
		Matcher m33 = p33.matcher(str);
		Matcher m34 = p34.matcher(str);
		Matcher m35 = p35.matcher(str);
		boolean f1 = false;
		boolean f2 = false;
		while(m3.find()) {
			f1 = true;
			int wei = m3.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1},{i,wei+5}});
			bj.zhuanzhi(direct, new int[] {2, 1, 5});
			sanlian.add(bj);//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
		}
		if(!f1) {
			while(m32.find()) {
				f2 = true;
				int wei = m32.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+1}});
				bj.zhuanzhi(direct, new int[] {2, 1});
				sanlian.add(bj);
			}
		}
		if(!f1 && !f2) {
			while(m33.find()) {
				int wei = m33.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+4}});
				bj.zhuanzhi(direct, new int[] {2, 4});
				sanlian.add(bj);
			}
		}
		if(m34.find()) {
			int wei = m34.start();
			sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
		}
		if(m35.find()) {
			int wei = m35.start();
			sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+2}}));
		}
		//强2连
		Matcher m2 = p2.matcher(str);
		while(m2.find()) {
			int wei = m2.start();//多次匹配，以后处理
			int c0 = m2.group(1).length();
			int c1 = m2.group(2).length();
			if(c0 + c1 >= 4) {
				int luo = c0 == 1 ? wei+ c0 +2 : c0 == 3 ? wei+c0-1 : wei+c0+2;
				if(luo == wei) {
					erlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					erlian.add(new BJ(new int[] {i,wei+c0}, new int[] {i, wei+c0+1}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
				}
			}
		}
		//
		Matcher m1 = p1.matcher(str);
		while(m1.find()) {//while的方式好点---因为可以发现很多组
			int wei = m1.start();
			int c0 = m1.group(1).length();
			int c1 = m1.group(2).length();
			if(c0 + c1 >= 5) {
				int luo = c0 == 1 ? wei+ c0 +1 : c0 == 4 ? wei+c0-1 : wei+c0+1;
				if(luo == wei) {
					yilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					yilian.add(new BJ(new int[] {i,wei+c0}, new int[] {i, wei+c0}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
				}
			}
		}
	}

	private static String arrToString(int[] is) {
		String rs = "";
		for(int i : is) {
			rs += i;
		}
		return rs;
	}

	static Pattern hp5 = Pattern.compile("1{5,}");
	static Pattern hp4 = Pattern.compile("09{4,}0");
	static Pattern hp3 = Pattern.compile("009{3}00");
	static Pattern hp32 = Pattern.compile("009{3}0");
	static Pattern hp33 = Pattern.compile("09{3}00");
	static Pattern hp34 = Pattern.compile("099090");
	static Pattern hp35 = Pattern.compile("090990");
	static Pattern hp2 = Pattern.compile("(0{1,})99(0{1,})");
	static Pattern hp1 = Pattern.compile("(0{1,})9(0{1,})");
	
	static Pattern p5 = Pattern.compile("1{5,}");
	static Pattern p4 = Pattern.compile("01{4,}0");
	static Pattern p3 = Pattern.compile("001{3}00");
	static Pattern p32 = Pattern.compile("001{3}0");
	static Pattern p33 = Pattern.compile("01{3}00");
	static Pattern p34 = Pattern.compile("011010");
	static Pattern p35 = Pattern.compile("010110");
	static Pattern p2 = Pattern.compile("(0{1,})11(0{1,})");
	static Pattern p1 = Pattern.compile("(0{1,})1(0{1,})");
	/**
	 * 只判断 连3 或者连4； 不判断前后 是否是null；这个作为进一步判断的根据
	 * @param 
	 * @author lishaoping
	 * @Date 2018年10月3日
	 * @Package com.bj58.im.client.algri
	 * @return Map<String,List<List<Point>>>
	 */
	private static Map<String, List<List<Point>>> shibiesilian(Integer[][] zi, int i) {
		//i == 0 表示自己：白子  1表示黑子
		Map<String, List<List<Point>>> map = new HashMap<>();
		List<List<Point>> silian = new ArrayList<>();
		
		for(Integer[] line : zi) {
			int count4 = 0;
			int count3 = 0;
			int count32 = 0;
			int count2 = 0;
			int c = 0;
			for(Integer col : line) {
				//四连
				if(col == 0) {
					count4++;
					count3++;
					if(count4 == 4) {
						System.out.println("si lian");
						if((line.length > c + 1 && line[c + 1] == null) && ( c >= 4 && line[c - 4] == null)) {
							System.out.println("胜利：下一步出招：");
							List<Point> li = new ArrayList<>();
							li.add(ins.new Point(c - 3, c));
						}
					}
					if(count3 == 3) {
						System.out.println("三连");
						if((line.length > c + 1 && line[c + 1] == null) && ( c >= 3 && line[c - 3] == null)
								&& ((line.length > c + 2 && line[c+2] == null)||(c >= 4 && line[c - 4] == null))) {
							System.out.println("牵制：下一步出招：");
							List<Point> li = new ArrayList<>();
							li.add(ins.new Point(c - 2, c));
						}//另一种情形，中间隔着一个 点 也可以牵制
					}
				}else {
					count4 = 0;
					count3 = 0;
				}
				if(col == 0 || col == null) {//三连的有空的情景
					count32++;
					if(count32 == 4 && (line[c - 1] == null || line[c-2] == null)
							&& line[c - 3] == 0 && line[c] == 0) {
						System.out.println("牵制：对方必防---但是意义不大");
					}
				}else {
					count32 = 0;
				}
				c++;
			}
			
		}
		return null;
	}
	
	class Point{
		Integer x;
		Integer y;
		public Point(Integer x, Integer y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			ins.duan(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
