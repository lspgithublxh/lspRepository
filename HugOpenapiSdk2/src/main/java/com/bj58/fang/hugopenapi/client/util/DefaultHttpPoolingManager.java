package com.bj58.fang.hugopenapi.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description:
 * @Package com.bj58.fang.hugopenapi.client.util
 */
public class DefaultHttpPoolingManager{

	private static Logger logger = LoggerFactory.getLogger(DefaultHttpPoolingManager.class);
	
	private static PoolingHttpClientConnectionManager httpPool = null;

	


	private static DefaultHttpPoolingManager instance = null;

	private static CloseableHttpClient httpClient;

	@SuppressWarnings("unused")
	private static RequestConfig requestConfig;

	public static DefaultHttpPoolingManager getInstance() {
		if(instance == null) {
			synchronized (DefaultHttpPoolingManager.class) {
				if(instance == null) {
					instance = new DefaultHttpPoolingManager();
				}
			}
		}
		return instance;
	}
	
	private static int maxTotal = 200;
	private static int maxPerRoute = 20;

	private static int connectionReqestTimeout = 2 * 1000;
	private static int connetionTimeout = 2 * 1000;

	private static int socketTimeout = 2 * 1000;
	private static boolean hasConfig = false;
	
	public static void config(Integer maxTotal,Integer maxPerRoute,
			Integer connectionReqestTimeout, Integer connetionTimeout, Integer socketTimeout) {
		if(!hasConfig) {
			synchronized (DefaultHttpPoolingManager.class) {
				if(!hasConfig) {
					DefaultHttpPoolingManager.maxTotal = maxTotal;
					DefaultHttpPoolingManager.maxPerRoute = maxPerRoute;
					DefaultHttpPoolingManager.connectionReqestTimeout = connectionReqestTimeout;
					DefaultHttpPoolingManager.connetionTimeout = connetionTimeout;
					DefaultHttpPoolingManager.socketTimeout = socketTimeout;
				}
			}
		}
	}

	public DefaultHttpPoolingManager() {
		init();
	}

	public void init() {
		httpPool = new PoolingHttpClientConnectionManager();
		httpPool.setMaxTotal(maxTotal);
		httpPool.setDefaultMaxPerRoute(maxPerRoute);
		requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionReqestTimeout)
				.setConnectTimeout(connetionTimeout)
				.setSocketTimeout(socketTimeout)
				.build();
		httpClient = HttpClients.custom().setConnectionManager(httpPool).build();
	}

	public String getDataForTT(String url) {
		HttpGet get = new HttpGet(url);
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("http conncetion error", e);
		} finally {
			get.releaseConnection();
		}
		return stringBuffer.toString();
	}
	
	protected CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	/**
	 * 简单点 请求的方式
	 * @param 
	 * @author lishaoping
	 * @Date 2018年8月2日
	 * @Package com.bj58.fang.miniapp.utils
	 * @return String
	 */
	public String getGetJson2(String path)  throws Exception {
		CloseableHttpClient httpClient = getHttpClient();
		HttpGet httpGet = new HttpGet(path);
		CloseableHttpResponse response = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			response = httpClient.execute(httpGet);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpGet.releaseConnection();
		}
		return stringBuffer.toString();
	}
	
	public String get(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			response = httpClient.execute(httpGet);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpGet.releaseConnection();
		}
		return stringBuffer.toString();
	}
	
	public String getGetJson(String path, List<NameValuePair> data) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();
		URIBuilder uri = null;
		String[] params = new String[0];
		if (path.indexOf("?") > -1) {
			params = path.split("\\?")[1].split("&");
			path = path.split("\\?")[0];
		}
		uri = new URIBuilder(path);
		if (params != null) {
			for (String param : params) {
				uri.addParameter(param.split("=")[0], param.split("=")[1]);
			}
		}
		uri.addParameters(data);
		HttpGet httpGet = new HttpGet(uri.toString());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpGet.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public String getGetJson(String path, Map<String, String> data) throws Exception {
		if(data != null && data.size() > 0) {
			List<NameValuePair> pairDataList = new ArrayList<NameValuePair>();
			for (String mapKey : data.keySet()) {
				pairDataList.add(new BasicNameValuePair(mapKey, data.get(mapKey)));
			}
			return getGetJson(path, pairDataList);
		}else {
			
		}
		return "";
	}

	public String getPostJson(String path, Map<String, String> data) throws Exception {
		if (data != null && data.size() > 0) {
			List<NameValuePair> pairDataList = new ArrayList<NameValuePair>();
			for (String mapKey : data.keySet()) {
				pairDataList.add(new BasicNameValuePair(mapKey, data.get(mapKey)));
			}
			return getPostJson(path, pairDataList);
		}
		return "";
	}

	public String getPostJson(String path, List<NameValuePair> data) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();
		URIBuilder uri = null;
		uri = new URIBuilder(path);
		HttpPost httpPost = new HttpPost(uri.toString());
		httpPost.setEntity(new UrlEncodedFormEntity(data, "utf-8"));
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpPost.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public String getPostJsonWithHeader(String path, Map<String, String> data, Map<String, String> headers) throws Exception {

		List<NameValuePair> pairDataList = new ArrayList<NameValuePair>();
		if(data != null) {
			for (String mapKey : data.keySet()) {
				pairDataList.add(new BasicNameValuePair(mapKey, data.get(mapKey)));
			}
		}

		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();

		HttpPost httpPost = new HttpPost(path);

		if(headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(pairDataList, "utf-8"));
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpPost.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public String getPostWithJsonContentType(String path, JSONObject jsonObject) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();
		URIBuilder uri = null;
		uri = new URIBuilder(path);
		HttpPost httpPost = new HttpPost(uri.toString());
		StringEntity entity = new StringEntity(jsonObject.toJSONString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpPost.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public String getDeleteJsonWithHeader(String path, Map<String, String> headers, Map<String, String> data) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();

		URIBuilder uri = new URIBuilder(path);

		for (String key : data.keySet()) {
			uri.addParameter(key, data.get(key));
		}

		HttpDelete httpPost = new HttpDelete(uri.toString());
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpPost.releaseConnection();
		}
		return stringBuffer.toString();
	}

	public String getPutJsonWithHeader(String path, Map<String, String> data, Map<String, String> headers) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		CloseableHttpClient httpClient = getHttpClient();
		URIBuilder uri = new URIBuilder(path);
		HttpPut httpPut = new HttpPut(uri.toString());

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPut.setHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse response = null;
		try {
			StringEntity entity = new StringEntity(JSONObject.toJSONString(data), "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPut.setEntity(entity);
			response = httpClient.execute(httpPut);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("", e);
		} finally {
		}
		return stringBuffer.toString();

	}
}