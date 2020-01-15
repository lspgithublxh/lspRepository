package com.li.shao.ping.KeyListBase.datastructure.util.server;

import java.io.OutputStream;

import com.li.shao.ping.KeyListBase.datastructure.util.reader.HttpStreamReaderWriter;

@FunctionalInterface
public interface Handler {

	public void handler(String header, byte[] content, HttpStreamReaderWriter util,
			OutputStream out);
}
