package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.FileWriter;
import java.io.IOException;

public class WrieFileTest {

	public static void main(String[] args) {
//		String x = "DROP TABLE IF EXISTS `user_knowlege_complete_%id%`;";
//		String x = "CREATE TABLE `user_knowlege_complete_%id%` (   `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',   `knowledge_point_lvl_end_id` bigint(20) unsigned NOT NULL COMMENT '末级知识点id',   `knowledge_point_lvl0_id` bigint(20) unsigned NOT NULL COMMENT '顶级知识点id', `exam_subject_id` SMALLINT(5) UNSIGNED NOT NULL default 0 COMMENT '考试科目id',  `grasp_lvl` double(9,8) NOT NULL COMMENT '掌握程度',   `priority` double(9,8) NOT NULL COMMENT '知识点优先级',  `recommend_times` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '推荐次数',  `last_update_time` bigint(16) unsigned NOT NULL COMMENT '上次回答问题后更新知识掌握度的时间',   PRIMARY KEY (`user_id`,`knowledge_point_lvl_end_id`, `exam_subject_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户知识掌握程度' ;";
		String x = "CREATE TABLE `recommend_user_question_%id%` (   `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',   `question_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '问题id',   `recommend_times` INT(10) UNSIGNED NOT NULL COMMENT '推荐次数',   `recommend_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '推荐序号；时间序号,每个用户唯一',   PRIMARY KEY (`user_id`,`question_id`) ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='推荐次数表';";
		try {
			FileWriter wri = new FileWriter("D:\\test\\f.txt");
			for(int i = 0; i < 1024; i++) {
				System.out.println(x.replace("%id%", i+""));
				wri.write(x.replace("%id%", i+"") + "\r\n");
			}
			wri.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
