package com.li.shao.ping.KeyListBase.datastructure.entity;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.li.shao.ping.KeyListBase.datastructure.geneutil.v2.SimpleConnectPoolUtil;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rejection2Entity2<T> {

	private String service;
	private String ipPort;
	private byte[] task;
	private LinkedBlockingQueue<T> queue;
	private String user;
	private Map<String, byte[]> receivedMap;
	private SimpleConnectPoolUtil util;
}
