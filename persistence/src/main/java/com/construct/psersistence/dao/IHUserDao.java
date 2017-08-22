package com.construct.psersistence.dao;

import java.io.Serializable;

import com.construct.persistence.common.DomainRepository;
import com.construct.persistence.entity.UserBean;

public interface IHUserDao extends DomainRepository<UserBean, Integer>{
	public <T> T findUniqueByProperty(Class<T> entityClass,final String propertyName,final Object value);   

}
