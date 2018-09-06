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

public class GetImage {

	public static BufferedImage getImage(int x, int y) {
		int w = 200;//200
		int h = 233;//233
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);//BufferedImage.TYPE_INT_ARGB
		Graphics2D gd = image.createGraphics();
		gd.fill(new Rectangle(w, h));
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		Font f = new Font(Font.SERIF, Font.PLAIN, 30);
		gd.setFont(f);
		gd.drawString("1", x, y);
		File fi = new File("D:\\cache3\\b.txt");
		FileOutputStream o = null;
		try {
			ImageIO.write(image, "png", new File("D:\\cache3\\a.png"));
			o = new FileOutputStream(fi);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
