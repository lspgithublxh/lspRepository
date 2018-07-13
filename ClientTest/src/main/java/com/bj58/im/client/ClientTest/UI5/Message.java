package com.bj58.im.client.ClientTest.UI5;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息格式，第一种
 * @ClassName:Message
 * @Description:
 * @Author lishaoping
 * @Date 2018年6月22日
 * @Version V1.0
 * @Package com.bj58.im.client.ClientTest.UI3
 */
public class Message implements Serializable{

	public Integer id;//消息索引
	public String text;//文本内容
	public Integer type;//类型   : 1文本 2 文件
	public Map<String, Object> params;
	public Integer direction;//是对方还是自己发送的，左边或者右边  1自己 2对方
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Message(Integer id, String text, Integer type, Integer direction) {
		super();
		this.id = id;
		this.text = text;
		this.type = type;
		this.direction = direction;
	}
	public Message() {
		super();
	}
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", text=" + text + ", type=" + type + ", params=" + params + ", direction="
				+ direction + "]";
	}
	
	
}
