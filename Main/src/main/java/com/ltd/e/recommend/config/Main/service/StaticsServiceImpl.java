package com.ltd.e.recommend.config.Main.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltd.e.recommend.config.Main.consts.RecommendConsts;
import com.ltd.e.recommend.config.Main.enums.ExamType;
import com.ltd.e.recommend.config.Main.model.StaticsItem;
import com.ltd.e.recommend.config.Main.repository.UserQuestionRecommendRepository;


/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月27日
 * @file StaticsServiceImpl
 */
@Service
public class StaticsServiceImpl implements StaticsService{

	@Autowired
	private UserQuestionRecommendRepository userQuestionRecommendRepository;
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public List<StaticsItem> getPriority(long userId, short examTypeId) {
		StringBuilder kpTable = new StringBuilder(RecommendConsts.USER_KNOWLEDGE_TABLE_PREFIX);
		kpTable.append(RecommendConsts.EXAM_SPLITOR).append(ExamType.getVal(examTypeId).name());
		String sql = RecommendConsts.QUERY_USER_KNOWLEGE_PRIORITY_LIST.replace("%tableName%", kpTable.toString());
		Query query = entityManager.createNativeQuery(sql);//, StaticsItem.class
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<Object> kpIdList = query.getResultList();//StaticsItem
		List<StaticsItem> collect = kpIdList.stream().map(obj -> new StaticsItem().setItemId(((BigInteger)((Object[])obj)[0]).longValue())
				.setValue(((Double)((Object[])obj)[1]).doubleValue())).collect(Collectors.toList());
		System.out.println(collect);
		return collect;
	}

}
