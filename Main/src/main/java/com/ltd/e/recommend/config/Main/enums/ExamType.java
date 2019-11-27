package com.ltd.e.recommend.config.Main.enums;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年10月6日
 * @file AnswerResultJudge
 */
public enum ExamType {

	/* 消防 */
	xiaofang((short)1),
	/**
	 * 会计
	 */
	kuaiji((short)1),
	/**
	 * 基金
	 */
	jijin((short)1);
	private short type;
	ExamType(short type) {
		this.type = type;
	}
	public short getType() {
		return type;
	}
	
	public static ExamType getVal(short type) {
		for(ExamType e : ExamType.values()) {
			if(type == e.type) {
				return e;
			}
		}
		return ExamType.xiaofang;
	}
}
