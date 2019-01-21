package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class SubwayEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private long	sort_num;
	private String	name;
	private long	id;
	private int	status;
	private long	city_id;

	public long getCity_id(){
 		return this.city_id;
	}

	public void setCity_id( long city_id){
 		this.city_id=city_id;
	}

	public long getId(){
 		return this.id;
	}

	public void setId( long id){
 		this.id=id;
	}

	public String getName(){
 		return this.name;
	}

	public void setName( String name){
 		this.name=name;
	}

	public long getSort_num(){
 		return this.sort_num;
	}

	public void setSort_num( long sort_num){
 		this.sort_num=sort_num;
	}

	public long getStatus(){
 		return this.status;
	}

	public void setStatus( int status){
 		this.status=status;
	}

}