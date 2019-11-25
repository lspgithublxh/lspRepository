package com.ltd.e.recommend.config.Main.service;

import com.ltd.e.recommend.config.Main.model.RecommendConfig;

public interface ConfigService {

	RecommendConfig queryRcByExamType(short examTypeId);
}
