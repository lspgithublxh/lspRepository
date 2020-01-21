package com.li.shao.ping.KeyListBase.datastructure.util.httpserver.file;

import java.util.List;
import java.util.regex.Pattern;

import avro.shaded.com.google.common.collect.Lists;

public class LogPattern {

	public static List<Pattern> pList = Lists.newArrayList(java.util.regex.Pattern.compile("exception|Exception", java.util.regex.Pattern.DOTALL));
	
}
