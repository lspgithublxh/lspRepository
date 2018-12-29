package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
public class RentSearchResultCateGoryList implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long	category_value_type;
	private Long	category_type;
	private Long[]	category_value;

	public Long getCategory_type(){
 		return this.category_type;
	}

	public void setCategory_type( Long category_type){
 		this.category_type=category_type;
	}

	public Long[] getCategory_value(){
 		return this.category_value;
	}

	public void setCategory_value( Long[] category_value){
 		this.category_value=category_value;
	}

	public Long getCategory_value_type(){
 		return this.category_value_type;
	}

	public void setCategory_value_type( Long category_value_type){
 		this.category_value_type=category_value_type;
	}

}