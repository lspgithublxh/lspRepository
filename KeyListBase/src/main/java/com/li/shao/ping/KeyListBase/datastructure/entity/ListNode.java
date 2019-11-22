package com.li.shao.ping.KeyListBase.datastructure.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListNode<V extends Serializable> {
	private double score;
	private V value;
	private volatile long id;
	private boolean wait;
}
