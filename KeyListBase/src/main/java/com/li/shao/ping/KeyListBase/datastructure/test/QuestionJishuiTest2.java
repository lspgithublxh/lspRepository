package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;

import com.google.common.collect.Lists;

public class QuestionJishuiTest2 {

	public static void main(String[] args) {
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,0,5},{1,2,6}});
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,0,5},{1,1,6}});
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,1,5},{1,1,6}});
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,1,5},{1,-1,6}});
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,1,5},{1,-1,-4}});
//		int total = jishuiTotal(new int[][] {{-3,2,3},{4,1,5},{1,-1,-4}});
//		int total = jishuiTotal(new int[][] {{3,2,3},{4,6,5},{1,2,4}});
//		int total = jishuiTotal(new int[][] {{3,2,3,3},{4,1,1,4},{1,2,4,5},{1,6,8,9}});
//		int total = jishuiTotal(new int[][] {{3,2,3,3,5},{4,1,1,4,6},{1,2,4,5,6},{1,6,8,9,6},{1,6,8,9,6}});
//		int total = jishuiTotal(new int[][] {{3,2,3,3,5},{4,1,1,4,6},{4,2,4,5,6},{1,6,8,9,6},{1,6,8,9,6}});

//		int total = jishuiTotal(new int[][] {{3,3,3,3,5},{4,1,1,4,6},{4,2,4,5,6},{1,6,8,9,6},{1,6,8,9,6}});
		int total = jishuiTotal(new int[][] {{3,3,3,3,5},{4,1,1,2,6},{4,2,4,1,6},{6,1,1,2,6},{1,6,8,9,6}});

		System.out.println(total);
		
		
	}

	private static int jishuiTotal(int[][] arr) {
		//本圈积水深度
		//修正积水深度
		//下一圈积水深度
		int[][] shendu = new int[arr.length][arr[0].length];
		int quanNum = arr.length / 2 + arr.length % 2;
		int currQuan = 0;
		List<int[]> xiuz = Lists.newArrayList();
		List<int[]> lianxuKen = Lists.newArrayList();
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
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//TODO
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
				if(xP == arr.length - 1 || yP == arr[0].length - 1) {//TODO yP == endY
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP][yP + 1] - arr[xP][yP] + shendu[xP][yP + 1];
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//TODO
				}
				xP++;
			}
			xP = endX;//转折点
			if(xP == 0 || (yP == endY && endY == arr[0].length - 1)) {//忽略
			}else {
				int shendu2 = arr[xP + 1][yP] - arr[xP][yP] + shendu[xP + 1][yP];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			yP--;
			//向左
			while(yP >= startY) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == arr.length - 1 || yP == 0) {//TODO
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP + 1][yP] - arr[xP][yP] + shendu[xP + 1][yP];
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//TODO
				}
				yP--;
			}
			yP = startY;//转折点
			if((xP == endX && endX == arr.length - 1) || yP == 0) {//忽略
			}else {
				int shendu2 = arr[xP][yP - 1] - arr[xP][yP] + shendu[xP][yP - 1];
				shendu2 = shendu2 < 0 ? 0 : shendu2;
				shendu[xP][yP] = shendu[xP][yP] > shendu2 ? shendu2 : shendu[xP][yP];
			}
			xP--;
			//向上--且不到起点
			while(xP > startX) {//(xp,yp)位置的深度
				xiuz.add(new int[] {xP,yP});
				if(xP == endX || yP == 0) {//TODO
					shendu[xP][yP] = 0 - arr[xP][yP];//负数不计算
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//负数归零
				}else {
					shendu[xP][yP] = arr[xP][yP - 1] - arr[xP][yP] + shendu[xP][yP - 1];
					shendu[xP][yP] = shendu[xP][yP] < 0 ? 0 : shendu[xP][yP];//TODO
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
			//开始修正.两个方向
			//先定漏洞点
			while(true) {
				int kenMowei = -1;
				boolean startLian = false;
				int rightDuan = -1;
				int leftDuan = xiuz.size() - 1;
				int total = 0;
				for(int i = 0; i < xiuz.size();) {
					int[] node = xiuz.get(i);
					
					if(shendu[node[0]][node[1]] > 0) {//是坑
						lianxuKen.add(node);
						kenMowei = i;
						startLian = true;
					}else if(startLian) {
						rightDuan = i;
						break;
					}
					if(!startLian) {
						leftDuan = i;
					}
					if(lianxuKen.size() == xiuz.size()) {
						break;
					}
					i = ++i % xiuz.size();
					if(++total > xiuz.size()) {
						break;
					}
				}
				if(kenMowei == -1) {//无坑,不修正
					break;
				}//寻找最低边界
				int[] first = xiuz.get(0);
				//------------------add
				//左边走, 找全坑
				if(lianxuKen.size() < xiuz.size() && shendu[first[0]][first[1]] > 0) {//需要调整左边left起点位置
					boolean startLeft = false;
					for(int j = 0;;) {
						j = j == 0 ? xiuz.size() - 1 : --j;//下一个位置
						int[] node = xiuz.get(j);
						if(shendu[node[0]][node[1]] > 0) {//是坑
							lianxuKen.add(0, node);
							startLeft = true;
						}else if(startLeft) {//不是坑
							leftDuan = j;
							break;
						}
						if(lianxuKen.size() == xiuz.size()) {
							break;
						}
					}
				}
				//------------------add
				if(lianxuKen.size() == xiuz.size()) {//全是坑
					//寻找最低高度
					int gao = Integer.MAX_VALUE;
					for(int[] no : xiuz) {
						int ca = arr[no[0]][no[1]] + shendu[no[0]][no[1]];
						if(ca < gao) {
							gao = ca;
						}
					}
					//开始修正
					for(int[] no : xiuz) {//TODO
						int ca = gao - arr[no[0]][no[1]];
						shendu[no[0]][no[1]] = ca < 0 ? 0 : ca;
					}
					break;
				}else {//部分是坑，有端点
					int[] youduan = xiuz.get(leftDuan);
					int[] zuoduan = xiuz.get(rightDuan);
					int gao = arr[zuoduan[0]][zuoduan[1]];
					int yougao = arr[youduan[0]][youduan[1]];
					gao = gao > yougao ? yougao : gao;
					for(int[] no : lianxuKen) {
						int ca = arr[no[0]][no[1]] + shendu[no[0]][no[1]];
						if(ca < gao) {
							gao = ca;
						}
					}
					//开始修正
					for(int[] no : lianxuKen) {
						int ca = gao - arr[no[0]][no[1]];
						shendu[no[0]][no[1]] = ca < 0 ? 0 : ca;
					}
					//只移除连续坑
					xiuz.removeAll(lianxuKen);
				}
				lianxuKen.clear();
			}
			xiuz.clear();
			lianxuKen.clear();
			currQuan++;
		}
		//深度表打印---直接计算总和
		int zonghe = 0;
		for(int[] s : shendu) {
			System.out.println();
			for(int x : s) {
				System.out.print(x + ",");
				zonghe += x;
			}
		}
		System.out.println();
		for(int[] s : arr) {
			System.out.println();
			for(int x : s) {
				System.out.print(x + ",");
			}
		}
		System.out.println();
		return zonghe;
	}
}
