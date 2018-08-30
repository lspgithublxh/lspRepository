package com.bj58.im.client.mediaTest;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class ColorDitu {

	public static List<Color> getColorDidu(){
		List<Color> colorList = new ArrayList<>();
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(i, 0, 0);
			colorList.add(co);
		}
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(255, 0, i);
			colorList.add(co);
		}
		for(int i= 0; i < 256; i++) {
			Color co = Color.rgb(255, i, 255);
			colorList.add(co);
		}
		return colorList;
	}
}
