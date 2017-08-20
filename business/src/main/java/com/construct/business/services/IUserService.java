package com.construct.business.services;

import java.util.List;

import com.construct.persistence.entity.UserBean;

public interface IUserService {

	List<UserBean> queryUsers();
}
