package com.bj58.im.client.ClientTest.Image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 图像移动、缩小、合并
 * @ClassName:CircleHandle
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月9日
 * @Version V1.0
 * @Package com.bj58.fang.imgGenerate
 */
public class CircleHandle {

	public static void main(String[] args) throws IOException {
		method5();
		
	}

	/**
	 * 
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月10日
	 * @Package com.bj58.fang.imgGenerate
	 * @return void
	 * @throws IOException 
	 */
	private static void method5() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\head.jpg"));  //图片太大
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx.png"));//这里用jpg会导致黑色底色和不透明
		big = scaled(big, 2);
		BufferedImage image = new BufferedImage(200, 233, BufferedImage.TYPE_INT_ARGB);
		
		int[] rs = new int[big.getWidth() * big.getHeight()];
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());//rs不用null反而会出问题..效果一样
		image.setRGB(40, 10, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		
		Graphics2D gd = image.createGraphics();
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		gd.setFont(new Font(null, Font.ROMAN_BASELINE, 10));
		String[] fs = new String[] {"二手房经纪人", "服务区域：房山长阳", "主营小区：长阳国际城" ,"所属公司：金色时光"};
		fs = formatString(fs);
		gd.drawString(fs[0], 30, 120);//w,h
		gd.drawString(fs[1], 30, 135);
		gd.drawString(fs[2], 30, 150);
		gd.drawString(fs[3], 30, 165);
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\new.jpg"));//格式必须png
		
	}

	private static String[] formatString(String[] sou) {
		int len = 0;
		String[] rs = new String[sou.length];
		for(String s : sou) {
			if(s.length() > len) {
				len = s.length();
			}
		}
		int ind = 0;
		for(String s : sou) {
			String s2 = s;
			for(int i = len - s.length(); i > 0;) {
				if(ind == 0) {
					s2 = "   " + s2 + "   ";//+ " "
				}else {
					s2 = "  " + s2 + "  ";//+ " "
				}
				
				i -= 2;
			}
			rs[ind++] = s2;
		}
		return rs;
	}
	
	private static void method4() throws IOException {
		BufferedImage bi1 = ImageIO.read(new File("D:\\a.png"));
		BufferedImage bi2 = ImageIO.read(new File("D:\\a.png"));
		BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight() + bi1.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bi2.createGraphics();
//		g2.fill(new Rectangle(image.getWidth(), image.getWidth()));
//		g2.drawImage(image, 0, 0, null);//TODO bi1,向右移动shape轮廓---在bi1上圈出的内容bi1.getWidth(), bi1.getHeight()
		g2.drawImage(bi1, 100,20, bi1.getWidth(), bi1.getHeight(), null);
	    g2.dispose();
	    
	    try {
            ImageIO.write(bi2, "PNG", new File("d:/21.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	private static BufferedImage scaled(BufferedImage image, int scale) {
		BufferedImage buf = new BufferedImage(image.getWidth() / scale, image.getWidth() / scale, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = buf.createGraphics();
//		g2.setColor(Color.WHITE);
//		g2.setBackground(Color.WHITE);
		g2.fill(new Rectangle(image.getWidth() / scale, image.getWidth() / scale));
		buf.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / scale, image.getWidth() / scale, Image.SCALE_SMOOTH), 0, 0, null);
        return buf;
	}
	
	private static BufferedImage round(BufferedImage image, int h, int w) {
		BufferedImage buf = new BufferedImage(w, w,
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Double(0, 0, w, w, w, w));
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);//TODO bi1,向右移动shape轮廓---在bi1上圈出的内容
	    g2.dispose();
	    
	    return buf;
	}
	
	/**
	 * 圆角和缩小
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月9日
	 * @Package com.bj58.fang.imgGenerate
	 * @return void
	 */
	private static void method3() throws IOException {
		BufferedImage bi1 = ImageIO.read(new File("D:\\head.jpg"));
		//1.图像重构,这里的宽高设置没意义，因为后面会代替,可以1,1  作用和42getDeviceConfiguration...一样的
		BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getWidth(),
                BufferedImage.TYPE_INT_ARGB);
		//2.通过轮廓矩形构造一个椭圆,,这里x,y也会移动矩形框
//		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
//                .getWidth());
//		
		 Graphics2D g2 = image.createGraphics();
		 //2.1最终生成图的宽和高
//		 image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getHeight(), bi1.getHeight(), Transparency.TRANSLUCENT);
//		 g2 = image.createGraphics();
//		 g2.setBackground(Color.WHITE);
		 //2.2这里的填充也没有必要，因为高宽也不影响，可以1,1...否则会是黑色的底色
		 g2.setComposite(AlphaComposite.Src);
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.setColor(Color.WHITE);
		 g2.fill(new RoundRectangle2D.Double(0, 0, bi1.getWidth(), bi1.getWidth(), bi1.getWidth(), bi1.getWidth()));
		 g2.setComposite(AlphaComposite.SrcAtop);
		 //3.开始画图，从bi1中取下当前可用的部分;从x,y处开始画...所以与圈图有关
		 g2.drawImage(bi1, 0, 0, null);//TODO bi1,向右移动shape轮廓---在bi1上圈出的内容
	     g2.dispose();
	     try {
	            ImageIO.write(image, "PNG", new File("d:/19.png"));
	            //4.图像压缩
	            BufferedImage ima = new BufferedImage(image.getWidth() / 10, image.getWidth() / 10, BufferedImage.TYPE_INT_RGB);
	            ima.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / 10, image.getWidth() / 10, Image.SCALE_SMOOTH), 0, 0, null);
	            ImageIO.write(ima, "PNG", new File("d:/16.png"));
         } catch (IOException e) {
            e.printStackTrace();
         }
	}

	
	
	private static void method2() throws IOException {
		BufferedImage bi1 = ImageIO.read(new File("D:\\a.png"));
		//1.图像重构,这里的宽高设置没意义，因为后面会代替,可以1,1  作用和42getDeviceConfiguration...一样的
		BufferedImage image = new BufferedImage(bi1.getHeight(), bi1.getHeight(),
                BufferedImage.TYPE_INT_RGB);
		//2.通过轮廓矩形构造一个椭圆,,这里x,y也会移动矩形框
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                .getWidth());
		
		 Graphics2D g2 = image.createGraphics();
		 //2.1最终生成图的宽和高
//		 image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getHeight(), bi1.getHeight(), Transparency.TRANSLUCENT);
		 g2 = image.createGraphics();
		 g2.setBackground(Color.WHITE);
		 //2.2这里的填充也没有必要，因为高宽也不影响，可以1,1...否则会是黑色的底色
		 g2.fill(new Rectangle(image.getWidth(), image.getWidth()));
//		 g2.fill(new RoundRectangle2D.Double(0, 0, 100, 100, 100, 100));
		 g2.setClip(shape);
		 //3.开始画图，从bi1中取下当前可用的部分;从x,y处开始画...所以与圈图有关
		 g2.drawImage(bi1, 0, 0, null);//TODO bi1,向右移动shape轮廓---在bi1上圈出的内容
	     g2.dispose();
	     try {
	            ImageIO.write(image, "PNG", new File("d:/8.png"));
	            //4.图像压缩
	            BufferedImage ima = new BufferedImage(image.getWidth() / 10, image.getWidth() / 10, BufferedImage.TYPE_INT_RGB);
	            ima.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / 10, image.getWidth() / 10, Image.SCALE_SMOOTH), 0, 0, null);
	            ImageIO.write(ima, "PNG", new File("d:/6.png"));
         } catch (IOException e) {
            e.printStackTrace();
         }
	}

	private static void method1() throws IOException {
		BufferedImage bi1 = ImageIO.read(new File("D:\\a.png"));
		 
        // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
        BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                BufferedImage.TYPE_INT_RGB);
         
        
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1
                .getHeight());
        
        Graphics2D g2 = image.createGraphics();
        image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
        g2 = image.createGraphics();
        g2.setBackground(Color.RED);
        g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        g2.dispose();
 
        try {
            ImageIO.write(image, "PNG", new File("d:/2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
