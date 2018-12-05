package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
public class A_result_getListInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private A_result_getListInfo_metaUpdate	metaUpdate;
	private String	baseQuery;
	private String	pubTitle;
	private Long	searchNum;
	private Boolean	lastPage;
	private Long	pageIndex;
	private Long	pageSize;
	private A_result_getListInfo_infolist[]	infolist;
	private A_result_getListInfo_commonio	commonio;
	private String	pubUrl;
	private String	showLog;
	private A_result_getListInfo_sidDict	sidDict;

	public String getBaseQuery(){
 		return this.baseQuery;
	}

	public void setBaseQuery( String baseQuery){
 		this.baseQuery=baseQuery;
	}

	public A_result_getListInfo_commonio getCommonio(){
 		return this.commonio;
	}

	public void setCommonio( A_result_getListInfo_commonio commonio){
 		this.commonio=commonio;
	}

	public A_result_getListInfo_infolist[] getInfolist(){
 		return this.infolist;
	}

	public void setInfolist( A_result_getListInfo_infolist[] infolist){
 		this.infolist=infolist;
	}

	public Boolean getLastPage(){
 		return this.lastPage;
	}

	public void setLastPage( Boolean lastPage){
 		this.lastPage=lastPage;
	}

	public A_result_getListInfo_metaUpdate getMetaUpdate(){
 		return this.metaUpdate;
	}

	public void setMetaUpdate( A_result_getListInfo_metaUpdate metaUpdate){
 		this.metaUpdate=metaUpdate;
	}

	public Long getPageIndex(){
 		return this.pageIndex;
	}

	public void setPageIndex( Long pageIndex){
 		this.pageIndex=pageIndex;
	}

	public Long getPageSize(){
 		return this.pageSize;
	}

	public void setPageSize( Long pageSize){
 		this.pageSize=pageSize;
	}

	public String getPubTitle(){
 		return this.pubTitle;
	}

	public void setPubTitle( String pubTitle){
 		this.pubTitle=pubTitle;
	}

	public String getPubUrl(){
 		return this.pubUrl;
	}

	public void setPubUrl( String pubUrl){
 		this.pubUrl=pubUrl;
	}

	public Long getSearchNum(){
 		return this.searchNum;
	}

	public void setSearchNum( Long searchNum){
 		this.searchNum=searchNum;
	}

	public String getShowLog(){
 		return this.showLog;
	}

	public void setShowLog( String showLog){
 		this.showLog=showLog;
	}

	public A_result_getListInfo_sidDict getSidDict(){
 		return this.sidDict;
	}

	public void setSidDict( A_result_getListInfo_sidDict sidDict){
 		this.sidDict=sidDict;
	}

}
