package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
public class A_result implements Serializable{
	private static final long serialVersionUID = 1L;
	private A_result_getListInfo	getListInfo;

	public A_result_getListInfo getGetListInfo(){
 		return this.getListInfo;
	}

	public void setGetListInfo( A_result_getListInfo getListInfo){
 		this.getListInfo=getListInfo;
	}

}
