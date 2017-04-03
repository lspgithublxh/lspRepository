package com.test.dept.dept.vo;

import java.io.Serializable;
import com.sgcc.uap.rest.annotation.attribute.EditorType;
import com.sgcc.uap.rest.annotation.attribute.AttributeType;
import com.sgcc.uap.rest.annotation.attribute.ViewAttribute;
import com.sgcc.uap.rest.support.ParentVO;

/**
 * Dept的VO类
 *
 * @author  Administrator  [Tue Mar 28 10:18:23 CST 2017]
 * 
 */
public class DeptVO extends ParentVO implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 
     * 属性id
     */
	@ViewAttribute(name = "id", caption="车辆标识", length =32, editor=EditorType.TextEditor,  nullable=false, readOnly=false,  type=AttributeType.STRING) 
	private String id;
    /** 
     * 属性name
     */
	@ViewAttribute(name = "name", caption="车辆名称", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String name;
    /** 
     * 属性periodOfValidity
     */
	@ViewAttribute(name = "periodOfValidity", caption="有效期", length =32, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.INTEGER) 
	private Integer periodOfValidity;
    /** 
     * 属性color
     */
	@ViewAttribute(name = "color", caption="车辆颜色", length =255, editor=EditorType.TextEditor,  nullable=true, readOnly=false,  type=AttributeType.STRING) 
	private String color;
    /**
     * DeptVO构造函数
     */
    public DeptVO() {
        super();
    }  
	
    /**
     * DeptVO完整的构造函数
     */  
    public DeptVO(String id){
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
   


}