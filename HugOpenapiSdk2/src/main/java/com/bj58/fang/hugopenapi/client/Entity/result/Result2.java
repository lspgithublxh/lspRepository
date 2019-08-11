package com.bj58.fang.hugopenapi.client.Entity.result;

import java.util.List;

public class Result2 {

	private Long	code;
	private List<Detail2>	detail;
	private String	message;
	private String	status;
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public List<Detail2> getDetail() {
		return detail;
	}
	public void setDetail(List<Detail2> detail) {
		this.detail = detail;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
