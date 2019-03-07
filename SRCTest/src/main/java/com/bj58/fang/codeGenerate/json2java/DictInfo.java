package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class DictInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	name;
	private String	rank;
	private String	id;
	private String	city_id;

	public String getCity_id(){
 		return this.city_id;
	}

	public void setCity_id( String city_id){
 		this.city_id=city_id;
	}

	public String getId(){
 		return this.id;
	}

	public void setId( String id){
 		this.id=id;
	}

	public String getName(){
 		return this.name;
	}

	public void setName( String name){
 		this.name=name;
	}

	public String getRank(){
 		return this.rank;
	}

	public void setRank( String rank){
 		this.rank=rank;
	}

}
