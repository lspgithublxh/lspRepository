package com.bj58.im.client.recog;

/**
 * 
 * @ClassName:Jietu
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月6日
 * @Version V1.0
 * @Package com.bj58.im.client.recog
 */
public class Jietu {
	
	public static byte[][] jietuImg(byte[] r) {
		int row = r.length / 200 + (r.length % 200 == 0 ? 0 : 1);
		byte[][] aa = new byte[row][200];
		byte[] by = new byte[200];
		int line = 0;
		int count = 0;
		int minX = 10000;
		int maxX = 0;
		int minY = 10000;
		int maxY = 0;
		for(int i = 0; i < r.length; i++) {
			by[count] = r[i] != -1 ? (byte)1 : (byte)0;
			if(r[i] != -1) {
				minX = line < minX ? line : minX;
				maxX = line >= maxX ? line : maxX;
				
				minY = count < minY ? count : minY;
				maxY = count >= maxY ? count : maxY;
			}
			if(++count == 200) {
				aa[line++] = by;
				count = 0;
				by = new byte[200];
			}
		}
		byte[][] jietu = new byte[maxX - minX + 1][maxY - minY + 1];
		for(int i = minX; i <= maxX; i++) {
			byte[] c = aa[i];
			for(int j = minY; j <= maxY; j++) {
				jietu[i - minX][j - minY] = c[j];
			}
		}
		return jietu;
	}
	
	public static byte[][] yuanImg(byte[] r) {
		int row = r.length / 200 + (r.length % 200 == 0 ? 0 : 1);
		byte[][] aa = new byte[row][200];
		byte[] by = new byte[200];
		int line = 0;
		int count = 0;
		for(int i = 0; i < r.length; i++) {
			by[count] = r[i] != -1 ? (byte)1 : (byte)0;
			if(++count == 200) {
				aa[line++] = by;
				count = 0;
				by = new byte[200];
			}
		}
		return aa;
	}
}
	
