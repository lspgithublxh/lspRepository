package com.construct.psersistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.construct.persistence.entity.UserBean;

@Repository
public class IHUserDaoImpl implements IHUserDao{

	@Autowired
    private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }

	@Override
	public UserBean load(Integer id) {
		return getCurrentSession().load(UserBean.class, id);
	}

	@Override
	public UserBean get(Integer id) {
		return getCurrentSession().get(UserBean.class, id);
	}

	@Override
	public List<UserBean> findAll() {
		Query<UserBean> query = getCurrentSession().createQuery("from UserBean", UserBean.class);
		return query.list();
	}

	@Override
	public void persist(UserBean entity) {
		getCurrentSession().persist(entity);
		
	}

	@Override
	public Integer save(UserBean entity) {
		// TODO Auto-generated method stub
		return (Integer) getCurrentSession().save(entity);
	}

	@Override
	public void saveOrUpdate(UserBean entity) {
		// TODO Auto-generated method stub
		getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(Integer id) {
		UserBean bean = load(id);
		getCurrentSession().delete(bean);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		getCurrentSession().flush();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

}
