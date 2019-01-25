package com.bj58.pubthree.jiankong;

/**
 * 有get/set才能被发现为属性；；且属性的get/set开头的方法被忽略---不认为是属性
 * @ClassName:JackMXBean
 * @Description:
 * @Author lishaoping
 * @Date 2019年1月25日
 * @Version V1.0
 * @Package com.bj58.pubthree.jiankong
 */
public interface JackMXBean {

	public void hi();
	
	public void setHi(String name);
	
	public String getHi();
	
	public void attr2();
	
	public void daican(String so);
}
