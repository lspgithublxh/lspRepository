/**
 * 
 */
package osgi.test.eventHandler;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * @author Administrator
 * 2017Äê3ÔÂ22ÈÕ
 * MyEventHandler
 */
public class MyEventHandler implements EventHandler{

	@Override
	public void handleEvent(Event event) {
		System.out.println("handle this event:" + event.getTopic() + "  " + event.toString());
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
