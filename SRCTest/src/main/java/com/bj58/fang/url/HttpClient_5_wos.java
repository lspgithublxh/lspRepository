package com.bj58.fang.url;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient_5_wos {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static void main(String[] args) throws IOException, OutOfMemoryError, NoSuchAlgorithmException {
		abc("https://testv1.wos.58dns.org/hbg_web_aroundweb/app_landord_share/abc.jpg");
	}

	private static void abc(String url) throws IOException, OutOfMemoryError, NoSuchAlgorithmException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
//        httpPost.setHeader("Content-Type","application/json");  //
        httpPost.addHeader("Host", "testv1.wos.58dns.org");
        httpPost.addHeader("Authorization", "testv1.wos.58dns.org");
        httpPost.addHeader("Content-Type", "multipart/form-data");
        httpPost.addHeader("Content-Length", "testv1.wos.58dns.org");
        MultipartEntity entity = new MultipartEntity();
        FileInputStream in = new FileInputStream("D:\\download\\timg.gif");//合成图会当作是png  compu.jpg 
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int l = 0;
        while((l = in.read(b)) > 0) {
        	out.write(b, 0, l);
        }
        
        entity.addPart("filecontent", new ByteArrayBody(out.toByteArray(), "abc.txt"));
        entity.addPart("op", new StringBody("upload"));
//        entity.addPart("sha", new StringBody(getSha1(out.toByteArray())));
        entity.addPart("insertOnly", new StringBody("1"));
        entity.addPart("ttl", new StringBody("168"));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String strResult = "";
        if(httpResponse != null){ 
            System.out.println(httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } else {
                strResult = "Error Response: " + httpResponse.getStatusLine().toString();
            } 
        }
        System.out.println(strResult);
        
	}
	
	public static String getSha1(byte[] input) throws OutOfMemoryError,
		IOException, NoSuchAlgorithmException {
		MessageDigest messagedigest = MessageDigest.getInstance("SHA-1");
//		FileInputStream in = new FileInputStream(file);
//		FileChannel ch = in.getChannel();
//		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
//				file.length());
//		messagedigest.update(byteBuffer);
		messagedigest.update(input);
		return bufferToHex(messagedigest.digest());
	}
	
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}
 
	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}


}
