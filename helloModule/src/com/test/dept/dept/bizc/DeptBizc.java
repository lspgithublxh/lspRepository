package com.test.dept.dept.bizc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgcc.uap.persistence.IHibernateDao;
import com.sgcc.uap.persistence.criterion.QueryCriteria;

import com.sgcc.uap.bizc.sysbizc.datadictionary.IDataDictionaryBizC;
import com.sgcc.uap.rest.support.DicItems;

import com.sgcc.uap.rest.support.IDRequestObject;
import com.sgcc.uap.rest.support.QueryFilter;
import com.sgcc.uap.rest.support.QueryResultObject;
import com.sgcc.uap.rest.support.RequestCondition;
import com.sgcc.uap.rest.utils.CrudUtils;
import com.sgcc.uap.rest.utils.RestUtils;
import org.hibernate.type.Type;

//引入po,vo,transefer
import com.test.dept.dept.po.Dept;
import com.test.dept.dept.vo.DeptTransfer;
import com.test.dept.dept.vo.DeptVO;
/**
 * 用户定义逻辑构件
 * 
 * @author Administrator 
 * 
 */
public class DeptBizc implements IDeptBizc{

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IDataDictionaryBizC dataDictionaryBizC;
	/**
	 * 保存更新
	 * 
	 * @param list
	 */
	 public List<DeptVO> saveOrUpdate(List<Map> list) {
		 
		List<DeptVO> voList = new ArrayList<DeptVO>();
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			String poName = Dept.class.getName();
			if(map.containsKey("id")){
				String id = (String)map.get("id");
				DeptVO vo = update(map,poName,id);
				voList.add(vo);
			}else{
				DeptVO deptVO = save(map);
				voList.add(deptVO);
			}
		}
		return voList;
	 }
	 
	//保存记录
	private DeptVO save(Map map) {
		
		DeptVO deptVo = new DeptVO();
		try {
			BeanUtils.populate(deptVo, map);
		} catch (Exception e) {
		}
		Dept dept = DeptTransfer.toPO(deptVo);
		hibernateDao.saveOrUpdateObject(dept);
		deptVo = DeptTransfer.toVO(dept);
		if(map.containsKey("mxVirtualId")){
			deptVo.setMxVirtualId(String.valueOf(map.get("mxVirtualId")));
		}
		return deptVo;
	}
	
	//更新记录
	private DeptVO update(Map<String, ?> map,String poName,String id) {
		
		DeptVO deptVo = new DeptVO();
		//更新操作
		try {
			BeanUtils.populate(deptVo, map);
		} catch (Exception e) {
		}
		Object[] objArray = CrudUtils.generateHqlWithDataType(Dept.class, map, "id");
		hibernateDao.update((String) objArray[0], (Object[]) objArray[1],(Type[])objArray[2]);
		
		return deptVo;
	}
	
	/**
	 * 删除
	 * 
	 * @param idObject
	 */
	public void remove(IDRequestObject idObject) {
		String[] ids = idObject.getIds();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hibernateDao.update("delete from " + Dept.class.getName() + " where id = ?" ,new Object[]{id});
		}
	}
	
	/**
	 * 根据输入条件查询记录
	 * 
	 * @param queryCondition
	 * @return
	 */
	public QueryResultObject query(RequestCondition queryCondition) {

		QueryCriteria qc = new QueryCriteria();
		List<Dept> result = null;
		int count = 0;
		qc.addFrom(Dept.class);
		if (queryCondition != null) {
			qc = wrapQuery(queryCondition, qc);
			count = getRecordCount(qc);
			qc = wrapPage(queryCondition, qc);
			result = hibernateDao.findAllByCriteria(qc);

		} else {
			result = hibernateDao.findAllByCriteria(qc);
			count = getRecordCount(qc);
		}
		return RestUtils.wrappQueryResult(result, count);
		
		
	}
	private QueryCriteria wrapQuery(RequestCondition queryCondition,
			QueryCriteria qc) {
		List<QueryFilter> wheres = queryCondition.getQueryFilter(DeptVO.class);
		if (wheres != null && wheres.size() > 0) {
			CrudUtils.addQCWhere(qc, wheres, Dept.class.getName());

		}

		String orders = queryCondition.getSorter();
		if (orders != null) {
			qc.addOrder(orders.replaceAll("&", ","));
		}
		return qc;
	}
	
	private QueryCriteria wrapPage(RequestCondition queryCondition,
			QueryCriteria qc) {
		int pageIndex = 1, pageSize = 1;
		if (queryCondition.getPageIndex() != null
				&& queryCondition.getPageSize() != null) {
			pageIndex = Integer.valueOf(queryCondition.getPageIndex());
			pageSize = Integer.valueOf(queryCondition.getPageSize());
			qc.addPage(pageIndex, pageSize);
		}
		return qc;
	}
	
	/**
	 * 查询单条记录
	 * @param id
	 * @return QueryResultObject
	 */
	public QueryResultObject queryById(String id) {
		Dept dept = (Dept) hibernateDao.getObject(Dept.class,id);
		DeptVO vo = null;
		if (dept != null) {
			vo = DeptTransfer.toVO(dept);
		}
		return RestUtils.wrappQueryResult(vo);
	}

	
	// 获取总记录数
	private int getRecordCount(QueryCriteria qc) {
		int count = 0;
		count = hibernateDao.findAllByCriteriaPageCount(qc,1);
		return count;
	}

	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	@Override
	public int updatePeroidTime(String id, Integer time) {//防止sql注入的危险。！！动态生成sql都会遇到---所以不要前端time位置的输入是危险的--尤其是字符串
		String sql = "update dept set PERIOD_OF_VALIDITY = " + time + " where id = '" + id + "'";
		System.out.println(sql);
		int state = this.hibernateDao.updateWithSql(sql);
		return state;
	}
}
