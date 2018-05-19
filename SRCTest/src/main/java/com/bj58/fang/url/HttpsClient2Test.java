package com.bj58.fang.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.alibaba.fastjson.JSONObject;

public class HttpsClient2Test {

	public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		getWXToken();
	}

	private static void getWXToken() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
			
		});
		SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(builder.build(), new String[] {"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null,
				NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> re = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory())
				.register("https", ssf)
				.build();
		PoolingHttpClientConnectionManager client = new PoolingHttpClientConnectionManager(re);
		client.setMaxTotal(200);//最大200个连接
		
		//开始具体请求
		CloseableHttpClient cl = HttpClients.custom().setSSLSocketFactory(ssf).setConnectionManager(client).setConnectionManagerShared(true).build();
		HttpGet get = new HttpGet("https://miniappfang.58.com/api/token");
		try {
			CloseableHttpResponse response = cl.execute(get);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine().getStatusCode());
			InputStream in = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();
			JSONObject json = JSONObject.parseObject(line);
			if(json.getInteger("code") == 0) {
				System.out.println(json.getString("token"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
