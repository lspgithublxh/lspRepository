package com.bj58.im.client.recog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
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
		int w = 200;//200
		int h = 233;//233
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);//BufferedImage.TYPE_INT_ARGB
		Graphics2D gd = image.createGraphics();
		gd.fill(new Rectangle(w, h));
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		Font f = new Font(Font.SERIF, Font.PLAIN, 30);
		gd.setFont(f);
		gd.drawString("1", 120, 120);
		File fi = new File("D:\\cache3\\b.txt");
		FileOutputStream o = null;
		try {
			ImageIO.write(image, "png", new File("D:\\cache3\\a.png"));
			o = new FileOutputStream(fi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] r = ImageUtils.imageToBytes(image);
		int count = 0;
		StringBuffer b = new StringBuffer();
		for(int i = 0; i < r.length; i++) {
			System.out.print(r[i] + ",");
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
//		int[] rs = new int[image.getWidth() * image.getHeight()];
//		rs = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getHeight());//rs不用null反而会出问题..效果一样
//		for(int i = 0; i < rs.length; i++) {
//			System.out.print(rs[i] + ",");
//			b.append(rs[i] + "");
//			if(count++ % 200 == 0) {
//				System.out.println();
//				b.append("\r\n");
//			}
//		}
//		try {
//			o.write(b.toString().getBytes());
//			o.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
