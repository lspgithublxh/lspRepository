package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
public class A_detail_dispatchResults implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	msg;
	private Long	infoid;
	private Long	code;
	private Long	infoState;
	private Long	unitedHouseId;
	private Long	company;
	private String	url;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public Long getCompany(){
 		return this.company;
	}

	public void setCompany( Long company){
 		this.company=company;
	}

	public Long getInfoState(){
 		return this.infoState;
	}

	public void setInfoState( Long infoState){
 		this.infoState=infoState;
	}

	public Long getInfoid(){
 		return this.infoid;
	}

	public void setInfoid( Long infoid){
 		this.infoid=infoid;
	}

	public String getMsg(){
 		return this.msg;
	}

	public void setMsg( String msg){
 		this.msg=msg;
	}

	public Long getUnitedHouseId(){
 		return this.unitedHouseId;
	}

	public void setUnitedHouseId( Long unitedHouseId){
 		this.unitedHouseId=unitedHouseId;
	}

	public String getUrl(){
 		return this.url;
	}

	public void setUrl( String url){
 		this.url=url;
	}

}