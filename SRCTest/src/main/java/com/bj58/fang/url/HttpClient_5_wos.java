package com.bj58.fang.url;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.spat.wos.ClientConfig;
import com.bj58.spat.wos.WOSClient;
import com.bj58.spat.wos.request.UploadFileRequest;
import com.bj58.spat.wos.sign.Credentials;
import com.bj58.wf.mvc.utils.Base64;

public class HttpClient_5_wos {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static void main(String[] args) throws IOException, OutOfMemoryError, NoSuchAlgorithmException {
//		FileInputStream in;
//		try {
//			in = new FileInputStream("D:\\download\\timg.gif");//D:\\news4.jpg
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//	        byte[] b = new byte[1024];
//	        int l = 0;
//	        while((l = in.read(b)) > 0) {
//	        	out.write(b, 0, l);
//	        }
////			String url = updloadBySpecial(out.toByteArray());
//	        String url = uploadByWOS(out.toByteArray(), String.format("%s_%s.jpg", "14587", System.currentTimeMillis()));
//			System.out.println(url);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}//合成图会当作是png  compu.jpg 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
        
//		wosJava();
		abc("http://testv1.wos.58dns.org/GIhdCbAZyGwhj/applandordsharepic/", "16.png");
	}

	private static void wosJava() {
		//上传资源的域名
		String uploadDomain = "testv1.wos.58dns.org";
		//下载资源的域名
		String downDomain = "testv1.wos.58dns.org";
		//获取token的域名
		String tokenserverDomain = "tokentest.wos.58dns.org";
//		String appId = "ACbXwVuTsRqAf";
//		String bucket = "test";
//		String secretId = "Qu5Wx4aMATJoi95QaAbEDiPslE76hpcX";
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setUploadWosEndPointDomain(uploadDomain);
		clientConfig.setDownWosEndPointDomain(downDomain);
		clientConfig.setTokenServerDomain(tokenserverDomain);
		
		Credentials cred = new Credentials(appid, secretId);
		
		WOSClient client = new WOSClient(clientConfig, cred);
		String fileName = "time.gif";//"head2.png";
		String localFilePath = "D:\\download\\timg.gif";//"D:\\head.jpg";
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket, fileName, localFilePath);
		uploadFileRequest.setTtl(168);//没有或者为0   会永久保存
//		String uploadFileRet = client.uploadFile(uploadFileRequest);
		String uploadFileRet = client.uploadSingleFile(uploadFileRequest);
		System.out.println("upload file ret:" + uploadFileRet);
//		UploadFileRequest r = new UploadFileRequest(bucketName, fileName, contentBuffer)
		
	}
	
	public static String uploadByWOS(byte[] data, String fileName) {
		String picUrl = "";
		//1.上传资源的域名
		String uploadDomain = "testv1.wos.58dns.org";
		//2.下载资源的域名
		String downDomain = "testv1.wos.58dns.org";
		//3.获取token的域名
		String tokenserverDomain = "tokentest.wos.58dns.org";
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setUploadWosEndPointDomain(uploadDomain);
		clientConfig.setDownWosEndPointDomain(downDomain);
		clientConfig.setTokenServerDomain(tokenserverDomain);
		Credentials cred = new Credentials(appid, secretId);
		//4.客户端
		WOSClient client = new WOSClient(clientConfig, cred);
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucket, fileName, data);
		uploadFileRequest.setTtl(168);//没有或者为0   会永久保存
		String uploadFileRet = client.uploadSingleFile(uploadFileRequest);
		JSONObject rs = JSONObject.parseObject(uploadFileRet);
		if(rs.getInteger("code") == 0) {
			picUrl = rs.getJSONObject("data").getString("url");//access_url是CDN url  ， url是源站的
		}
		return picUrl;
		
	}
	
	private static String getToken(CloseableHttpClient httpClient, String bucket, String filename, String appid, String secret_id) {
		String url = String.format("http://tokentest.wos.58dns.org/get_token?bucket=%s&filename=%s", bucket, filename);
//		HttpPost httpPost = getHttpPost(url);
		HttpGet httpGet = getHttpGet(url);
		try {
			httpGet.addHeader("Authorization", String.format("Basic %s", Base64.encode(String.format("%s:%s", appid, secret_id).getBytes("UTF-8"))));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String strResult = getRsFromResponse(httpResponse);
	        System.out.println(strResult);
	        JSONObject rs = JSONObject.parseObject(strResult);
	        if(rs.getInteger("code") == 0) {
	        	return rs.getString("data");
	        }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static HttpGet getHttpGet(String url) {
		RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		return httpGet;
	}

	private static void abc(String url, String filename) throws IOException, OutOfMemoryError, NoSuchAlgorithmException {
		String appid = "GIhdCbAZyGwhj";
		String secretKey = "UqZ3o85DcbcQiq32ATbNGSTDKcbJzbjo";
		String secretId = "GeQ6AoBhegvSBCcmmx288OGroRgEhbQp";
		String bucket = "applandordsharepic";
		CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        HttpPost httpPost = getHttpPost(url + filename);
//		HttpGet httpGet = getHttpGet(url + filename);
//        httpPost.setHeader("Content-Type","application/json");  //
       
//        httpPost.addHeader("Content-Length", "testv1.wos.58dns.org".length());
        MultipartEntity entity = new MultipartEntity();
        FileInputStream in = new FileInputStream("D:\\16.png");//合成图会当作是png  compu.jpg 
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int l = 0;
        while((l = in.read(b)) > 0) {
        	out.write(b, 0, l);
        }
        String au = getToken(httpClient, bucket, filename, appid, secretId);
        System.out.println(au);
        //new FileBody(new File("D:\\head.jpg"), "application/octet-stream")
//        entity.addPart("filecontent", new FileBody(new File("D:\\16.png"), "application/octet-stream"));//new ByteArrayBody(out.toByteArray(), filename)
        entity.addPart("op", new StringBody("upload", Charset.forName("UTF-8")));
//        entity.addPart("appid", new StringBody(appid));
//        entity.addPart("bucket", new StringBody(bucket));
        entity.addPart("sha", new StringBody(getSha1(out.toByteArray())));
//        entity.addPart("insertOnly", new StringBody("1"));
//        entity.addPart("ttl", new StringBody("168"));
        httpPost.setEntity(entity);
        httpPost.addHeader("Host", "testv1.wos.58dns.org");//UzB4VVk2UlgwUEFlQ25lM2dNSVZsbzJjY0JzPTplPTE0ODkwNDg5Mzg=
        httpPost.addHeader("Authorization", au);
        httpPost.addHeader("Content-Type", "multipart/form-data");
//        httpPost.addHeader("Content-Length", "" + entity.getContentLength());//有boy自动加
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String strResult = getRsFromResponse(httpResponse);
        System.out.println(strResult);
        
	}

	private static String getRsFromResponse(HttpResponse httpResponse) throws IOException {
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
		return strResult;
	}

	private static HttpPost getHttpPost(String url) {
		RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
		return httpPost;
	}
	
	public static byte[] getRSASign(byte[] source, String encodedKey){
		String rs = "";
		try {
			PKCS8EncodedKeySpec ps = new PKCS8EncodedKeySpec(encodedKey.getBytes("UTF-8"));
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey key = kf.generatePrivate(ps);
			Signature sign = Signature.getInstance("MD5withRSA");
			sign.initSign(key);
			sign.update(source);
			return sign.sign();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
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
