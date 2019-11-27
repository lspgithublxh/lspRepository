package com.ltd.e.recommend.config.Main.consts;
/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月15日
 * @file RecommendConsts
 */
public class RecommendConsts {

	public final static String USER_KNOWLEDGE_TABLE_PREFIX = "user_knowlege_complete";
	public final static String USER_QUESTION_TABLE_PREFIX = "recommend_user_question";
	public final static String EXAM_SPLITOR = "_";
	public final static String EXAM_SUBJECT_CONDITION = " and exam_subject_id=:examSubjectId ";
	
	public final static String QUERY_USER_KNOWLEDGE_TOPN = "SELECT DISTINCT knowledge_point_lvl3_id FROM %tableName% WHERE  user_id=:userId %examSubject% ORDER BY priority desc limit 0,:limit";
	public final static String QUERY_USER_SOME_KNOWLEDGE_TOPN = "SELECT DISTINCT knowledge_point_lvl3_id FROM %tableName% WHERE  user_id=:userId %examSubject% and knowledge_point_lvl1_id in(:topKpId) and priority>:priority ORDER BY priority desc limit 1";
	public final static String QUERY_USER_KNOWLEDGE_PRIORIY = "SELECT DISTINCT knowledge_point_lvl3_id FROM %tableName% WHERE  user_id=:userId and knowledge_point_lvl3_id in (:kpIds) and priority >:priority order by priority desc";

	
	public final static String QUERY_USER_QUESTION_RECOMMEND_TIMES = "SELECT * FROM %tableName% WHERE user_id=:userId AND question_id IN (:qIds)";

	public final static String QUERY_USER_KNOWLEGE_PRIORITY_LIST = "SELECT knowledge_point_lvl3_id as itemId, priority as value FROM %tableName% where user_id=:userId order by priority desc";

}
