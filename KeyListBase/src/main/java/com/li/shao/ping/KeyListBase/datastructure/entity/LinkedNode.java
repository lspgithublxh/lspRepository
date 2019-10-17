package com.li.shao.ping.KeyListBase.datastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LinkedNode {
	private double score;
	private String value;
	private long id;
	private LinkedNode next;
}
