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
		start();
	}

	private static void start() {
		Integer[][] zi = new Integer[100][100];
		//遍历n行n列，和斜对角线，识别连续的点
		Map<String, List<List<Point>>> list = shibiesilian(zi, 0);
	}

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
	private static Map<String, List<BJ>> kan(int[][] zi, int color) {
		//i == 0 表示自己：白子  1表示黑子
		Map<String, List<BJ>> map = new HashMap<>();
		List<BJ> silian = new ArrayList<>();
		List<BJ> rsilian = new ArrayList<>();
		map.put("q_si_l", silian);
		map.put("r_si_l", rsilian);
		//1.横竖撇捺  先看自己
		for(int i = 0; i < zi.length; i++) {
			//tostring
			String str = arrToString(zi[i]);
			Matcher m = p4.matcher(str);
			if(m.find()) {
				int wei = m.start();//多次匹配，以后处理
				silian.add(new BJ(new int[] {i,wei}, new int[] {i, wei+3}, new int[][] {{i,wei-1},{i,wei+4}}));
			}
			//
			for(int j = 0; j < zi[0].length; j++) {
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
					}
				}
			}
		}
		
		return null;
	}
	
	private static String arrToString(int[] is) {
		String rs = "";
		for(int i : is) {
			rs += i;
		}
		return rs;
	}

	static Pattern p4 = Pattern.compile("01{4,}0");
	
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
