package com.bj58.im.client.algri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Wfisveszposisntq {

	static Wfisveszposisntq ins = new Wfisveszposisntq();
	public static void main(String[] args) {
//		start();
//		Matcher m = hp32.matcher("00999000009990");
//		while(m.find()) {
//			System.out.println(m.start());
//		}
	}

	private static void start() {
		Integer[][] zi = new Integer[100][100];
		//遍历n行n列，和斜对角线，识别连续的点
		Map<String, List<List<Point>>> list = shibiesilian(zi, 0);
	}

	private void duan() {
		int[][] start = new int[99][99];//白出1 ， 黑出9
		//白先
		start[49][49] = 1;
		while(true) {
			Map<String, List<BJ>> bai = kanbai(start);
			Map<String, List<BJ>> hei = kanhei(start);
			//自己是白方
			//1.先看己方一步胜利，再看对方一步胜利，在看己方2步胜利，.....
			if(bai.containsKey("q_si_l")) {
				List<BJ> qsi = bai.get("q_si_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(bai.containsKey("r_si_l")) {
				List<BJ> qsi = bai.get("r_si_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(hei.containsKey("q_si_l")) {
				List<BJ> qsi = hei.get("q_si_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(hei.containsKey("r_si_l")) {
				List<BJ> qsi = hei.get("r_si_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(bai.containsKey("q_san_l")) {//2招
				List<BJ> qsi = bai.get("q_san_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(bai.containsKey("r_san_l")) {//2招
				List<BJ> qsi = bai.get("r_san_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}else if(hei.containsKey("q_san_l")) {//2招
				List<BJ> qsi = hei.get("q_san_l");
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}
//			else if(hei.containsKey("r_san_l")) {//2招 先不阻止
//				List<BJ> qsi = bai.get("r_san_l");
//				int[] chu = qsi.get(0).k[0];
//				start[chu[0]][chu[1]] = 1;
//			}
			else if(bai.containsKey("q_er_l")) {//强2连 综合判断
				List<BJ> qsi = bai.get("q_er_l");
				//
				int[] chu = qsi.get(0).k[0];
				start[chu[0]][chu[1]] = 1;
			}
			
			//等待输入出动
		}
	}
	
	private Map<String, List<BJ>> kanhei(int[][] zi) {
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
			Matcher m = hp4.matcher(str);
			while(m.find()) {
				int wei = m.start();//多次匹配，以后处理
				hsilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei-1},{i,wei+4}}));
			}
			Matcher m3 = hp3.matcher(str);
			Matcher m32 = hp32.matcher(str);
			Matcher m33 = hp33.matcher(str);
			boolean f1 = false;
			boolean f2 = false;
			while(m3.find()) {
				f1 = true;
				int wei = m.start();//多次匹配，以后处理
				hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1},{i,wei+3}}));//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
			}
			if(!f1) {
				while(m32.find()) {
					f2 = true;
					int wei = m.start();//多次匹配，以后处理
					hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1}}));
				}
			}
			if(!f1 && !f2) {
				while(m33.find()) {
					int wei = m.start();//多次匹配，以后处理
					hsanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
				}
			}
			Matcher m1 = hp1.matcher(str);
			while(m1.find()) {//while的方式好点---因为可以发现很多组
				int wei = m1.start();
				int c0 = m1.group(1).length();
				int c1 = m1.group(2).length();
				if(c0 + c1 == 5) {
					int luo = c0 == 1 ? wei+1 : c0 == 4 ? wei+4 : wei;
					if(luo == wei) {
						hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}));
					}else {
						hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,luo}}));
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
							if(zi[i][j+k] == 1) {
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
	
	List<BJ> hsilian = new ArrayList<>();
	List<BJ> hrsilian = new ArrayList<>();
	List<BJ> hsanlian = new ArrayList<>();
	List<BJ> hrsanlian = new ArrayList<>();
	List<BJ> herlian = new ArrayList<>();
	List<BJ> hrerlian = new ArrayList<>();
	List<BJ> hyilian = new ArrayList<>();
	
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
	 */
	private Map<String, List<BJ>> kanbai(int[][] zi) {
		//i == 0 表示自己：白子  1表示黑子
		Map<String, List<BJ>> map = new HashMap<>();
		
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
			Matcher m = p4.matcher(str);
			while(m.find()) {
				int wei = m.start();//多次匹配，以后处理
				silian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei-1},{i,wei+4}}));
			}
			Matcher m3 = p3.matcher(str);
			Matcher m32 = p32.matcher(str);
			Matcher m33 = p33.matcher(str);
			boolean f1 = false;
			boolean f2 = false;
			while(m3.find()) {
				f1 = true;
				int wei = m.start();//多次匹配，以后处理
				sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1},{i,wei+3}}));//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
			}
			if(!f1) {
				while(m32.find()) {
					f2 = true;
					int wei = m.start();//多次匹配，以后处理
					sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1}}));
				}
			}
			if(!f1 && !f2) {
				while(m33.find()) {
					int wei = m.start();//多次匹配，以后处理
					sanlian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}}));
				}
			}
			Matcher m1 = p1.matcher(str);
			while(m1.find()) {//while的方式好点---因为可以发现很多组
				int wei = m1.start();
				int c0 = m1.group(1).length();
				int c1 = m1.group(2).length();
				if(c0 + c1 == 5) {
					int luo = c0 == 1 ? wei+1 : c0 == 4 ? wei+4 : wei;
					if(luo == wei) {
						yilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}));
					}else {
						yilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,luo}}));
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
	
	private void addQiangh(String str, int i , int direct) {
		Matcher m = hp4.matcher(str);
		while(m.find()) {
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei-1},{i,wei+4}});
			bj.zhuanzhi(direct, new int[] {3, -1, 4});
			hsilian.add(bj);
		}
		Matcher m3 = hp3.matcher(str);
		Matcher m32 = hp32.matcher(str);
		Matcher m33 = hp33.matcher(str);
		boolean f1 = false;
		boolean f2 = false;
		while(m3.find()) {
			f1 = true;
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1},{i,wei+3}});
			bj.zhuanzhi(direct, new int[] {2, -1, 3});
			hsanlian.add(bj);//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
		}
		if(!f1) {
			while(m32.find()) {
				f2 = true;
				int wei = m.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1}});
				bj.zhuanzhi(direct, new int[] {2, -1});
				hsanlian.add(bj);
			}
		}
		if(!f1 && !f2) {
			while(m33.find()) {
				int wei = m.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}});
				bj.zhuanzhi(direct, new int[] {2, 3});
				hsanlian.add(bj);
			}
		}
		Matcher m1 = hp1.matcher(str);
		while(m1.find()) {//while的方式好点---因为可以发现很多组
			int wei = m1.start();
			int c0 = m1.group(1).length();
			int c1 = m1.group(2).length();
			if(c0 + c1 == 5) {
				int luo = c0 == 1 ? wei+1 : c0 == 4 ? wei+4 : wei;
				if(luo == wei) {
					hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					hyilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
				}
			}
		}
	}
	
	private void addQiang(String str, int i , int direct) {
		Matcher m = p4.matcher(str);
		while(m.find()) {
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei-1},{i,wei+4}});
			bj.zhuanzhi(direct, new int[] {3, -1, 4});
			silian.add(bj);
		}
		Matcher m3 = p3.matcher(str);
		Matcher m32 = p32.matcher(str);
		Matcher m33 = p33.matcher(str);
		boolean f1 = false;
		boolean f2 = false;
		while(m3.find()) {
			f1 = true;
			int wei = m.start();//多次匹配，以后处理
			BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1},{i,wei+3}});
			bj.zhuanzhi(direct, new int[] {2, -1, 3});
			sanlian.add(bj);//因为必胜，所以可以定 一定是哪些点，而不是还要再判断
		}
		if(!f1) {
			while(m32.find()) {
				f2 = true;
				int wei = m.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei-1}});
				bj.zhuanzhi(direct, new int[] {2, -1});
				sanlian.add(bj);
			}
		}
		if(!f1 && !f2) {
			while(m33.find()) {
				int wei = m.start();//多次匹配，以后处理
				BJ bj = new BJ(new int[] {i,wei}, new int[] {i, wei+2}, new int[][] {{i,wei+3}});
				bj.zhuanzhi(direct, new int[] {2, 3});
				sanlian.add(bj);
			}
		}
		Matcher m1 = p1.matcher(str);
		while(m1.find()) {//while的方式好点---因为可以发现很多组
			int wei = m1.start();
			int c0 = m1.group(1).length();
			int c1 = m1.group(2).length();
			if(c0 + c1 == 5) {
				int luo = c0 == 1 ? wei+1 : c0 == 4 ? wei+4 : wei;
				if(luo == wei) {
					yilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,wei-1},{i,wei+1}}).zhuanzhi(direct,  new int[] {0, -1, 1}));
				}else {
					yilian.add(new BJ(new int[] {i,wei}, new int[] {i, wei}, new int[][] {{i,luo}}).zhuanzhi(direct,  new int[] {0, luo - wei}));
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

	static Pattern hp4 = Pattern.compile("09{4,}0");
	static Pattern hp3 = Pattern.compile("009{3}00");
	static Pattern hp32 = Pattern.compile("009{3}0");
	static Pattern hp33 = Pattern.compile("09{3}00");
	static Pattern hp1 = Pattern.compile("(0{1,})9(0{1,})");
	
	static Pattern p4 = Pattern.compile("01{4,}0");
	static Pattern p3 = Pattern.compile("001{3}00");
	static Pattern p32 = Pattern.compile("001{3}0");
	static Pattern p33 = Pattern.compile("01{3}00");
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
}
