package com.bj58.fang.serialize;

import java.io.Serializable;

/**
 * 
 * @ClassName:Message
 * @Description:
 * @Author lishaoping
 * @Date 2018年6月22日
 * @Version V1.0
 * @Package com.bj58.fang.serialize
 */
public class Message implements Serializable{

	public int id;
	public String name;
	public String address;
	public Message(int id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}
	public Message() {
		super();
	}

}
