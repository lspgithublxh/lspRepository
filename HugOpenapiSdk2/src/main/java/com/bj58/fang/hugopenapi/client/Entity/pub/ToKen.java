package com.bj58.fang.hugopenapi.client.Entity.pub;

import java.io.Serializable;

/**
 * 序列化到本地
 * @ClassName:ToKen
 * @Description:
 * @Author lishaoping
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.client.oldentity
 */
public class ToKen implements Serializable{

	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientSecret;
	private long deadline;
	private String token;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public ToKen(String clientId, String clientSecret, long deadline) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.deadline = deadline;
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "ToKen [clientId=" + clientId + ", clientSecret=" + clientSecret + ", deadline=" + deadline + ", token="
				+ token + "]";
	}

}
