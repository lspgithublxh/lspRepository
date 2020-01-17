package com.li.shao.ping.KeyListBase.datastructure.util.httpserver;

import java.io.OutputStream;
import java.util.List;

import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

public class FilterChain {

	public static FilterChain instance = new FilterChain();
	
	public static FilterChain getInstance() {
		return instance;
	}
	
	public boolean filter(List<Filter> filters, String url, HttpStreamReaderWriter util,
			String header, byte[] content,  OutputStream out) {
		for(Filter f : filters) {
			boolean rs = f.filter(url, header, util, content, out);
			if(rs) {
				return true;
			}
		}
		return false;
	}
}
