package com.li.shao.ping.KeyListBase.datastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArrayNode<V> {
	private double score;
	private V value;
	private long id;
}
