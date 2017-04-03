package com.test.model.test.bizc;

import com.sgcc.uap.rest.support.QueryResultObject;
import java.io.Serializable;
import com.sgcc.uap.mdd.runtime.base.IBizC;
import java.util.*;
import com.sgcc.uap.rest.support.RequestCondition;
import com.test.model.test.po.Employee;


public interface IEmployeeBizc extends IBizC<Employee,Serializable>{

	public Employee add(Employee be);
	
	public void delete(Serializable id);
	
	public QueryResultObject query(RequestCondition queryCondition);
	
	public Employee get(Serializable id);
	
	public void update(Employee employee,Serializable pk);
	
	
}
