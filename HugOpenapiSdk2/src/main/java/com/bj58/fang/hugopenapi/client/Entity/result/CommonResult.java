package com.bj58.fang.hugopenapi.client.Entity.result;

public class CommonResult {
	
	private int	code;
	private String message;
	private String status;
	private String detail;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	@Override
	public String toString() {
		return "CommonResult [code=" + code + ", message=" + message + ", status=" + status + ", detail=" + detail
				+ "]";
	}
	
}
