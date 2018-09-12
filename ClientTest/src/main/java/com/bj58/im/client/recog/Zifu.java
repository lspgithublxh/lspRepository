package com.bj58.im.client.recog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.util.ImageUtils;


/**
 * 实时拍摄，实时识别
 *下一步，才是推荐列表：消息
 *算法库---也要有接口库：否则大量重复功夫
 *不是好算法，没有理论的蛮干
 *--最简单--只关注平移、放缩，字体和旋转确定
 *
 *---第二：moveto  lineto  arcto  这几种 -- 并且记录方向:::来记录形状；；类似向量法
 *---第三：图形统一描述语言：
 *		a: 起-止-方式-率 为一个item(率可以描述形状：斜率、曲率),连接点；；；共同构成一个电路图的方式。。
 *			  q1:识别端点-方式， q2:由端点构造电路图， q3:比较电路图
 *
 *
 * @ClassName:Zifu
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月6日
 * @Version V1.0
 * @Package com.bj58.im.client.recog
 */
public class Zifu {

	public static void main(String[] args) {
		test();
	}

	/**
	 * 最小单位：111 横向隔0 纵向隔3
	 * 理论不足
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月10日
	 * @Package com.bj58.im.client.recog
	 * @return void
	 */
	private static void test() {
		generateImg();
	}

	private static void generateImg() {
		FileOutputStream o;
		BufferedImage image = GetImage.getImage(180, 120);
		
		byte[] r = ImageUtils.imageToBytes(image);
		
		byte[][] jietu = Jietu.jietuImg(r);
		
		writeToFile(jietu, "D:\\cache3\\d.txt");
		writeToFile(Jietu.yuanImg(r), "D:\\cache3\\old.txt");
		
		BufferedImage image2 = GetImage.getImage(120, 120);
		
		byte[] r2 = ImageUtils.imageToBytes(image2);
		
		byte[][] jietu2 = Jietu.jietuImg(r2);
		writeToFile(jietu2, "D:\\cache3\\e.txt");
		
		Compare.compare(jietu, jietu2);
	}


	private static void writeToFile(byte[][] jietu, String file) {
		FileOutputStream o;
		System.out.println("start write to file");
		try {
			o = new FileOutputStream(new File(file));
			writeToFile(o, jietu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	

	private static void writeToFile(FileOutputStream o, byte[][] r) {
		StringBuffer b = new StringBuffer();
		for(int i = 0; i < r.length; i++) {
			byte[] c = r[i];
			for(byte cc : c) {
				b.append(cc + ",");
			}
			
			b.append("\r\n");
//			for(byte bb : c) {
//				
//			}
		}
		try {
			o.write(b.toString().getBytes());
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int writeToFile(FileOutputStream o, byte[] r) {
		int count = 0;
		StringBuffer b = new StringBuffer();
		for(int i = 0; i < r.length; i++) {
//			System.out.print(r[i] + ",");
			b.append(r[i] + "");
			if(count++ % 200 == 0) {
				System.out.println();
				b.append("\r\n");
			}
		}
		try {
			o.write(b.toString().getBytes());
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
}
