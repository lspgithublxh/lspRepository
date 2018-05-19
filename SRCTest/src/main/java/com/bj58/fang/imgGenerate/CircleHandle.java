package com.bj58.fang.imgGenerate;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.javafx.tk.FontLoader;


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
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		method9();
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println(Color.WHITE.getRGB());
		System.out.println(Color.WHITE.getTransparency());//透明度1， 蓝色也是
		System.out.println(Color.TRANSLUCENT);
		System.out.println(System.getProperty("user.dir"));//CircleHandle.class.getResource("")
//		String path = CircleHandle.class.getResource("").toString();
//		System.out.println(path.substring(0, path.indexOf("WEB-INF/resources/compu.jpg")));
	}

	static FileOutputStream out = null;
	
	private static void printIntArray(int[] a) {
		int i = 0;
		String s = "";
		for(int x : a) {
			s += Integer.toHexString(x) + ",";
		}
		try {
			out.write(s.getBytes());
			out.write("\r\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void outputFile(BufferedImage big) {
		int[] a = new int[big.getWidth() * big.getHeight()];
		a = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());
		if(out == null) {
			try {
				out = new FileOutputStream("D:\\ab.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		int i = 0;
		String s = "";
		for(int x : a) {//TODO 按行列来打，更好
			s += Integer.toHexString(x) + ",";
		}
		try {
			out.write(s.getBytes());
			out.write("\r\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void touming(BufferedImage bufferedImage) {
		for (int y = bufferedImage.getMinY(); y < bufferedImage.getHeight(); y++) {
			             // 内层遍历是X轴的像素
			             for (int x = bufferedImage.getMinX(); x < bufferedImage.getWidth(); x++) {
			                 int rgb = bufferedImage.getRGB(x, y);
			                 if(rgb == 0x00000000) {
			                	 rgb = 0xff0000ff;
			                 }
			                 // 对当前颜色判断是否在指定区间内
//			                 if (colorInRange(rgb)){
//			                     alpha = 0;
//			                }else{
//			                     // 设置为不透明
//			                     alpha = 255;
//			                 }
//			                 // #AARRGGBB 最前两位为透明度
//			                 rgb = (alpha << 24) | (rgb & 0x00ffffff);
			                 bufferedImage.setRGB(x, y, rgb);
			             }
			         }
	}
	
	public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;
 
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };
 
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
	
	private static void method7() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\head.jpg"));  //图片太大
//		outputFile(big);
//		big = round(big, big.getHeight(), big.getWidth());
//		ImageIO.write(big, "png", new File("D:\\xxx.png"));//这里用jpg会导致黑色底色和不透明
//		big = scaled(big, 3);
//		ImageIO.write(big, "png", new File("D:\\xxx2.png"));//这里用jpg会导致黑色底色和不透明
////		outputFile(big);
//		//透明元素判断：int alpha = (pixels[i] >> 24) & 0xff;alpha值为0则是透明元素
//		big = round(big, big.getHeight(), big.getWidth());
//		ImageIO.write(big, "png", new File("D:\\xxx3.png"));//再次圆角
//		outputFile(big);
		
		//先
		BufferedImage image = new BufferedImage(200, 233, BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = image.createGraphics();//上移
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		gd.setColor(Color.BLUE);	
		gd.fill(new Rectangle2D.Double(0, 0, 200, 50 + 10 * 2));
		
//		gd.setBackground(Color.green);  
		//后.矩形就不行，，不管是否透明，白色，
//		touming(big);
//		int[] rs = new int[big.getWidth() * big.getHeight()];
//		System.out.println(big.getWidth() +", "+ big.getHeight());
//		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());//rs不用null反而会出问题..效果一样
//
//		//		image.setRGB((200 / 2 - big.getWidth() / 2), 10, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
//		image.setRGB(20, 10, big.getWidth(), big.getWidth(), rs, 0, big.getWidth());

//		outputFile(big);
//		printIntArray(rs);
//		outputFile(image);
		
		ImageIO.write(image, "jpg", new File("D:\\xxx47.jpg"));//前面不fill，那么这里会是透明的，如果fill了，那么本来透明的地方会变成白色
		//再次添加背景
		gd.setColor(Color.WHITE);
		gd.fill(new Rectangle2D.Double(0, 50 + 10 * 2, 200, 233 - (50 + 10 * 2)));//133
		//aplha代表透明度，
		gd.setColor(Color.WHITE);
		drawTextS2(new String[] {"王厚勇", "房山长阳", "暂无公司信息"}, gd, 
				20 + 50 + 20, (10 + 50) / 2, 20, 50 + 10 * 2 + 20);
		//new Rectangle(200, 233) new Rectangle2D.Double(0, 0, 200, 233)
//		BufferedImage compu = ImageIO.read(new File("D:\\compu.jpg"));
//		compu = scaled(compu, 10);
//		int[] rs = compu.getRGB(0, 0, compu.getWidth(), compu.getHeight(), null, 0, compu.getWidth());//rs不用null反而会出问题..效果一样
//		image.setRGB(150, big.getWidth() + 10 * 2 + 20 + 20 + 20, compu.getWidth(), compu.getHeight(), rs, 0, compu.getWidth());
		
		
		gd.setColor(Color.WHITE);
//		gd.setBackground(Color.WHITE);
		//
//		
		String[] fs = new String[] {"二手房经纪人", "服务区域：房山长阳", "主营小区：长阳国际城" ,"所属公司：金色时光"};
//		fs = formatString(fs);
//		gd.drawString(fs[0], 30, 120);//w,h
//		gd.drawString(fs[1], 30, 135);
//		gd.drawString(fs[2], 30, 150);
//		gd.drawString(fs[3], 30, 165);
//		drawTextS(fs, gd, 200);
		Ellipse2D.Double shape = new Ellipse2D.Double(20,10, 50, 50);//62, 53
//		gd.setComposite(AlphaComposite.Clear);//将区域清除
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1));//直接将源数据 放到 目标 位置
		gd.setClip(shape);
		gd.drawImage(big, 20,10, 50, 50, null);//确定裁剪的目标....
		//clip之后不再受到控制
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\news7.jpg"));//格式必须png
		
	}
	
	private static void method9() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\head.jpg"));  //图片太大  gui.jpg a.png
//		Graphics2D gdb = big.createGraphics();
		
		big = round(big, big.getWidth(), big.getHeight());
		ImageIO.write(big, "png", new File("D:\\xxx31.png"));
		big = scaled3(big, 260);// 4倍压缩
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx3.png"));
		big = round3(big, big.getHeight(), big.getWidth());//
		ImageIO.write(big, "png", new File("D:\\xxx3x.png"));
			
		int w = 1200;//200
		int h = 960;//233
		int splitline = 580;
		int rest = 500;
		Graphics2D gd = big.createGraphics();
		gd.setColor(Color.WHITE);
		
		drawTextS3(new String[] {"王厚勇", "房山长阳", "北京世纪房源房地产经纪有限公司"}, gd, w,
				w / 2 - big.getWidth() / 2,   440, 40, splitline + 20);//w / 2 - big.getWidth() / 2
		
		ImageIO.write(big, "png", new File("D:\\news4.jpg"));//格式必须png
	}

	
	private static void method8() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\head.jpg"));  //图片太大
//		Graphics2D gdb = big.createGraphics();
		
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx31.png"));
		big = scaled(big, 3);
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx3.png"));
		big = round2(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx3x.png"));
		int w = 200;//200
		int h = 150;//233
		
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = image.createGraphics();//上移
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		gd.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		gd.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		gd.setComposite(AlphaComposite.Src);
                
		
		gd.setColor(new Color(0x43, 0x6E, 0xEE));//Color.BLUE
		int splitline = big.getHeight() + 10 * 2 + 10;
		gd.fill(new Rectangle2D.Double(0, 0, w, splitline));
		
//		touming(big);
		int[] rs = new int[big.getWidth() * big.getHeight()];
		System.out.println(big.getWidth() +", "+ big.getHeight());
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());//rs不用null反而会出问题..效果一样
		image.setRGB(w / 2 - big.getWidth() / 2, 5, big.getWidth(), big.getWidth(), rs, 0, big.getWidth());
		
		//再次添加背景
		gd.setColor(Color.WHITE);
		gd.fill(new Rectangle2D.Double(0, splitline, w, h - splitline));//133
		//aplha代表透明度，
		gd.setColor(Color.WHITE);
		drawTextS3(new String[] {"王厚勇", "房山长阳", "北京世纪房源房地产经纪有限公司"}, gd, w,
				w / 2 - big.getWidth() / 2, big.getHeight() + 24, 20, splitline + 20);

		ImageIO.write(image, "png", new File("D:\\news3.jpg"));//格式必须png
		
	}
	
	private static void method6() throws IOException {
		BufferedImage big = ImageIO.read(new File("D:\\head.jpg"));  //图片太大
		outputFile(big);
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx.png"));//这里用jpg会导致黑色底色和不透明
		big = scaled(big, 3);
		ImageIO.write(big, "png", new File("D:\\xxx2.png"));//这里用jpg会导致黑色底色和不透明
//		outputFile(big);
		//透明元素判断：int alpha = (pixels[i] >> 24) & 0xff;alpha值为0则是透明元素
		big = round(big, big.getHeight(), big.getWidth());
		ImageIO.write(big, "png", new File("D:\\xxx3.png"));//再次圆角
//		outputFile(big);
		
		//先
		BufferedImage image = new BufferedImage(200, 233, BufferedImage.TYPE_INT_RGB);
		Graphics2D gd = image.createGraphics();//上移
		gd.setColor(Color.BLUE);
		gd.fill(new Rectangle2D.Double(0, 0, 200, big.getWidth() + 10 * 2));
		//后.矩形就不行，，不管是否透明，白色，
		touming(big);
		int[] rs = new int[big.getWidth() * big.getHeight()];
		System.out.println(big.getWidth() +", "+ big.getHeight());
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());//rs不用null反而会出问题..效果一样

		//		image.setRGB((200 / 2 - big.getWidth() / 2), 10, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		image.setRGB(20, 10, big.getWidth(), big.getWidth(), rs, 0, big.getWidth());

//		outputFile(big);
//		printIntArray(rs);
//		outputFile(image);
		
		ImageIO.write(image, "jpg", new File("D:\\xxx4.jpg"));//前面不fill，那么这里会是透明的，如果fill了，那么本来透明的地方会变成白色
		//再次添加背景
		gd.setColor(Color.WHITE);
		gd.fill(new Rectangle2D.Double(0, big.getWidth() + 10 * 2, 200, 233 - (big.getWidth() + 10 * 2)));//133
		//aplha代表透明度，
		gd.setColor(Color.WHITE);
		drawTextS2(new String[] {"王厚勇", "房山长阳", "暂无公司信息"}, gd, 
				20 + big.getWidth() + 20, (10 + big.getHeight()) / 2, 20, big.getWidth() + 10 * 2 + 20);
		//new Rectangle(200, 233) new Rectangle2D.Double(0, 0, 200, 233)
		BufferedImage compu = ImageIO.read(new File("D:\\compu.jpg"));
		compu = scaled(compu, 10);
		rs = compu.getRGB(0, 0, compu.getWidth(), compu.getHeight(), null, 0, compu.getWidth());//rs不用null反而会出问题..效果一样
		image.setRGB(150, big.getWidth() + 10 * 2 + 20 + 20 + 20, compu.getWidth(), compu.getHeight(), rs, 0, compu.getWidth());
		
		
		gd.setColor(Color.BLACK);
//		gd.setBackground(Color.WHITE);
		//
//		
		String[] fs = new String[] {"二手房经纪人", "服务区域：房山长阳", "主营小区：长阳国际城" ,"所属公司：金色时光"};
//		fs = formatString(fs);
//		gd.drawString(fs[0], 30, 120);//w,h
//		gd.drawString(fs[1], 30, 135);
//		gd.drawString(fs[2], 30, 150);
//		gd.drawString(fs[3], 30, 165);
//		drawTextS(fs, gd, 200);
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\news.jpg"));//格式必须png
		
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
		
		Graphics2D gd = image.createGraphics();//上移
		gd.fill(new Rectangle(200, 233));
		
		int[] rs = new int[big.getWidth() * big.getHeight()];
		rs = big.getRGB(0, 0, big.getWidth(), big.getHeight(), null, 0, big.getHeight());//rs不用null反而会出问题..效果一样
		image.setRGB((200 / 2 - big.getWidth() / 2), 10, big.getWidth(), big.getHeight(), rs, 0, big.getHeight());
		
		
		gd.setColor(Color.BLACK);
		gd.setBackground(Color.WHITE);
		//
//		
		String[] fs = new String[] {"二手房经纪人", "服务区域：房山长阳", "主营小区：长阳国际城" ,"所属公司：金色时光"};
//		fs = formatString(fs);
//		gd.drawString(fs[0], 30, 120);//w,h
//		gd.drawString(fs[1], 30, 135);
//		gd.drawString(fs[2], 30, 150);
//		gd.drawString(fs[3], 30, 165);
		drawTextS(fs, gd, 200);
		gd.dispose();
		ImageIO.write(image, "png", new File("D:\\news.jpg"));//格式必须png
		
	}

	private static void drawTextS(String[] sou , Graphics2D gd, int w) {
		int h  = 120;
//		gd.setFont(new Font(null, Font.ROMAN_BASELINE, 13));
		int i = 0;
		for(String s : sou) {
			Font font = null;
			if(i++ == 0) {
				font = new Font("宋体", Font.PLAIN, 13);
			}else {
				font = new Font("微软雅黑", Font.PLAIN, 13);//微软雅黑 微软雅黑
			}
			gd.setFont(font);
			gd.drawString(s, drawText(s, font, w), h);
			h += 20;
		}
	}
	
	private static void drawTextS4(String[] strs, Graphics2D gd,int width, int w, int h, int startw, int starth) {
		
		Font font = null;
		font = new Font("宋体", Font.BOLD, 15);//
//		font = LoadFont.loadFont("D:\\PingFang.ttc", 15);//浪漫雅圆.ttf
		
		gd.setFont(font);
		gd.drawString(strs[0], getPosition(strs[0], font, width), h);
		h += 20;
		
//		font = new Font("宋体", Font.PLAIN, 15);//宋体
//		gd.setFont(font);
//		gd.drawString("经纪人", w, h);
//		
		gd.setColor(Color.GRAY);
		font = new Font("宋体", Font.PLAIN, 13);//宋体
//		font = LoadFont.loadFont("D:\\PingFang.ttc", 13);//浪漫雅圆.ttf
		
		gd.setFont(font);
		gd.drawString("服务区域  ", startw, starth);
		gd.setColor(Color.BLACK);
		gd.drawString(strs[1], getPositionRight("服务区域  ", font, startw), starth);
		
		starth += 20;
		gd.setColor(Color.GRAY);
//		font = new Font("宋体", Font.PLAIN, 13);//宋体
		int words = 8;
		int words_balank = 20;
		String[] adrr = new String[strs[2].length() % words == 0 ?  strs[2].length() / words : (strs[2].length() / words + 1)];
		for(int i = 0; i < adrr.length; i++) {
			adrr[i] = strs[2].substring(i * words, (i + 1) * words > strs[2].length() ? strs[2].length() : (i + 1) * words);
		}
		gd.setFont(font);
		gd.drawString("所属公司  ", startw, starth);
		gd.setColor(Color.BLACK);
		for(String name : adrr) {
			gd.drawString(name, getPositionRight("所属公司  ", font, startw), starth);
			starth += words_balank;
		}
	}
	
	private static void drawTextS3(String[] strs, Graphics2D gd,int width, int w, int h, int startw, int starth) {
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();
		for (String s : fontFamilies) {
		    System.out.println(s);
		}
		String ziti = "Monospaced";
		
		Font font = null;
//		font = new Font(ziti, Font.BOLD, 15);//微软vista正黑体
		font = LoadFont.loadFont("D:\\微软vista雅黑Bold.ttf", 70);//浪漫雅圆.ttf 阿丽达黑体.ttf
		
		gd.setFont(font);
		gd.drawString(strs[0], getPosition(strs[0], font, width), h);
		h += 80;
		
//		font = new Font("宋体", Font.PLAIN, 15);//宋体
//		gd.setFont(font);
//		gd.drawString("经纪人", w, h);
//		
		gd.setColor(Color.GRAY);
//		font = new Font(ziti, Font.PLAIN, 13);//宋体
		font = LoadFont.loadFont("D:\\微软vista雅黑.ttf", 68);//浪漫雅圆.ttf  华文细黑.ttf弯曲的  PingFang.ttc
		
		gd.setFont(font);
		gd.drawString("服务区域   ", startw, starth);
		gd.setColor(new Color(0x33, 0x33, 0x33));
		gd.drawString(strs[1], getPositionRight("服务区域    ", font, startw), starth);
		
		starth += 80;
		gd.setColor(Color.GRAY);
//		font = new Font("宋体", Font.PLAIN, 13);//宋体
		int words = 8;
		int words_balank = 80;
		String[] adrr = new String[strs[2].length() % words == 0 ?  strs[2].length() / words : (strs[2].length() / words + 1)];
		for(int i = 0; i < adrr.length; i++) {
			adrr[i] = strs[2].substring(i * words, (i + 1) * words > strs[2].length() ? strs[2].length() : (i + 1) * words);
		}
		gd.setFont(font);
		gd.drawString("所属公司    ", startw, starth);
		gd.setColor(new Color(0x33, 0x33, 0x33));
		for(String name : adrr) {
			gd.drawString(name, getPositionRight("所属公司    ", font, startw), starth);
			starth += words_balank;
		}
		
		
//		gd.setColor(Color.GRAY);
//		font = new Font("宋体", Font.PLAIN, 16);//宋体
//		gd.setFont(font);
//		gd.drawString("所属公司  ", startw, starth + 20);
//		gd.setColor(Color.BLACK);
//		gd.drawString(strs[2], getPositionRight("所属公司  ", font, startw), starth + 20);
		
	}
	
	private static void drawTextS2(String[] strs, Graphics2D gd, int w, int h, int startw, int starth) {
				
		Font font = null;
		font = new Font("宋体", Font.BOLD, 16);
		
		gd.setFont(font);
		gd.drawString(strs[0], w, h);
		h += 20;
		
		font = new Font("宋体", Font.PLAIN, 15);//宋体
		gd.setFont(font);
		gd.drawString("经纪人", w, h);
		
		gd.setColor(Color.BLACK);
		
		font = new Font("宋体", Font.PLAIN, 14);//宋体
		gd.setFont(font);
		gd.drawString(String.format("服务区域：%s", strs[1]), startw, starth);
		
		font = new Font("宋体", Font.PLAIN, 14);//宋体
		gd.setFont(font);
		gd.drawString(String.format("所属公司：%s", strs[2]), startw, starth + 20);
		
	}
	
	private static int drawText(String s, Font font, int w) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rX = (int) Math.round(r2D.getX());
	    int a = (w / 2) - (rWidth / 2) - rX;
	    return a;
	}
	
	private static int getPositionRight(String s, Font font, int startX) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rX = (int) Math.round(r2D.getX());
	    int a = startX + rWidth + rX;
	    return a;
	}
	
	private static int getPosition(String s, Font font, int w) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rX = (int) Math.round(r2D.getX());
	    int a = (w / 2) - (rWidth / 2) - rX;
	    return a;
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

	/**
	 * 定宽压缩
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月16日
	 * @Package com.bj58.fang.imgGenerate
	 * @return BufferedImage
	 */
	private static BufferedImage scaled3(BufferedImage image, int width) {
		float radio = width / ((float)image.getWidth());
		int height = (int) (image.getHeight() * radio);
		BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

//		g2.setColor(Color.WHITE);
//		g2.setBackground(Color.WHITE);
		g2.fill(new Rectangle(image.getWidth() * 2, height));
		buf.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return buf;
	}
	
	private static BufferedImage scaled2(BufferedImage image, int scale) {
		BufferedImage buf = new BufferedImage(image.getWidth() / scale, image.getHeight() / scale, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

//		g2.setColor(Color.WHITE);
//		g2.setBackground(Color.WHITE);
		g2.fill(new Rectangle(image.getWidth() / scale, image.getHeight() / scale));
		buf.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / scale, image.getHeight() / scale, Image.SCALE_SMOOTH), 0, 0, null);
        return buf;
	}
	
	private static BufferedImage scaled(BufferedImage image, int scale) {
		BufferedImage buf = new BufferedImage(image.getWidth() / scale, image.getWidth() / scale, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

//		g2.setColor(Color.WHITE);
//		g2.setBackground(Color.WHITE);
		g2.fill(new Rectangle(image.getWidth() / scale, image.getWidth() / scale));
		buf.getGraphics().drawImage(image.getScaledInstance(image.getWidth() / scale, image.getWidth() / scale, Image.SCALE_SMOOTH), 0, 0, null);
        return buf;
	}
	
	private static BufferedImage round3(BufferedImage image, int h, int w) throws IOException {
//		BufferedImage buf = new BufferedImage(w, w,
//                BufferedImage.TYPE_INT_ARGB);
		BufferedImage buf = ImageIO.read(new File("D:\\img_bg@3x.png"));
		buf = scaled3(buf, 1200);//TODO 1280
		ImageIO.write(buf, "png", new File("D:\\news4xx.jpg"));
		Graphics2D g2 = buf.createGraphics();
		g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		
//		g2.setColor(new Color(0x43, 0x6E, 0xEE));
//		g2.fill(new Rectangle2D.Double(0, 0, buf.getWidth(), buf.getHeight()));
		
//        g2.setColor(Color.WHITE);
//	    g2.fill(new RoundRectangle2D.Double(0, 0, w, w, w, w));
	    g2.setComposite(AlphaComposite.SrcAtop);//185为佳
	    g2.drawImage(image, 1200 / 2 - image.getWidth() / 2, 56, null);//TOD185 - h / 2  56bi1,向右移动shape轮廓---在bi1上圈出的内容
	    g2.dispose();
	    
	    return buf;
	}
	
	
	private static BufferedImage round2(BufferedImage image, int h, int w) {
		BufferedImage buf = new BufferedImage(w, w,
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		
		g2.setColor(new Color(0x43, 0x6E, 0xEE));
		g2.fill(new Rectangle2D.Double(0, 0, buf.getWidth(), buf.getHeight()));
		
        g2.setColor(Color.WHITE);
	    g2.fill(new RoundRectangle2D.Double(0, 0, w, w, w, w));
	    g2.setComposite(AlphaComposite.SrcAtop);
	    g2.drawImage(image, 0, 0, null);//TODO bi1,向右移动shape轮廓---在bi1上圈出的内容
	    g2.dispose();
	    
	    return buf;
	}
	
	private static BufferedImage round(BufferedImage image, int h, int w) {
		BufferedImage buf = new BufferedImage(w, w,
                BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buf.createGraphics();
		g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);  
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

//		g2.setColor(Color.BLUE);
//		g2.fill(new Rectangle2D.Double(0, 0, buf.getWidth(), buf.getHeight()));
		
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
