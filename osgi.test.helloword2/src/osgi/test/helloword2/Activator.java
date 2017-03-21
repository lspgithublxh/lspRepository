package osgi.test.helloword2;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import osgi.test.service.Ihello;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}


	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Ihello service = (Ihello)bundleContext.getService(bundleContext.getServiceReference(Ihello.class.getName()));
		System.out.println(service.getHello());
		
	}


	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
