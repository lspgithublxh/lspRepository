package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.util.Map;
public class A implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	code;
	private Map<String, DictInfo>	data;
	private String	message;

	public String getCode(){
 		return this.code;
	}

	public void setCode( String code){
 		this.code=code;
	}

	public Map<String, DictInfo> getData() {
		return data;
	}

	public void setData(Map<String, DictInfo> data) {
		this.data = data;
	}

	public String getMessage(){
 		return this.message;
	}

	public void setMessage( String message){
 		this.message=message;
	}

}
