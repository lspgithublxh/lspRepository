package com.ltd.e.recommend.config.Main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltd.e.recommend.config.Main.model.RecommendConfig;
import com.ltd.e.recommend.config.Main.repository.RecommendConfigRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月25日
 * @file ConfigServiceImpl
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl implements ConfigService{
	
	@Autowired
	private RecommendConfigRepository rcRepository;
	
	@Override
	public RecommendConfig queryRcByExamType(short examTypeId) {
		RecommendConfig rc = rcRepository.queryRcByExamType(examTypeId);
		return rc;
	}

	@Override
	public boolean saveRecommendConfig(RecommendConfig config) {
		config.setConfigVersion((short)(config.getConfigVersion() + (short)1));
		rcRepository.saveAndFlush(config);
		return true;
	}
	
}
