package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;
public class RentSearchResultDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<String>	search_debug;
	private Long	code;
	private RentSearchResultDataDto	data;
	private String	message;

	public Long getCode(){
 		return this.code;
	}

	public void setCode( Long code){
 		this.code=code;
	}

	public RentSearchResultDataDto getData(){
 		return this.data;
	}

	public void setData( RentSearchResultDataDto data){
 		this.data=data;
	}

	public String getMessage(){
 		return this.message;
	}

	public void setMessage( String message){
 		this.message=message;
	}

	public List<String> getSearch_debug() {
		return search_debug;
	}

	public void setSearch_debug(List<String> search_debug) {
		this.search_debug = search_debug;
	}

}
