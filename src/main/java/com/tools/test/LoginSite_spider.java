package com.tools.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginSite_spider {

	static class DefaultTrustManager implements X509TrustManager{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
	}
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		test();
//		System.out.println(Charset.availableCharsets());
//		byte s = 0x81;
//		byte s = -127;
//		System.out.println(Integer.toHexString(s));
//		System.out.println(Integer.toBinaryString(s));
	}
	
	

	private static void test() throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException,
			IOException, ProtocolException {
		SSLContext ssl = SSLContext.getInstance("TLS");
		ssl.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
		SSLSocketFactory factory = ssl.getSocketFactory();
		URL url = new URL("https://www.shodan.io/search?query=port%3A102");
		URLConnection con = url.openConnection();
		HttpsURLConnection con2 = (HttpsURLConnection) con;
		con2.setSSLSocketFactory(factory);
		con2.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}});
		con2.setDoInput(true);
		con2.setDoOutput(true);
//		"authority": "www.shodan.io",
//        "method": "GET",
//        "path": "/search?query=port%3A102",
//        "scheme": "https",
//        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
//        'Accept-Encoding':'gzip, deflate, sdch, br',
//        'Accept-Language':'zh-CN,zh;q=0.8',
//        'cache-control':'max-age=0',
//        'cookie':'__cfduid=dd7096f06fdc5ad7b5f81ef9706c673351510477684; __guid=257598605.3644327904132300300.1510477677988.1885; polito="b91d5dce8ae4ca991d6009cc56a1d4f75a080dc45a080d79e449853f25e6b2a1!"; session=4af887580ef211b0719c4b44c9fb112d2c2cf19bgAJVQDQ1OWU1MTNhYjFiM2JlZmQ1ZDhjNDM4YWFhMTFmYWE2Y2JlYWU3ZmRjZjM2MWIwNWQ0ZTY2NDA2NmYzOTMwODZxAS4=; monitor_count=9; _ga=GA1.2.1287974922.1510477679; _gid=GA1.2.1788027332.1510477679',
//        'referer':'https://www.shodan.io/explore/category/industrial-control-systems',
//        'Upgrade-Insecure-Requests': str(1),
//        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
//		con2.setRequestProperty("authority", "www.shodan.io");
//		con2.setRequestProperty("method", "GET");
//		con2.setRequestProperty("path", "/search?query=port%3A102");
//		con2.setRequestProperty("scheme", "https");
		con2.setRequestProperty("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
//		con2.setRequestProperty("Accept-Encoding", "gzip, deflate");//, sdch, br
		con2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
//		con2.setRequestProperty("cache-control", "max-age=0");
		con2.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//		con2.setRequestProperty("cookie", "__cfduid=dd7096f06fdc5ad7b5f81ef9706c673351510477684; __guid=257598605.3644327904132300300.1510477677988.1885; polito=\"b91d5dce8ae4ca991d6009cc56a1d4f75a080dc45a080d79e449853f25e6b2a1!\"; session=4af887580ef211b0719c4b44c9fb112d2c2cf19bgAJVQDQ1OWU1MTNhYjFiM2JlZmQ1ZDhjNDM4YWFhMTFmYWE2Y2JlYWU3ZmRjZjM2MWIwNWQ0ZTY2NDA2NmYzOTMwODZxAS4=; monitor_count=9; _ga=GA1.2.1287974922.1510477679; _gid=GA1.2.1788027332.1510477679");
		con2.setRequestProperty("Cookie", "__cfduid=d87e444527da4f351dc26b984084ef3161510500071; polito=a5e45f7cd4b98365da949a0fc189ad7f5a0864c95a080d79e449853f25e6b2a1!; _ga=GA1.2.660487598.1510500135; _gid=GA1.2.1371259293.1510500135; _gat=1; _LOCALE_=en; session=2e4b261a5673bd2e7e5ec4f21c27e5e4071618f9gAJVQGQ5MDg5MmJlNDJiNGRmYzZjODc3NTEyMzdmNzcyYWRmZGJhM2NiNzE1NTA1ODYwYzc1NmNhZDBjOTg2YjBkMDZxAS4=");
		con2.setRequestProperty("Referer", "https://www.shodan.io/search?query=port%3A102");
//		con2.setRequestProperty("Upgrade-Insecure-Requests", "1");
//		con2.setRequestProperty("Connection", "keep-alive");
		con2.setRequestProperty("Host", "www.shodan.io");
		con2.setRequestMethod("GET");
		con2.connect();
//		OutputStream out = con2.getOutputStream();
//		out.write("query=port%3A102".getBytes());
//		out.flush();
//		out.close();
		InputStream in = con2.getInputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int l = 0;
		while((l = in.read(b)) > 0) {
			out2.write(b, 0, l);
		}
//		for(byte b2 : out2.toByteArray()) {
////			System.out.print(Integer.toHexString(b2));
//			System.out.print(Integer.toHexString(b2) + ",");
//		}
//		System.out.println();
//		byte[] ss = out2.toByteArray();
//		System.out.println(ss[0] );
//		System.out.println(ss[1] );
//		System.out.println(ss[2] );
		System.out.println(out2.toString("ISO-8859-1"));
		System.out.println(out2.toString("UTF-8"));
		
//		InputStreamReader reader = new InputStreamReader(in, "unicode");
//		BufferedReader read = new BufferedReader(reader);
//		String line = "";
//		while((line = read.readLine()) != null) {
//			System.out.println(line);
//		}
	}
}
