package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class A_result_getListInfo_infolist_detailaction implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	tradeline;
	private String	action;
	private A_result_getListInfo_infolist_detailaction_content	content;

	public String getAction(){
 		return this.action;
	}

	public void setAction( String action){
 		this.action=action;
	}

	public A_result_getListInfo_infolist_detailaction_content getContent(){
 		return this.content;
	}

	public void setContent( A_result_getListInfo_infolist_detailaction_content content){
 		this.content=content;
	}

	public String getTradeline(){
 		return this.tradeline;
	}

	public void setTradeline( String tradeline){
 		this.tradeline=tradeline;
	}

}