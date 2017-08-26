package com.construct.persistence.common;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("hibernateDao")
public class HibernateDao {

	@Autowired
	private SessionFactory sessionFactory; 
	
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
	
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForListByNamedHql(String hql, Map<String, String> param, Class<T> cla_){
		Query<T> query = getCurrentSession().getNamedQuery(hql);
		for(String key : param.keySet()){
			query.setParameter(key, param.get(key));
		}
		List<T> result = query.list();
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<?> queryForListByNamedHql2(String hql, Map<String, String> param, Class<? extends Object> cla_){
		Query<?> query = getCurrentSession().getNamedQuery(hql);
		for(String key : param.keySet()){
			query.setParameter(key, param.get(key));
		}
		List<?> result = query.list();
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public <K> List<K>  queryForListByHql(String hql, Class<K> cla_) {
		Query<K> query = getCurrentSession().createQuery(hql, cla_);
		return query.list();
	}
	
	public List<?> queryForListByNamedSql(String sql, Map<String, String> params, Class<? extends Object> cla_){
		Query<?> query = getCurrentSession().getNamedQuery(sql);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
		List<?> result = query.list();
		return result;
	}
	
	public <T> void test(T... args) {
		for(T t: args) {
			System.out.println(t.getClass());
		}
	}
	
	interface CC<L>{
		public L getString();
	}
	
	class DD<T> implements CC<T>{

		private T x;
		@Override
		public T getString() {
			return null;
		}
		
	}
}
