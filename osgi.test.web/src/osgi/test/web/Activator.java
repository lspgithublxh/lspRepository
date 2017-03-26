package osgi.test.web;

import java.util.Hashtable;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}


	public void start(BundleContext bundleContext) throws Exception {
		startHttpRequestMethod2(bundleContext);
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public void startHttpRequestMethod2(BundleContext bundleContext){
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(MyHttpServlet.class, "/*");
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setContextPath("/p");
		contextHandler.setServletHandler(handler);
		Hashtable table = new Hashtable<>();
		bundleContext.registerService(ContextHandler.class.getName(), contextHandler, table);
		System.out.println("Register successed2!");
	}

}
