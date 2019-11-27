package com.ltd.e.recommend.config.Main.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月18日
 * @file UserQuestionRecommendPK
 */
public class UserQuestionRecommendPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long user_id;
	private long question_id;
	
	public UserQuestionRecommendPK(long userId, long question_id) {
		super();
		this.user_id = userId;
		this.question_id = question_id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserQuestionRecommendPK pk = (UserQuestionRecommendPK) o;
		return Objects.equals(user_id, pk.user_id) && Objects.equals(question_id, pk.question_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_id, question_id);
	}

	public UserQuestionRecommendPK() {
		super();
	}
}
