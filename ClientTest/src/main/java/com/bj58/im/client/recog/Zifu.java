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

	private static void test() {
		generateImg();
	}

	private static void generateImg() {
		FileOutputStream o;
		BufferedImage image = GetImage.getImage(180, 120);
		
		byte[] r = ImageUtils.imageToBytes(image);
		
		byte[][] jietu = Jietu.jietuImg(r);
		
		writeToFile(jietu, "D:\\cache3\\d.txt");
		
		
		BufferedImage image2 = GetImage.getImage(120, 120);
		
		byte[] r2 = ImageUtils.imageToBytes(image2);
		
		byte[][] jietu2 = Jietu.jietuImg(r2);
		writeToFile(jietu2, "D:\\cache3\\e.txt");
		
		Compare.compare(jietu, jietu2);
	}


	private static void writeToFile(byte[][] jietu, String file) {
		FileOutputStream o;
		System.out.println("start");
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
