package com.li.shao.ping.KeyListBase.datastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SkipNode{

	private Integer val;
	private SkipNode bottom;
	private SkipNode next;
}
