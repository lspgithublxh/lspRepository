package com.ltd.e.recommend.config.Main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name="recommend_user_question")
@IdClass(value = UserQuestionRecommendPK.class )
public class UserQuestionRecommend {

	@Id
	@Column(name="user_id", nullable=false)
	private long user_id;
	
	@Id
	@Column(name="question_id", nullable=false)
	private long question_id;
	
	
	@Column(name="recommend_times", nullable=false)
	private int recommend_times;
	
	
	@Column(name="recommend_no", nullable=false)
	private long recommend_no;

	

}
