package com.ltd.e.recommend.config.Main.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月18日
 * @file RecommendConfigPK
 */
public class RecommendConfigPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long user_id;
	private long exam_type_id;
	
	public RecommendConfigPK(long userId, long exam_type_id) {
		super();
		this.user_id = userId;
		this.exam_type_id = exam_type_id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RecommendConfigPK pk = (RecommendConfigPK) o;
		return Objects.equals(user_id, pk.user_id) && Objects.equals(exam_type_id, pk.exam_type_id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user_id, exam_type_id);
	}

	public RecommendConfigPK() {
		super();
	}
}
