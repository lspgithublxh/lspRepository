package com.ltd.e.recommend.config.Main.service;

import com.ltd.e.recommend.config.Main.model.RecommendConfig;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月25日
 * @file ConfigService
 */
public interface ConfigService {

	RecommendConfig queryRcByExamType(short examTypeId);
	
	boolean saveRecommendConfig(RecommendConfig config);
}
