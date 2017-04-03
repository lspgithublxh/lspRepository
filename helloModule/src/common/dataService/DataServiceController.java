package common.dataService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import com.sgcc.uap.mdd.runtime.data.AbstractDataServiceController;

@Controller
@RequestMapping("/data")
public class DataServiceController extends AbstractDataServiceController {
	
	public Bundle getBundle() {
		Bundle bundle = FrameworkUtil.getBundle(DataServiceController.class);
		return bundle;
	}
}
