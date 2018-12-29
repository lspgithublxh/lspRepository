package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.String;
public class RentSearchResultDataDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	ab_test_flow_id;
	private RentSearchResultCateGoryList[]	category_list;
	private RentSearchResultPage	page;
	private RentSearchResultPropList[]	prop_list;
	private RentSearchResultFacet	facet;
	private String	search_type;

	public String getAb_test_flow_id(){
 		return this.ab_test_flow_id;
	}

	public void setAb_test_flow_id( String ab_test_flow_id){
 		this.ab_test_flow_id=ab_test_flow_id;
	}

	public RentSearchResultCateGoryList[] getCategory_list(){
 		return this.category_list;
	}

	public void setCategory_list( RentSearchResultCateGoryList[] category_list){
 		this.category_list=category_list;
	}

	public RentSearchResultFacet getFacet(){
 		return this.facet;
	}

	public void setFacet( RentSearchResultFacet facet){
 		this.facet=facet;
	}

	public RentSearchResultPage getPage(){
 		return this.page;
	}

	public void setPage( RentSearchResultPage page){
 		this.page=page;
	}

	public RentSearchResultPropList[] getProp_list(){
 		return this.prop_list;
	}

	public void setProp_list( RentSearchResultPropList[] prop_list){
 		this.prop_list=prop_list;
	}

	public String getSearch_type(){
 		return this.search_type;
	}

	public void setSearch_type( String search_type){
 		this.search_type=search_type;
	}

}
