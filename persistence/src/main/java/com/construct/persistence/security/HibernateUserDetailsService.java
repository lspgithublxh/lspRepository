package com.construct.persistence.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.construct.persistence.dao.IHUserDao;
import com.construct.persistence.entity.Users;

//@Service("hibernateUserDetailsService")  
public class HibernateUserDetailsService implements UserDetailsService {  
  
private IHUserDao baseCollectionDao;  
      
//    @Resource(name="baseCollectionDao")  
    public void setBaseCollectionDao(IHUserDao baseCollectionDao)  
    {  
        this.baseCollectionDao = baseCollectionDao;  
    }  
  
    @Transactional  
    public UserDetails loadUserByUsername(String username)  
            throws UsernameNotFoundException  
    {  
    	Users users = baseCollectionDao.findUniqueByProperty(Users.class, "username", username);  
          
        if(users == null)   
            throw new UsernameNotFoundException("用户" + username + " 不存在!!!");  
          
        String[] roles = users.getRoles().split(",");  
          
          
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();  
          
        for(String role : roles)  
        {  
            authorities.add(new SimpleGrantedAuthority(role));  
        }  
          
          
        return new User(users.getUsername(), users.getPassword(), authorities);  
    }  
}  
