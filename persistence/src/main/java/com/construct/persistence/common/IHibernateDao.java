package com.construct.persistence.common;

import java.util.List;
import java.util.Map;

public interface IHibernateDao {

	public List<?> queryForListByNamedSqlOrHql(String sql, Map<String, String> params, Class<? extends Object> cla_);
	
	public <T> List<T> queryForListByNamedSqlOrHql(String sql, Map<String, String> params);
	
	public <T> List<T> queryForListByNamedQuery(String sql, Map<String, String> params, Class<T> cla_);

}
