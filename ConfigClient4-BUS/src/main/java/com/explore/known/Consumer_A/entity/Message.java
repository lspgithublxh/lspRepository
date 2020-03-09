package com.explore.known.Consumer_A.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Message {

	private long id;
	private String msg;
}
