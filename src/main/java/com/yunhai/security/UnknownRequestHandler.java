/**
 * 
 */
package com.yunhai.security;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.impl.ActionConfigMatcher;

/**
 * @author Administrator
 * 2017年3月28日
 * UnknownRequestHandler
 */
public class UnknownRequestHandler implements UnknownHandler{

	@Override
	public ActionConfig handleUnknownAction(String namespace, String actionName) throws XWorkException {
		return null;
		
	}

	@Override
	public Result handleUnknownResult(ActionContext actionContext, String actionName, ActionConfig actionConfig,
			String resultCode) throws XWorkException {
		return null;
	}

	@Override
	public Object handleUnknownActionMethod(Object action, String methodName) throws NoSuchMethodException {
		return null;
	}

}
