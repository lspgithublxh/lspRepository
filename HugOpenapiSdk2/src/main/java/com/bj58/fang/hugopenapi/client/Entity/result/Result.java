package com.bj58.fang.hugopenapi.client.Entity.result;

public class Result{
	private Long	code;
	private Detail	detail;
	private String	message;
	private String	status;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
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
