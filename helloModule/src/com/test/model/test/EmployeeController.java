package com.test.model.test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import com.sgcc.uap.mdd.runtime.meta.IMetadataService;
import com.sgcc.uap.mdd.runtime.utils.HttpMessageConverter;
import org.osgi.framework.Bundle;
import com.sgcc.uap.service.validator.ServiceValidatorBaseException;
import com.sgcc.uap.rest.annotation.QueryRequestParam;
import com.sgcc.uap.rest.annotation.attribute.ViewAttributeData;
import com.sgcc.uap.mdd.runtime.validate.IValidateService;
import org.springframework.http.server.ServletServerHttpRequest;
import java.net.URL;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sgcc.uap.rest.annotation.VoidResponseBody;
import javax.annotation.Resource;
import com.sgcc.uap.rest.annotation.ColumnResponseBody;
import java.util.*;
import com.sgcc.uap.rest.annotation.IdRequestBody;
import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import org.springframework.web.client.RestClientException;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.annotation.ItemResponseBody;
import com.sgcc.uap.rest.annotation.ItemsRequestBody;
import com.test.model.test.bizc.IEmployeeBizc;
import com.sgcc.uap.mdd.runtime.base.validator.ValidateResult;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.mdd.runtime.utils.BeanUtils;
import com.sgcc.uap.mdd.runtime.utils.BodyReaderRequestWrapper;
import com.sgcc.uap.rest.support.IDRequestObject;
import org.springframework.web.bind.annotation.PathVariable;
import com.sgcc.uap.rest.annotation.ColumnRequestParam;
import com.test.model.test.po.Employee;
import org.osgi.framework.FrameworkUtil;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Resource
	private IEmployeeBizc employeeBizc;
	
	@Resource
	private IDataDictionaryBizC dataDictionaryBizC;
	
	@Resource
	private IMetadataService metadataService;
	@Resource
	private IValidateService validateService;
	@Resource
	private HttpMessageConverter coverter;
	@RequestMapping("/meta")
	public @ColumnResponseBody List<ViewAttributeData> getPropertyMeta(@ColumnRequestParam("params") String[] filterPropertys) throws Exception {
	
		List<ViewAttributeData> datas = null;
		datas = metadataService.getPropertyMeta(this.getClass(), "com.test.model.test.po.Employee", filterPropertys);
		return datas;
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ItemResponseBody List<Employee> save(HttpServletRequest request_){
		try {
			//获取servlet API
			ServletServerHttpRequest servlet = new BodyReaderRequestWrapper(request_);
	        //将模型转换为java对象
			Employee[] employees = coverter.converter(new Employee[0], servlet);
		    List<Map<String,Object>> changedProperies = coverter.converter(new ArrayList<Map<String,Object>>(), servlet);
	        List<Employee> voList = new ArrayList<Employee>();
	        //对所有属性进行后端校验
			validateService.validateWithException(Employee.class, changedProperies);
			//遍历表单数据, 如果当前数据在数据库里存在, 则做修改, 否则做新增处理
			for (int i = 0; i < employees.length; i++) {
				Employee employee= employees[i];
				Serializable pkValue = employee.getId();
				Map<String,Object> changedProperty = coverter.flatHandle(Employee.class,changedProperies.get(i));
				if (null != pkValue) {
					Employee old = employeeBizc.get(pkValue);

	 				BeanUtils.populate(old, changedProperty);
	 				
	                employeeBizc.update(old, pkValue);
					voList.add(employee);
	
				}else{
					BeanUtils.populate(employee, changedProperty);
					employeeBizc.add(employee);
					voList.add(employee);
				}
			}
			return voList;
		}catch (ServiceValidatorBaseException e) {
			throw e;
		}catch (Exception e) {
			throw new RestClientException("保存方法异常", e);
		}
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @VoidResponseBody Object delete(@IdRequestBody IDRequestObject idObject){
		String[] ids = idObject.getIds();
		for (String id : ids) {
			employeeBizc.delete(java.lang.String.valueOf(id));
		}
		return null;
	}

	@RequestMapping("/{id}")
    public @ItemResponseBody QueryResultObject get(@PathVariable String id) {
		Employee employee ;
		if("null".equals(id)){
			employee = null;
		}else {
			employee = employeeBizc.get(java.lang.String.valueOf(id));
		}
		QueryResultObject qObject = new QueryResultObject();
		List items = new ArrayList();
		items.add(employee);
		qObject.setItems(items);

    	return qObject;
    }


	@RequestMapping("/")
    public @ItemResponseBody QueryResultObject query(@QueryRequestParam("params") RequestCondition queryCondition){
	    QueryResultObject queryResult = employeeBizc.query(queryCondition);

	    return queryResult;
    }


	
}
