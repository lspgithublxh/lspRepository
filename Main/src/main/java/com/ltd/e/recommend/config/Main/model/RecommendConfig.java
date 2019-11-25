package com.ltd.e.recommend.config.Main.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TODO 
 * @author lishaoping
 * @date 2019年11月20日
 * @file RecommendConfig
 */
@Data
@Accessors(chain = true)
@Entity(name="recommend_config")
public class RecommendConfig {

	@Id
	@Column(name="exam_type_id", nullable=false)
	private short examTypeId;
	
	@Column(name="priority_algorithm_param2", nullable=false)
	private double priorityAlgorithmParam2;
	
	@Column(name="priority_algorithm_param1", nullable=false)
	private double priorityAlgorithmParam1;
	
	@Column(name="composite_topn", nullable=false)
	private short compositeTopn;
	
	@Column(name="top_kp_request_topn", nullable=false)
	private short topKpRequestTopn;
	
	@Column(name="composite_complex_total_max", nullable=false)
	private double compositeComplexTotalMax;
	
	@Column(name="top_kp_complex_total_max", nullable=false)
	private double topKpComplexTotalMax;
	
	@Column(name="knowledge_lvl_max", nullable=false)
	private short knowledgeLvlMax;
	
	@Column(name="init_kp_grasp_val", nullable=false)
	private double initKpGraspVal;
	
	@Column(name="kp_similar_thredshold", nullable=false)
	private volatile double kpSimilarThredshold;
	
	@Column(name="kp_lvl_reduce_radio", nullable=false)
	private volatile double kpLvlReduceRadio;
	
	@Column(name="kp_priority_min", nullable=false)
	private volatile double kpPriorityMin;
	
	@Column(name="comlex_normalize_param", nullable=false)
	private double comlexNormalizeParam;
	
	@Column(name="importance_normalize_param", nullable=false)
	private int importanceNormalizeParam;
	
	@Column(name="kp_update_step", nullable=false)
	private double kpUpdateStep;
	
	@Column(name="kp_update_practise", nullable=false)
	private volatile double kpUpdatePractise;
	
	@Column(name="kp_update_learn", nullable=false)
	private double kpUpdateLearn;
	
	@Column(name="kp_update_forget", nullable=false)
	private double kpUpdateForget;
	
	@Column(name="config_version", nullable=false)
	private short configVersion;
	
	@Column(name="add_time", nullable=false)
	private Timestamp addTime;
}
