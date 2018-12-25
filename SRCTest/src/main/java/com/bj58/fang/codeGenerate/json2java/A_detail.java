package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
public class A_detail implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	msg;
	private Long	code;
	private Long	houseState;
	private A_detail_dispatchResults[]	dispatchResults;
	private Long	unitedHouseId;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public A_detail_dispatchResults[] getDispatchResults(){
 		return this.dispatchResults;
	}

	public void setDispatchResults( A_detail_dispatchResults[] dispatchResults){
 		this.dispatchResults=dispatchResults;
	}

	public Long getHouseState(){
 		return this.houseState;
	}

	public void setHouseState( Long houseState){
 		this.houseState=houseState;
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

}
