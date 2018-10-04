package com.bj58.im.client.algri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 整体描述：
 * 目的：构造出12步-14步范围内可以产生的不同的形态种类：是否可以达到：26873856种
 * 形态 ：矩阵表示：
 * 变换：矩阵表示：
 * @ClassName:MssdFangchakan
 * @Description:
 * @Author lishaoping
 * @Date 2018年10月4日
 * @Version V1.0
 * @Package com.bj58.im.client.algri
 */
public class MssdFangchakan {

	public static void main(String[] args) {
		test();
	}

	private static void writeTofile() {
		System.out.println("sssssssss");
		try {
			FileOutputStream out = new FileOutputStream("D:\\mf.txt", true);
			StringBuilder b = new StringBuilder();
			int i = 0;
			for(Entry<String, String> en : map.entrySet()) {
				b.append(en.getKey() + ";" + en.getValue() + "\r\n");
				System.out.println(i++);
			}
			System.out.println("write");
			out.write(b.toString().getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Map<String, String> map = new HashMap<String, String>();
	
	private static void test() {
		//1.构造出一个初始完整形态的矩阵
		int[][] ding = init1();
		int[][] zhong = init2();
		//2.构造6种基本变换
//		trans_univers(ding, zhong, new int[] {1,2,3,4},new int[] {1,2,3,4}, new int[] {0,2});
//		trans_univers(ding, zhong, new int[] {2,6,7,4},new int[] {2,6,10,7}, new int[] {0,1});
//		trans_univers(ding, zhong, new int[] {5,6,7,8},new int[] {9,10,11,12}, new int[] {0,2});
//		trans_univers(ding, zhong, new int[] {1,5,8,3},new int[] {5,12,8,4}, new int[] {0,1});
//		trans_univers(ding, zhong, new int[] {1,2,6,5},new int[] {1,6,9,5}, new int[] {1,2});
//		trans_univers(ding, zhong, new int[] {3,4,7,8},new int[] {3,7,11,8}, new int[] {1,2});
//		matrixtoString(ding, zhong);
		//3.使用6种基本变换来构造出其他的形态:递归16次
		map.put(matrixtoString(ding, zhong), "");
		getByGui(ding, zhong, 12, 0, "");
		//4.任意一种形态，恢复到原样时候的步骤流程
		//5.寻找一种通用的变换方法-过程-算法：可以找出来所有的不同的形态
	}

	private static void getByGui(int[][] ding, int[][] zhong, int step, int curr, String lujing) {
//		System.out.println(step);
		if(curr > step) {
			return;
		}
		if(map.size() > 100000) {
			System.out.println(map.size() + "   dont ");
			writeTofile();
			map.clear();
		}
		int[][] cc = copy(ding);
		int[][] c1 = copy(zhong);
		trans_univers(cc,c1, new int[] {1,2,3,4},new int[] {1,2,3,4}, new int[] {0,2});
		map.put(matrixtoString(cc, c1), lujing + 1);
		getByGui(cc, c1, step, curr + 1, lujing + 1);
		
		int[][] cc2 = copy(ding);
		int[][] c2 = copy(zhong);
		trans_univers(cc2, c2, new int[] {2,6,7,4},new int[] {2,6,10,7}, new int[] {0,1});
		map.put(matrixtoString(cc2, c2), lujing + 2);
		getByGui(cc2, c2, step, curr + 1, lujing + 2);
		
		int[][] cc3 = copy(ding);
		int[][] c3 = copy(zhong);
		trans_univers(cc3, c3, new int[] {5,6,7,8},new int[] {9,10,11,12}, new int[] {0,2});
		map.put(matrixtoString(cc3, c3), lujing + 3);
		getByGui(cc3, c3, step, curr + 1, lujing + 3);
		
		
		int[][] cc4 = copy(ding);
		int[][] c4 = copy(zhong);
		trans_univers(cc4, c4, new int[] {1,5,8,3},new int[] {5,12,8,4}, new int[] {0,1});
		map.put(matrixtoString(cc4, c4), lujing + 4);
		getByGui(cc4, c4, step, curr + 1, lujing + 4);
		
		int[][] cc5 = copy(ding);
		int[][] c5 = copy(zhong);
		trans_univers(cc5, c5, new int[] {1,2,6,5},new int[] {1,6,9,5}, new int[] {1,2});
		map.put(matrixtoString(cc5, c5), lujing + 5);
		getByGui(cc5, c5, step, curr + 1, lujing + 5);
		
		int[][] cc6 = copy(ding);
		int[][] c6 = copy(zhong);
		trans_univers(cc6, c6, new int[] {3,4,7,8},new int[] {3,7,11,8}, new int[] {1,2});
		map.put(matrixtoString(cc6, c6), lujing + 6);
		getByGui(cc6, c6, step, curr + 1, lujing + 6);
	}

	private static int[][] copy(int[][] ding){
		int[][] rs = new int[ding.length][ding[0].length];
		int i = 0;
		for(int[] s : ding) {
			int j = 0;
			for(int x : s) {
				rs[i][j] = ding[i][j];
				j++;
			}
			i++;
		}
		return rs;
	}
	
	private static String matrixtoString(int[][] ding, int[][] zhong) {
		String rs = "";
		for(int[] d : ding) {
			for(int x : d) {
				rs += x;
			}
		}
		for(int[] d : zhong) {
			for(int x : d) {
				rs += x;
			}
		}
		return rs;
	}

	/**
	 * 绕x轴旋转90°
	 * @param 
	 * @author lishaoping
	 * @Date 2018年10月4日
	 * @Package com.bj58.im.client.algri
	 * @return void
	 */
	private static void trans1(int[][] ding, int[][] zhong) {
		trans11(ding);
		//2.zhong
		trans11(zhong);
	}

	private static void trans11(int[][] ding) {
		int[] cache = ding[3];
		for(int i = 3; i >= 1; i--) {
			ding[i] = ding[i - 1];
		}
		ding[0] = cache;
		//列交换
		for(int i = 0; i < 4; i++) {
			int[] c = ding[i];
			int ca = c[0];
			c[0] = c[2];
			c[2] = ca;
		}
	}
	
	private static void trans_univers(int[][] ding, int[][] zhong, int[] xuhao, int[] xuhao2, int[] axis) {
		//ding 2-6-7-3
		//zhong 2-6-10-7
		//最后一列
		xuhao(ding, xuhao);
		xuhao(zhong, xuhao2);
		axis(ding, xuhao, axis);
		axis(zhong, xuhao2, axis);
	}
	
	private static void trans2(int[][] ding, int[][] zhong, int[] xuhao, int[] axis) {
		//ding 2-6-7-3
		//zhong 2-6-10-7
		//最后一列
		xuhao(ding, xuhao);
		xuhao(zhong, xuhao);
		axis(ding, xuhao, axis);
		axis(zhong, xuhao, axis);
	}

	private static void axis(int[][] ding, int[] xuhao, int[] axis) {
		for(int x : xuhao) {
			int[] ca = ding[x - 1];
			int tem = ca[axis[0]];
			ca[axis[0]] = ca[axis[1]];
			ca[axis[1]] = tem;
		}
	}

	private static void xuhao(int[][] ding, int[] xuhao) {
		int[] cache = ding[xuhao[3] - 1];
		ding[xuhao[3] - 1] = ding[xuhao[2] - 1];
		ding[xuhao[2] - 1] = ding[xuhao[1] - 1];
		ding[xuhao[1] - 1] = ding[xuhao[0] - 1];
		ding[xuhao[0] - 1] = cache;
	}
	
	private static void trans3(int[][] ding, int[][] zhong) {
		
	}
	
	private static void trans4(int[][] ding, int[][] zhong) {
		
	}
	
	private static void trans5(int[][] ding, int[][] zhong) {
		
	}
	
	private static void trans6(int[][] ding, int[][] zhong) {
		
	}


	
	private static int[][] init2() {
		int[][] zhong = {
				{5,1,0},
				{0,1,2},
				{6,1,0},
				{0,1,4},
				{5,0,4},
				{5,0,2},
				{6,0,2},
				{6,0,4},
				{5,3,0},
				{0,3,2},
				{6,3,0},
				{0,3,4}
		};
		return zhong;
	}

	private static int[][] init1() {
		int[][] ding = {
				{5,1,4},
				{5,1,2},
				{6,1,2},
				{6,1,4},
				{5,3,4},
				{5,3,2},
				{6,3,2},
				{6,3,4}
		};//行列new int[8][3] 
		
		return ding;
	}
}
