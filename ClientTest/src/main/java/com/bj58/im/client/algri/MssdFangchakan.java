package com.bj58.im.client.algri;

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

	private static void test() {
		//1.构造出一个初始完整形态的矩阵
		int[][] ding = init1();
		int[][] zhong = init2();
		//2.构造6种基本变换
		trans_univers(ding, zhong, new int[] {1,2,3,4},new int[] {1,2,3,4}, new int[] {0,2});
		trans_univers(ding, zhong, new int[] {2,6,7,4},new int[] {2,6,10,7}, new int[] {0,1});
		trans_univers(ding, zhong, new int[] {5,6,7,8},new int[] {9,10,11,12}, new int[] {0,2});
		trans_univers(ding, zhong, new int[] {1,5,8,3},new int[] {5,12,8,4}, new int[] {0,1});
		trans_univers(ding, zhong, new int[] {1,2,6,5},new int[] {1,6,9,5}, new int[] {1,2});
		trans_univers(ding, zhong, new int[] {3,4,7,8},new int[] {3,7,11,8}, new int[] {1,2});
		//3.使用6种基本变换来构造出其他的形态
		//4.任意一种形态，恢复到原样时候的步骤流程
		//5.寻找一种通用的变换方法-过程-算法：可以找出来所有的不同的形态
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
			int[] ca = ding[x];
			int tem = ca[axis[0]];
			ca[axis[0]] = ca[axis[1]];
			ca[axis[1]] = tem;
		}
	}

	private static void xuhao(int[][] ding, int[] xuhao) {
		int[] cache = ding[xuhao[3]];
		ding[xuhao[3]] = ding[xuhao[2]];
		ding[xuhao[2]] = ding[xuhao[1]];
		ding[xuhao[1]] = ding[xuhao[0]];
		ding[xuhao[0]] = cache;
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
