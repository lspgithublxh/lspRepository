package com.bj58.fang.codeGenerate.json2java;
import java.io.Serializable;
import java.lang.Long;
import java.util.Map;
public class RentSearchResultFacet implements Serializable{
	private static final long serialVersionUID = 1L;
	private Map<String, String>	use_type;
	private Map<String, String>	fitment_ids;
	private Map<String, String>	broker_ids;
	private Map<String, String>	room_num_range;
	private Long[]	real_store_ids;
	private Map<String, String>	community_ids;
	private Map<String, String>	region_id;
	private Map<String, String>	price_range;
	private Map<String, String>	metro_ids;
	private Map<String, String>	block_id;
	private Map<String, String>	metro_station_ids;
	private Map<String, String>	functional_area_ids;
	public Map<String, String> getUse_type() {
		return use_type;
	}
	public void setUse_type(Map<String, String> use_type) {
		this.use_type = use_type;
	}
	public Map<String, String> getFitment_ids() {
		return fitment_ids;
	}
	public void setFitment_ids(Map<String, String> fitment_ids) {
		this.fitment_ids = fitment_ids;
	}
	public Map<String, String> getBroker_ids() {
		return broker_ids;
	}
	public void setBroker_ids(Map<String, String> broker_ids) {
		this.broker_ids = broker_ids;
	}
	public Map<String, String> getRoom_num_range() {
		return room_num_range;
	}
	public void setRoom_num_range(Map<String, String> room_num_range) {
		this.room_num_range = room_num_range;
	}
	public Long[] getReal_store_ids() {
		return real_store_ids;
	}
	public void setReal_store_ids(Long[] real_store_ids) {
		this.real_store_ids = real_store_ids;
	}
	public Map<String, String> getCommunity_ids() {
		return community_ids;
	}
	public void setCommunity_ids(Map<String, String> community_ids) {
		this.community_ids = community_ids;
	}
	public Map<String, String> getRegion_id() {
		return region_id;
	}
	public void setRegion_id(Map<String, String> region_id) {
		this.region_id = region_id;
	}
	public Map<String, String> getPrice_range() {
		return price_range;
	}
	public void setPrice_range(Map<String, String> price_range) {
		this.price_range = price_range;
	}
	public Map<String, String> getMetro_ids() {
		return metro_ids;
	}
	public void setMetro_ids(Map<String, String> metro_ids) {
		this.metro_ids = metro_ids;
	}
	public Map<String, String> getBlock_id() {
		return block_id;
	}
	public void setBlock_id(Map<String, String> block_id) {
		this.block_id = block_id;
	}
	public Map<String, String> getMetro_station_ids() {
		return metro_station_ids;
	}
	public void setMetro_station_ids(Map<String, String> metro_station_ids) {
		this.metro_station_ids = metro_station_ids;
	}
	public Map<String, String> getFunctional_area_ids() {
		return functional_area_ids;
	}
	public void setFunctional_area_ids(Map<String, String> functional_area_ids) {
		this.functional_area_ids = functional_area_ids;
	}


}
