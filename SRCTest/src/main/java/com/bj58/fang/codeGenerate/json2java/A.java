package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
public class A implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long	code;
	private A_detail	detail;
	private String	message;
	private String	status;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public A_detail getDetail(){
 		return this.detail;
	}

	public void setDetail( A_detail detail){
 		this.detail=detail;
	}

	public String getMessage(){
 		return this.message;
	}

	public void setMessage( String message){
 		this.message=message;
	}

	public String getStatus(){
 		return this.status;
	}

	public void setStatus( String status){
 		this.status=status;
	}

}
