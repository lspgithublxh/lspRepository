package com.construct.persistence.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Repository("hibernateDao")
public class HibernateDao implements IHibernateDao{

	@Autowired
	private SessionFactory sessionFactory; 
		
	private static ConcurrentHashMap<String, Template> sqlMap = new ConcurrentHashMap<String,Template>();
	
	
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
	public <K> List<K>  queryForListByHql(String hql, Class<K> cla_) {
		Query<K> query = getCurrentSession().createQuery(hql, cla_);
		return query.list();
	}
	
	public <T> List<T> queryForListByNamedQuery(String sql, Map<String, String> params, Class<T> cla_){
//		Query<T> query2 = getCurrentSession().createNamedQuery(sql);
		Query<T> query = getCurrentSession().getNamedQuery(sql);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
//			query2.setParameter(key, params.get(key));
		}
		List<T> result = query.list();
//		List<T> result2 = query2.list();
		return result;
	}
	
	public <T> List<T> queryForListByNamedSqlOrHql(String sql, Map<String, String> params){
		Query<T> query = getCurrentSession().getNamedQuery(sql);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
		List<T> result = query.list();
		return result;
	}
	
	@Override
	public <T> List<T> queryForListByNamedQueryUsingFreemarker(String sql, Map<String, String> params, Class<T> cla_) {
		Session session = getCurrentSession();
		Query<T> query = session.getNamedQuery(sql);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
		String queryString = query.getQueryString();
		System.out.println(queryString);
		List<T> result = null;
		//1.开始用freemarker来处理
		StringTemplateLoader loader = new StringTemplateLoader();
		loader.putTemplate(sql, queryString);
		Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
		freeMarkerConfig.setTemplateLoader(loader);
		 //2.生成freemarker模板
		try {
			Template template = freeMarkerConfig.getTemplate(sql,"utf-8");
			StringWriter stringWriter = new StringWriter();
	        template.process(params, stringWriter);
	        String sql_rs = stringWriter.toString();
	        //3.再次使用session进行查询
	        query = session.createNativeQuery(sql_rs, cla_);//session.createSQLQuery(sql_rs);
	        for(String key : params.keySet()){
				query.setParameter(key, params.get(key));
			}
	        result = query.list();
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
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

	@Override
	public List<?> queryForListByNamedSqlOrHql(String sql, Map<String, String> params, Class<? extends Object> cla_) {
		Query<?> query = getCurrentSession().getNamedQuery(sql);
		for(String key : params.keySet()){
			query.setParameter(key, params.get(key));
		}
		List<?> result = query.list();
		return result;
	}


}
