package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.model;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogModel {

	private String fileName;
	private String content;
	
}
