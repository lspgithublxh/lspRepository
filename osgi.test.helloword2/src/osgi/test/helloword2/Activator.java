package osgi.test.helloword2;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import osgi.test.event.MyEvent;
import osgi.test.eventHandler.MyEventHandler;
import osgi.test.service.Ihello;
import osgi.test.service.TriggerEventService;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}


	public void start(BundleContext bundleContext) throws Exception {
		//事件handler也是一种服务，也需要注册
		String[] topics = new String[]{MyEvent.MY_TOPIC};
		Hashtable<String, String[]> table = new Hashtable<>();
		table.put(EventConstants.EVENT_TOPIC, topics);
		EventHandler handler = new MyEventHandler();
		bundleContext.registerService(EventHandler.class.getName(), handler, table);
		
		Activator.context = bundleContext;
		Ihello service = (Ihello)bundleContext.getService(bundleContext.getServiceReference(Ihello.class.getName()));
		System.out.println(service.getHello());
		//模拟触发事件
		TriggerEventService service2 = (TriggerEventService) bundleContext.getService(bundleContext.getServiceReference(TriggerEventService.class.getName()));
		service2.trggerEvent(bundleContext);
		
	}


	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
