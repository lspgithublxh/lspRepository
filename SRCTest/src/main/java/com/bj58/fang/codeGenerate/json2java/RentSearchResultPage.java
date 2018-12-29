package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
public class RentSearchResultPage implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long	auction_count;
	private Long	total;
	private Long	page;
	private Long	rows;
	private Long	pricing_count;

	public Long getAuction_count(){
 		return this.auction_count;
	}

	public void setAuction_count( Long auction_count){
 		this.auction_count=auction_count;
	}

	public Long getPage(){
 		return this.page;
	}

	public void setPage( Long page){
 		this.page=page;
	}

	public Long getPricing_count(){
 		return this.pricing_count;
	}

	public void setPricing_count( Long pricing_count){
 		this.pricing_count=pricing_count;
	}

	public Long getRows(){
 		return this.rows;
	}

	public void setRows( Long rows){
 		this.rows=rows;
	}

	public Long getTotal(){
 		return this.total;
	}

	public void setTotal( Long total){
 		this.total=total;
	}

}
