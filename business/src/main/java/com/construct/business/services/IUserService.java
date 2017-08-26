package com.construct.business.services;

import java.util.List;

import com.construct.persistence.entity.UserBean;
import com.construct.persistence.entity.UserRoles;

public interface IUserService {

	List<UserBean> queryUsers();
	
	List<UserRoles> queryUserRoles();
}
