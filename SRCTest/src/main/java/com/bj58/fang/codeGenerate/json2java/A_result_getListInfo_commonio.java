package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
public class A_result_getListInfo_commonio implements Serializable{
	private static final long serialVersionUID = 1L;
	private A_result_getListInfo_commonio_filter	filter;

	public A_result_getListInfo_commonio_filter getFilter(){
 		return this.filter;
	}

	public void setFilter( A_result_getListInfo_commonio_filter filter){
 		this.filter=filter;
	}

}
