/**
 * 
 */
package osgi.test.impl;

import osgi.test.service.Ihello;

/**
 * @author Administrator
 * 2017��3��21��
 * IhlloImpl
 */
public class IhlloImpl implements Ihello{

	@Override
	public String getHello() {
		return "get hello info";
	}

}
