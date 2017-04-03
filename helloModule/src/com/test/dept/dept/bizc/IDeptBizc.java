package com.test.dept.dept.bizc;

import java.util.List;
import java.util.Map;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.test.dept.dept.po.Dept;
import com.test.dept.dept.vo.DeptVO;

/**
 * 单表场景逻辑构件
 *
 */
public interface IDeptBizc {

	/**
	 * 保存更新方法
	 * @param ll
	 * @return list
	 */
	public List<DeptVO> saveOrUpdate(List<Map> ll);
			
	/**
	 * 删除
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject);
	
	/**
	 * 查询
	 * @param queryCondition
	 * @return QueryResultObject
	 */
	public QueryResultObject query(RequestCondition queryCondition);
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id);
	
	public int updatePeroidTime(String id, Integer time);
	
}
