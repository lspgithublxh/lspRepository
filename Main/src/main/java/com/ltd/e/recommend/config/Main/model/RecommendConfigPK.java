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
	private short configVersion;
	private short examTypeId;
	
	public RecommendConfigPK(short configVersion, short examTypeId) {
		super();
		this.configVersion = configVersion;
		this.examTypeId = examTypeId;
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
		return Objects.equals(configVersion, pk.configVersion) && Objects.equals(examTypeId, pk.examTypeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(configVersion, examTypeId);
	}

	public RecommendConfigPK() {
		super();
	}
}
