package com.ltd.e.recommend.config.Main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.e.recommend.config.Main.model.RecommendConfig;
import com.ltd.e.recommend.config.Main.repository.RecommendConfigRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService{
	
	@Autowired
	private RecommendConfigRepository rcRepository;
	
	@Override
	public RecommendConfig queryRcByExamType(short examTypeId) {
		RecommendConfig rc = rcRepository.queryRcByExamType(examTypeId);
		return rc;
	}
	
}
