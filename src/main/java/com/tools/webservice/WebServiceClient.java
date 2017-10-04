package com.tools.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class WebServiceClient {

	public static void main(String[] args) throws IOException {
		 
//		 javaxMethod();
		String url = "http://localhost:8080/web/services/messageService?wsdl";
		StringBuilder builder = new StringBuilder();
		builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"");
		builder.append(" xmlns:q0=\"http://webservice.tools.construct.com/\" ");
		builder.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> ");
		builder.append(" <soapenv:Body>");
//		builder.append(" <q0:sayHello><arg0>aaa</arg0>  </q0:sayHello> ");
		builder.append(" <q0:getMessage><arg0>aaa</arg0>  </q0:getMessage> ");
		builder.append(" </soapenv:Body> </soapenv:Envelope>");
		String body = builder.toString();
//		String url = "http://localhost:8888/toolT/services/IABService";
//		String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:q0=\"http://webservice.tools.com/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
//                "<soapenv:Body> <q0:sayHello><arg0>aaa</arg0>  </q0:sayHello> </soapenv:Body> </soapenv:Envelope>";
		urlOpenWriteMethod(url, body);
		 
	}
	
	public static void javaxMethod() throws MalformedURLException {
		URL wsdlUrl = new URL("http://localhost:8888/toolT/services/IABService?wsdl");
		//需要写实现类，即有效服务
		 Service service = Service.create(wsdlUrl, new QName("http://webservice.tools.com/", "ABServiceService"));
		 IABService inter = service.getPort(IABService.class);
		 System.out.println(inter.sayHello("d"));;
	}
	
	public static void xfireMethod() {
		
	}
	
	public static void urlOpenWriteMethod(String url, String body) throws IOException {
		URL wsUrl = new URL(url);
        
        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        
        OutputStream os = conn.getOutputStream();
        
        //请求体
        String soap = body;
        
        os.write(soap.getBytes());
        
        InputStream is = conn.getInputStream();
        
        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while((len = is.read(b)) != -1){
            String ss = new String(b,0,len,"UTF-8");
            s += ss;
        }
        System.out.println(s);
        
        is.close();
        os.close();
        conn.disconnect();
	}
}
