package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
public class RentSearchResultPropList implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	ab_test_flow_id;
	private Long	prop_id;
	private Long	source_type;
	private Long	prop_type;
	private Long	city_id;

	public String getAb_test_flow_id(){
 		return this.ab_test_flow_id;
	}

	public void setAb_test_flow_id( String ab_test_flow_id){
 		this.ab_test_flow_id=ab_test_flow_id;
	}

	public Long getCity_id(){
 		return this.city_id;
	}

	public void setCity_id( Long city_id){
 		this.city_id=city_id;
	}

	public Long getProp_id(){
 		return this.prop_id;
	}

	public void setProp_id( Long prop_id){
 		this.prop_id=prop_id;
	}

	public Long getProp_type(){
 		return this.prop_type;
	}

	public void setProp_type( Long prop_type){
 		this.prop_type=prop_type;
	}

	public Long getSource_type(){
 		return this.source_type;
	}

	public void setSource_type( Long source_type){
 		this.source_type=source_type;
	}

}