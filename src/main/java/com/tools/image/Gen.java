package com.tools.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gen {

	public static void main(String[] args){
		try {
//			test1();
//			merge();
//			font();
			imageAndFont();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void imageAndFont() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\test\\img\\cai.jpg"));  
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
//	
		int[] rs = new int[big.getWidth() * big.getHeight()];
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		image.setRGB(40, 100, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		Graphics2D gd = image.createGraphics();
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		gd.setFont(new Font(null, Font.BOLD, 60));
		gd.drawString("hello world", 30, 60);
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\test\\img\\rs.png"));
	}

	/**
	 * 
	 *@author lishaoping
	 *ToolsTest
	 *2018年5月9日
	 * @throws IOException
	 */
	private static void font() throws IOException {
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd = image.createGraphics();
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		gd.setFont(new Font(null, Font.BOLD, 60));
		gd.drawString("hello world", 30, 60);
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\test\\img\\n.png"));
	}


	/**
	 * 凭空生成一张图，或者合并
	 *@author lishaoping
	 *ToolsTest
	 *2018年5月9日
	 * @throws IOException 
	 */
	private static void merge() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\test\\img\\cai.jpg"));  
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D gd = image.createGraphics();
		int[] rs = new int[big.getWidth() * big.getHeight()];
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		image.setRGB(40, 100, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		ImageIO.write(image, "png", new File("D:\\test\\img\\cq.png"));
	}

	
	


	/**
	 * 覆盖一层
	 *@author lishaoping
	 *ToolsTest
	 *2018年5月9日
	 * @throws IOException
	 */
	private static void test1() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\test\\img\\x.png"));  
        BufferedImage small = ImageIO.read(new File("D:\\test\\img\\x.png"));  
        Graphics2D g = big.createGraphics();  
        int x = (big.getWidth()) / 2; // - small.getWidth()
        int y = (big.getHeight() ) / 2;  //- small.getHeight()
        //2.会控制位置
        g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);  
        g.dispose();  
        ImageIO.write(big, "png", new File("D:\\test\\img\\e.png"));
	}
}
