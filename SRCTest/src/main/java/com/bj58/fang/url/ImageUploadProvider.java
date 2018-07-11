package com.bj58.fang.url;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.bj58.biz.utility.StringUtil;
import com.bj58.spat.wos.ClientConfig;
import com.bj58.spat.wos.WOSClient;
import com.bj58.spat.wos.request.UploadFileRequest;
import com.bj58.spat.wos.sign.Credentials;

/**
 * 上传图片
 * @ClassName:ImageUploadProvider
 * @Description:
 * @Author lishaoping
 * @Date 2018年5月10日
 * @Version V1.0
 * @Package com.bj58.fang.landlord.app.provider
 */
public class ImageUploadProvider {
	
	
	public static void main(String[] args) {
//		System.out.println(ImageUploadProvider.class.getResource(""));
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(System.getProperty("os.name"));
//		IPICSignFileIdService up = ;
//		SignFileIdEntity   getSignFileIdEntity("", "jpg");
		FileInputStream in;
		try {
			in = new FileInputStream("D:\\download\\timg.gif");//D:\\news4.jpg
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] b = new byte[1024];
	        int l = 0;
	        while((l = in.read(b)) > 0) {
	        	out.write(b, 0, l);
	        }
//			String url = updloadBySpecial(out.toByteArray());
	        String url = uploadByWOS(out.toByteArray(), "timgxx.gif");
			System.out.println("Ok:" + url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}//合成图会当作是png  compu.jpg 
		catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	
//	public static byte[] readPic(String filePath) throws Exception {
//		InputStream inStream = new FileInputStream(filePath);
//		byte[] src = new byte[inStream.available()];
//		inStream.read(src);
//		inStream.close();
//		return src;
//	}

	
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
	
	/**
	 * 上传图片, base64编码格式上传
	 * 合成图，只能缩小，不能放大。。。所以最开始可以给大图，然后缩小
	 * @param 
	 * @author lishaoping
	 * @Date 2018年5月10日
	 * @Package com.bj58.fang.landlord.app.provider
	 * @return String
	 */
	
	
	
}
