package com.bj58.fang.imgGenerate;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @ClassName:LoadFont
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月15日
 * @Version V1.0
 * @Package com.bj58.fang.imgGenerate
 */
public class LoadFont {

	/**
	 * 获取专门字体
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月15日
	 * @Package com.bj58.fang.imgGenerate
	 * @return Font
	 */
	public static Font loadFont(String fontName, float fontSize)   {
		Font font = null;
		try {
			FileInputStream in = new FileInputStream(fontName);
			font = Font.createFont(Font.TRUETYPE_FONT, in);
			font = font.deriveFont(fontSize);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
		
	}
}
