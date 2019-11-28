package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;

import com.google.common.collect.Lists;

public class QuestionJishuiTest {

	public static void main(String[] args) {
		int total = jishuiTotal(new int[][] {{3,2,3},{4,1,5},{1,2,6}});
	}

	private static int jishuiTotal(int[][] arr) {
		//本圈积水深度
		//修正积水深度
		//下一圈积水深度
		int[][] shendu = new int[arr.length][arr[0].length];
		int quanNum = arr.length / 2 + arr.length % 2;
		int currQuan = 0;
		List<int[]> xiuz = Lists.newArrayList();
		while(currQuan < quanNum) {
			int startX = currQuan;
			int endX = arr.length - 1 - startX;
			int startY = currQuan;
			int endY = arr[0].length - 1 - startY;
			int xP = startX;
			int yP = startY;
			
			//向右
			while(yP <= endY) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == 0 || yP == 0) {
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP - 1][yP] - arr[xP][yP] + shendu[xP - 1][yP];
				}
				yP++;
			}
			yP = endY;//转折点
			if(xP == 0 || yP == 0) {//忽略
			}else {
				int shendu2 = arr[xP][yP + 1] - arr[xP][yP] + shendu[xP][yP + 1];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			xP++;
			//向下
			while(xP <= endX) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == 0 || yP == 0) {
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP][yP + 1] - arr[xP][yP] + shendu[xP][yP + 1];
				}
				xP++;
			}
			xP = endX;//转折点
			if(xP == 0 || yP == 0) {//忽略
			}else {
				int shendu2 = arr[xP + 1][yP] - arr[xP][yP] + shendu[xP + 1][yP];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			yP--;
			//向左
			while(yP >= startY) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == 0 || yP == 0) {
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP + 1][yP] - arr[xP][yP] + shendu[xP + 1][yP];
				}
				yP--;
			}
			yP = startY;//转折点
			if(xP == 0 || yP == 0) {//忽略
			}else {
				int shendu2 = arr[xP][yP - 1] - arr[xP][yP] + shendu[xP][yP - 1];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			xP++;
			//向上--且不到起点
			while(xP > startX) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == 0 || yP == 0) {
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP][yP - 1] - arr[xP][yP] + shendu[xP][yP - 1];
				}
				xP--;
			}
			xP = startX;//转折点
			if(xP == 0 || yP == 0) {//忽略
			}else {
				int shendu2 = arr[xP][yP - 1] - arr[xP][yP] + shendu[xP][yP - 1];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			//开始修正
			
		}
		return 0;
	}
}
