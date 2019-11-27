package com.ltd.e.recommend.config.Main.service;

import java.util.List;

import com.ltd.e.recommend.config.Main.model.StaticsItem;

public interface StaticsService {

	public List<StaticsItem> getPriority(long userId, short examTypeId);
	
	public List<StaticsItem> getRecommendTimes(long userId, short examTypeId);
}
