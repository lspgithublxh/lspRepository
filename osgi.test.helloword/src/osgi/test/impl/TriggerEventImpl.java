/**
 * 
 */
package osgi.test.impl;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventAdmin;

import osgi.test.event.MyEvent;
import osgi.test.service.TriggerEventService;

/**
 * @author Administrator
 * 2017Äê3ÔÂ22ÈÕ
 * TriggerEventImpl
 */
public class TriggerEventImpl implements TriggerEventService{

	@Override
	public void trggerEvent(BundleContext context) {
		EventAdmin admin = (EventAdmin) context.getService(context.getServiceReference(EventAdmin.class.getName()));
		System.out.println("post event started");
		admin.postEvent(new MyEvent());
		System.out.println("post event end");
	}

}
