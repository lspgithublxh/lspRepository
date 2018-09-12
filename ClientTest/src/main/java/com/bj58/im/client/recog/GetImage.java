package com.bj58.im.client.recog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.util.ImageUtils;

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
		gd.drawString("A", x, y);
		try {
			ImageIO.write(image, "png", new File("D:\\cache3\\a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void main(String[] args) {
		int x = 180;
		int y = 100;
		
		int w = 200;//200
		int h = 233;//233
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);//BufferedImage.TYPE_INT_ARGB
		Graphics2D gd = image.createGraphics();
		gd.fill(new Rectangle(w, h));
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		Font f = new Font(Font.SERIF, Font.PLAIN, 30);//10，20，30, 40,...才有区分，之间没有区分  <10是不完整的，
		gd.setFont(f);
		gd.drawString("1", x, y);
		
//		image = scaled(image, 3);
		
		byte[] r = ImageUtils.imageToBytes(image);
		WriteToFile.writeToFile("D:\\cache3\\aa.txt", r);
		
		try {
			ImageIO.write(image, "png", new File("D:\\cache3\\a.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage scaled(BufferedImage image, int scale) {
		BufferedImage buf = new BufferedImage(image.getWidth() / scale, image.getWidth() / scale, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = buf.createGraphics();
		g2.fill(new Rectangle(image.getWidth() / scale, image.getWidth() / scale));
		buf.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / scale, image.getWidth() / scale, Image.SCALE_SMOOTH), 0, 0, null);
        return buf;
	}
}
