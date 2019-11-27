package com.ltd.e.recommend.config.Main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ltd.e.recommend.config.Main.model.UserQuestionRecommend;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月18日
 * @file UserQuestionRecommendRepository
 */
@Repository
public interface UserQuestionRecommendRepository extends JpaRepository<UserQuestionRecommend, Long>{

	@Query(value="select recommend_no from user_recommend_no where user_id=?1 and exam_type_id =?2 limit 1", nativeQuery = true)
	Long queryRecommendNoByUserId(long userId, short examType);
	
	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query(value="insert into user_recommend_no(user_id, exam_type_id, recommend_no) values(?1, ?3, ?2) on duplicate key update recommend_no=?2", nativeQuery = true)
	void updateUserRecommendNo(long userId, long recommendNo, short examType);

	@Query(value="select distinct table_name from recommend_exam_tableName where exam_type_id=?1", nativeQuery = true)
	List<String> queryExamTableName(short examType);
	
	@Transactional(rollbackFor = Exception.class)
	@Modifying
	@Query(value="insert into recommend_exam_tableName(exam_type_id, table_name, create_time) values(?1, ?2, current_timestamp)", nativeQuery = true)
	void insertExamTableName(short examType, String tableName);

//	@Query(value="select from ", nativeQuery = true)
//	List<Object[]> queryKpPriority(long userId);
}
