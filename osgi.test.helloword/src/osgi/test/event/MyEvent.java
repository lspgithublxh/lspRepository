/**
 * 
 */
package osgi.test.event;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.event.Event;

/**
 * @author Administrator
 * 2017Äê3ÔÂ22ÈÕ
 * MyEvent
 */
public class MyEvent extends Event{

	public static final String MY_TOPIC = "osgi/test/event/MyEvent";
	
	public MyEvent(String topic, Dictionary<String, ?> properties) {
		super(topic, properties);
	}

	public MyEvent(String topic, Map<String, ?> properties) {
		super(topic, properties);
	}

	@Override
	public String toString() {
		return "MyEvent";
	}
	
	public MyEvent(){
		super(MY_TOPIC, new HashMap<>());
	}
}
