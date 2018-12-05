package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
public class A implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	msg;
	private A_result	result;
	private Long	status;

	public String getMsg(){
 		return this.msg;
	}

	public void setMsg( String msg){
 		this.msg=msg;
	}

	public A_result getResult(){
 		return this.result;
	}

	public void setResult( A_result result){
 		this.result=result;
	}

	public Long getStatus(){
 		return this.status;
	}

	public void setStatus( Long status){
 		this.status=status;
	}

}
