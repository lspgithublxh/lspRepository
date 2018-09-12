package recommend;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpConectionManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultHttpConectionManager.class);
	
	private static PoolingHttpClientConnectionManager httpPool = null;
	static CloseableHttpClient httpClient;//HttpClients.createDefault();不默认的创建方式--用pool中来创建
	static final int timeOut = 2 * 1000;
	RequestConfig requestConfig;
	static DefaultHttpConectionManager instance = new DefaultHttpConectionManager();
	
	public DefaultHttpConectionManager() {
		super();
		init();
	}

	public static DefaultHttpConectionManager getInstance() {
		return instance;
	}

	public void init() {
		httpPool = new PoolingHttpClientConnectionManager();
		httpPool.setMaxTotal(200);
		httpPool.setDefaultMaxPerRoute(20);
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
		httpClient = HttpClients.custom().setConnectionManager(httpPool).build();
	}
	
	public String getPostJsonWithHeader(String path, Map<String, String> data, Map<String, String> headers) throws Exception {
		HttpPost httpPost = new HttpPost(path);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			httpPost.setHeader(entry.getKey(), entry.getValue());
		}
		StringBuffer stringBuffer = getContent(data, httpPost);
		return stringBuffer.toString();
	}
	
	public String getPostJson(String path, Map<String, String> data) throws Exception {
		HttpPost httpPost = new HttpPost(path);
		StringBuffer stringBuffer = getContent(data, httpPost);
		return stringBuffer.toString();
	}

	private StringBuffer getContent(Map<String, String> data, HttpPost httpPost) throws UnsupportedEncodingException {
		List<NameValuePair> pairDataList = new ArrayList<NameValuePair>();
		for (String mapKey : data.keySet()) {
			pairDataList.add(new BasicNameValuePair(mapKey, data.get(mapKey)));
		}
		StringBuffer stringBuffer = new StringBuffer();
		
		httpPost.setEntity(new UrlEncodedFormEntity(pairDataList, "utf-8"));
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity in = response.getEntity();
			stringBuffer.append(EntityUtils.toString(in, "utf-8"));
		} catch (Exception e) {
			logger.error("http conncetion error", e);
		} finally {
			httpPost.releaseConnection();
		}
		return stringBuffer;
	}
	
	public String getData(String url) {
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
	
	public String getData(String url, Map<String, String> data) {
		HttpGet get = new HttpGet(url);
		for (Map.Entry<String, String> entry : data.entrySet()) {
			get.setHeader(entry.getKey(), entry.getValue());
		}
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
	
	public static void main(String[] args) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("imei", "861498039727957");
		headers.put("uid", "35667656346895");
//		String s = DefaultHttpConectionManager.getInstance().getData("http://appfang.58.com/api/list/ershoufang?tabkey=allcity&action=checkAnxuanRedirect&curVer=8.8.0&appId=1&localname=bj&os=android&format=json&geotype=baidu&v=1&location=1%2C1142%2C7551&filterParams=%7B%22param1077%22%3A%220%22%7D&page=2&geoia=39.993792%2C116.511392", headers);
		String s = DefaultHttpConectionManager.getInstance().getData("http://appfang.58.com/api/list/ershoufang?action=checkAnxuanRedirect&curVer=8.8.0&localname=bj",headers);
		System.out.println(s);
	}
}
