package com.hadoop.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    
    public void test(){
    	String url = "hdfs://192.168.180.136:9000/";
    	Configuration conf = new Configuration();
    	try {
    		//显示文件状态
			FileSystem fs = FileSystem.get(URI.create(url), conf);
			FileStatus[] status = fs.listStatus(new Path("/user"));
			for(FileStatus stat : status){
				System.out.println(stat);
			}
			//写下一个文件，上传
			FSDataOutputStream os = fs.create(new Path("/user/test.log"));
			os.write("Hello World!".getBytes());
			os.flush();
			os.close();
			//下载
			InputStream is = fs.open(new Path("/user/test.log"));
//			IOUtils.copyBytes(is, System.out, conf);
			IOUtils.copyBytes(is, System.out, 1024, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
}
