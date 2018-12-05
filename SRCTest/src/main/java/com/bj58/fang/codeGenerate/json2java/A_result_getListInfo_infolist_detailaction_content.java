package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
public class A_result_getListInfo_infolist_detailaction_content implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	pagetype;
	private String	infoLog;
	private Boolean	is_supportLive;
	private Long	infoID;
	private Boolean	use_cache;
	private String	list_name;
	private Boolean	isWorryFree;
	private String	title;
	private String	local_name;
	private String	userID;
	private String	infoSource;
	private A_result_getListInfo_infolist_detailaction_content_commondata	commondata;
	private String	detailLog;
	private Boolean	isPanoramic;
	private String	action;
	private Boolean	recomInfo;
	private String	countType;
	private String	full_path;
	private String	data_url;

	public String getAction(){
 		return this.action;
	}

	public void setAction( String action){
 		this.action=action;
	}

	public A_result_getListInfo_infolist_detailaction_content_commondata getCommondata(){
 		return this.commondata;
	}

	public void setCommondata( A_result_getListInfo_infolist_detailaction_content_commondata commondata){
 		this.commondata=commondata;
	}

	public String getCountType(){
 		return this.countType;
	}

	public void setCountType( String countType){
 		this.countType=countType;
	}

	public String getData_url(){
 		return this.data_url;
	}

	public void setData_url( String data_url){
 		this.data_url=data_url;
	}

	public String getDetailLog(){
 		return this.detailLog;
	}

	public void setDetailLog( String detailLog){
 		this.detailLog=detailLog;
	}

	public String getFull_path(){
 		return this.full_path;
	}

	public void setFull_path( String full_path){
 		this.full_path=full_path;
	}

	public Long getInfoID(){
 		return this.infoID;
	}

	public void setInfoID( Long infoID){
 		this.infoID=infoID;
	}

	public String getInfoLog(){
 		return this.infoLog;
	}

	public void setInfoLog( String infoLog){
 		this.infoLog=infoLog;
	}

	public String getInfoSource(){
 		return this.infoSource;
	}

	public void setInfoSource( String infoSource){
 		this.infoSource=infoSource;
	}

	public Boolean getIsPanoramic(){
 		return this.isPanoramic;
	}

	public void setIsPanoramic( Boolean isPanoramic){
 		this.isPanoramic=isPanoramic;
	}

	public Boolean getIsWorryFree(){
 		return this.isWorryFree;
	}

	public void setIsWorryFree( Boolean isWorryFree){
 		this.isWorryFree=isWorryFree;
	}

	public Boolean getIs_supportLive(){
 		return this.is_supportLive;
	}

	public void setIs_supportLive( Boolean is_supportLive){
 		this.is_supportLive=is_supportLive;
	}

	public String getList_name(){
 		return this.list_name;
	}

	public void setList_name( String list_name){
 		this.list_name=list_name;
	}

	public String getLocal_name(){
 		return this.local_name;
	}

	public void setLocal_name( String local_name){
 		this.local_name=local_name;
	}

	public String getPagetype(){
 		return this.pagetype;
	}

	public void setPagetype( String pagetype){
 		this.pagetype=pagetype;
	}

	public Boolean getRecomInfo(){
 		return this.recomInfo;
	}

	public void setRecomInfo( Boolean recomInfo){
 		this.recomInfo=recomInfo;
	}

	public String getTitle(){
 		return this.title;
	}

	public void setTitle( String title){
 		this.title=title;
	}

	public Boolean getUse_cache(){
 		return this.use_cache;
	}

	public void setUse_cache( Boolean use_cache){
 		this.use_cache=use_cache;
	}

	public String getUserID(){
 		return this.userID;
	}

	public void setUserID( String userID){
 		this.userID=userID;
	}

}