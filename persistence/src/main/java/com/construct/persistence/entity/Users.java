package com.construct.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name="login_users")  
public class Users {  
   
    private int id;  
      
    private String username;  
      
    private String password;  
      
    private String roles = "ROLE_ADMIN";  
  
    @Id  
    @GeneratedValue(strategy=GenerationType.AUTO)  
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public String getRoles() {  
        return roles;  
    }  
  
    public void setRoles(String roles) {  
        this.roles = roles;  
    }  
      
      
      
}  
