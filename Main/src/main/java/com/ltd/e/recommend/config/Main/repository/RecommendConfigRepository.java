package com.ltd.e.recommend.config.Main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ltd.e.recommend.config.Main.model.RecommendConfig;
/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月18日
 * @file UserQuestionRecommendRepository
 */
@Repository
public interface RecommendConfigRepository extends JpaRepository<RecommendConfig, Long>{

	@Query(value="SELECT * FROM recommend_config WHERE (exam_type_id, config_version)= (SELECT exam_type_id, MAX(config_version) FROM recommend_config GROUP BY exam_type_id )", nativeQuery = true)
	List<RecommendConfig> queryAllRecommendConfig();
	
	@Query(value="SELECT * FROM recommend_config WHERE (exam_type_id, config_version)= (SELECT exam_type_id, MAX(config_version) FROM recommend_config where exam_type_id=?1 GROUP BY exam_type_id )", nativeQuery = true)
	RecommendConfig queryRcByExamType(short examTypeId);
	
//	@Transactional(rollbackFor = Exception.class)
//	@Modifying
//	@Query(value="insert into user_recommend_no(user_id, exam_type_id, recommend_no) values(?1, ?3, ?2) on duplicate key update recommend_no=?2", nativeQuery = true)
//	void updateUserRecommendConfig(long userId, long recommendNo, short examType);

}
