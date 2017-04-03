package com.test.model.test.bizc;
import com.sgcc.uap.rest.support.QueryResultObject;
import java.io.Serializable;
import com.sgcc.uap.mdd.runtime.base.BizCDefaultImpl;
import java.util.*;
import com.test.model.test.po.Employee;
import com.sgcc.uap.rest.support.RequestCondition;
import org.hibernate.Hibernate;


public class EmployeeBizc extends BizCDefaultImpl<Employee, Serializable> implements IEmployeeBizc {

	/**************** 标准方法执行前后事件,默认全部返回true *******************/
	@Override
	protected void afterDelete(Employee employee) {
		// 自定义逻辑
	
	}

	@Override
	protected void afterAdd(Employee employee) {
		// 自定义逻辑
	}

	@Override
	protected boolean beforeDelete(Employee employee) {
		// 自定义逻辑
		
		return true;
	}

	@Override
	protected boolean beforeAdd(Employee employee) {
		// 自定义逻辑
		return true;
	}

	@Override
	protected void afterUpdate(Employee employee ,Serializable pk) {
		// 自定义逻辑
	}

	@Override
	protected boolean beforeUpdate(Employee employee, Serializable pk) {
		// 自定义逻辑
		return true;
	}
	@Override
	public Employee get(Serializable id) {

		Employee employee = super.get(id);
		/*
		if (employee != null) {
			Hibernate.initialize(employee.getDept());

		}
		*/
		return employee;
	}

	@Override
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryResultObject qo = super.query(queryCondition);
		/*
		List<Employee> employees = qo.getItems();
		if (employees != null) {
			for (Employee employee : employees) {
				Hibernate.initialize(employee.getDept());

			}
		}
		*/
		return qo;
	}
	
}
