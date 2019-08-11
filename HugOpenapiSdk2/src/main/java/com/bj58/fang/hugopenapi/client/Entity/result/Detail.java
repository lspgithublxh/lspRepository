package com.bj58.fang.hugopenapi.client.Entity.result;

import java.util.List;

public class Detail{

	private String	msg;
	private Long	code;
	private Long	houseState;
	private List<DispatchResults>	dispatchResults;
	private Long	unitedHouseId;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public List<DispatchResults> getDispatchResults() {
		return dispatchResults;
	}

	public void setDispatchResults(List<DispatchResults> dispatchResults) {
		this.dispatchResults = dispatchResults;
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
