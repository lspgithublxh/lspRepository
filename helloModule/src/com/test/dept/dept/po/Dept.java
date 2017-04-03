package com.test.dept.dept.po;
//导入 java 类
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Dept的POJO类
 *
 * @author  Administrator  [Tue Mar 28 10:18:23 CST 2017]
 * 
 */
public class Dept implements Serializable{

    /** 
     * 属性id
     */
    private String id;
	
    /** 
     * 属性name
     */
    private String name;
	
    /** 
     * 属性periodOfValidity
     */
    private Integer periodOfValidity = 10;
	
    /** 
     * 属性color
     */
    private String color;
	
    /**
     * Dept构造函数
     */
    public Dept() {
        super();
    }  
	
    /**
     * Dept完整的构造函数
     */  
    public Dept(String id){
        this.id = id;
    }
 
    /**
     * 属性 id 的get方法
     * @return String
     */
    public String getId(){
        return id;
    }
	
    /**
     * 属性 id 的set方法
     * @return
     */
    public void setId(String id){
        if(id != null && id.trim().length() == 0){
            this.id = null;
        }else{
            this.id = id;
        }
    } 
	
    /**
     * 属性 name 的get方法
     * @return String
     */
    public String getName(){
        return name;
    }
	
    /**
     * 属性 name 的set方法
     * @return
     */
    public void setName(String name){
        this.name = name;
    } 
	
    /**
     * 属性 periodOfValidity 的get方法
     * @return Integer
     */
    public Integer getPeriodOfValidity(){
        return periodOfValidity;
    }
	
    /**
     * 属性 periodOfValidity 的set方法
     * @return
     */
    public void setPeriodOfValidity(Integer periodOfValidity){
        this.periodOfValidity = periodOfValidity;
    } 
	
    /**
     * 属性 color 的get方法
     * @return String
     */
    public String getColor(){
        return color;
    }
	
    /**
     * 属性 color 的set方法
     * @return
     */
    public void setColor(String color){
        this.color = color;
    } 
	
    /**
     * Hibernate通过该方法判断对象是否相等
     * @return boolean
     */  
    public boolean equals(Object o) {
        if (this == o)
			return true;
		
        if (o == null || !(o instanceof Dept))
	        return false; 
			
        if(getId() == null) 
	        return false;

        Dept other = (Dept) o;	        
	    return new EqualsBuilder()
            .append(this.getId(), other.getId())
			.isEquals();
    } 
     
    /**
     * toString方法
     * @return String
     */
	public String toString(){

		  return new StringBuffer()
            .append("id"+":"+getId())
            .append("name"+":"+getName())
            .append("periodOfValidity"+":"+getPeriodOfValidity())
            .append("color"+":"+getColor())
            .toString(); 
			
    } 
   

    /**
     * hashcode方法
     * @return int
     * 
     */
    @Override
    public int hashCode(){
		return super.hashCode();
	}

}