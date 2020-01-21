package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;


public class ReadFileUtil {

	public static ReadFileUtil instance = new ReadFileUtil();
	
	public void readFromChannel(ByteBuffer buffer, FileChannel channel, ByteArrayOutputStream out) throws IOException {
		int len = 0;
		
		byte[] data = new byte[1024];
		while((len = channel.read(buffer)) > 0) {
			buffer.flip();
			buffer.get(data, 0, len);
			buffer.compact();
			out.write(data, 0, len);
		}
	}

}
