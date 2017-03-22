package osgi.test.helloword;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import osgi.test.impl.IhlloImpl;
import osgi.test.impl.TriggerEventImpl;
import osgi.test.service.Ihello;
import osgi.test.service.TriggerEventService;

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
		Activator.context = bundleContext;
		System.out.println("Hello world! Good night");
		bundleContext.registerService(Ihello.class.getName(), new IhlloImpl(), null);
		bundleContext.registerService(TriggerEventService.class.getName(), new TriggerEventImpl(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("stop osgi");
	}

}
