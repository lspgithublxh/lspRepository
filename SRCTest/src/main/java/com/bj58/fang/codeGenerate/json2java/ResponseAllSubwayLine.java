package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class ResponseAllSubwayLine implements Serializable{
	private static final long serialVersionUID = 1L;
	private int	code;
	private SubwayEntity[]	data;
	private String	message;

	public int getCode(){
 		return this.code;
	}

	public void setCode( int code){
 		this.code=code;
	}

	public SubwayEntity[] getData() {
		return data;
	}

	public void setData(SubwayEntity[] data) {
		this.data = data;
	}

	public String getMessage(){
 		return this.message;
	}

	public void setMessage( String message){
 		this.message=message;
	}

}
