package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class A_result_getListInfo_sidDict implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	PGTID;
	private String	GTID;

	public String getGTID(){
 		return this.GTID;
	}

	public void setGTID( String GTID){
 		this.GTID=GTID;
	}

	public String getPGTID(){
 		return this.PGTID;
	}

	public void setPGTID( String PGTID){
 		this.PGTID=PGTID;
	}

}
