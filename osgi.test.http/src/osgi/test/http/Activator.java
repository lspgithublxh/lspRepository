package osgi.test.http;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

import osgi.test.servlet.MyServlet;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		//具体的实现应该在tomcat或者jetty中。框架里面应该没有httpservice的实现！！
//		ServiceReference servf = bundleContext.getServiceReference(HttpService.class.getName());
//		 System.out.println(servf);
//		if(servf != null){
//			HttpService service = (HttpService) bundleContext.getService(servf);
//			service.registerResources("/demo", "pages", null);
//		}
//		
		ServiceReference s = bundleContext.getServiceReference("org.eclipse.jetty.server.Server");
		System.out.println("content");
		System.out.println(s);
		System.out.println("content");
		//
		Server server = new Server();
		Dictionary table = new Hashtable<>();
		table.put("managedServerName", "myServer");
		table.put("jetty.http.port", 8080);
		table.put("jetty.etc.config.urls", "D:\\project\\osgi\\repository\\jetty.xml,D:\\project\\osgi\\repository\\jetty-deployer.xml,D:\\project\\osgi\\repository\\jetty-selector.xml");
		bundleContext.registerService(Server.class, server, table);
		
		WebAppContext context = new WebAppContext();
		Dictionary table2 = new Hashtable<>();
		table2.put("war", ".");
		table2.put("contextPath", "/acme");
		table2.put("managedServerName", "fooServer");
		bundleContext.registerService(ContextHandler.class, context, table2);
		startHttpRequestMethod2(bundleContext);
		Activator.context = bundleContext;
	}

	public void startHttpRequestMethod2(BundleContext bundleContext){
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(MyServlet.class, "/*");
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath("/path");
		contextHandler.setServletHandler(handler);
		Hashtable table = new Hashtable<>();
		bundleContext.registerService(ContextHandler.class.getName(), contextHandler, table);
		System.out.println("Register successed!");
	}
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
