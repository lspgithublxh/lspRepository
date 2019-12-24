package com.li.shao.ping.KeyListBase.datastructure.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WrieFileTest {

	public static void main(String[] args) {
//		String x = "DROP TABLE IF EXISTS `user_knowlege_complete_%id%`;";
//		String x = "CREATE TABLE `user_knowlege_complete_%id%` (   `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',   `knowledge_point_lvl_end_id` bigint(20) unsigned NOT NULL COMMENT '末级知识点id',   `knowledge_point_lvl0_id` bigint(20) unsigned NOT NULL COMMENT '顶级知识点id', `exam_type_id` SMALLINT(5) UNSIGNED NOT NULL default 0 COMMENT '考试类型id',`exam_subject_id` SMALLINT(5) UNSIGNED NOT NULL default 0 COMMENT '考试科目id',  `grasp_lvl` double(9,8) NOT NULL COMMENT '掌握程度',   `priority` double(9,8) NOT NULL COMMENT '知识点优先级',  `recommend_times` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '推荐次数',  `last_update_time` bigint(16) unsigned NOT NULL COMMENT '上次回答问题后更新知识掌握度的时间',   PRIMARY KEY (`user_id`,`knowledge_point_lvl_end_id`, `exam_subject_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户知识掌握程度' ;";
//		String x = "CREATE TABLE `recommend_user_question_%id%` (   `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',   `question_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '问题id',   `recommend_times` INT(10) UNSIGNED NOT NULL COMMENT '推荐次数',   `recommend_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '推荐序号；时间序号,每个用户唯一',   PRIMARY KEY (`user_id`,`question_id`) ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='推荐次数表';";
//		String x = "alter table user_knowlege_complete_%id% add `exam_type_id` SMALLINT(5) UNSIGNED NOT NULL default 0 COMMENT '考试类型id';";
		String x = "UPDATE user_knowlege_complete_%id% u SET exam_type_id=(SELECT exam_type_id FROM test_prosser.exam_subject e WHERE e.exam_subject_id=u.exam_subject_id);";
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
	
	private static void ttest() throws IOException {
		FileWriter wri = new FileWriter("D:\\test\\e.txt");
		int[] i = {108,109,112,122,156,157,160,161,303,311,313,334,343,349,351,355,356,357,359,361,365,366,367,368,369,370,371,372,373,374,375,376,377,378,379,380,381,382,383,384,385,386,387,388,389,390,391,392,393,394,395,802,805,806,807,808,809,810,811,812,813,814,815,816,817,818,819,820,821,822,832,833,838,839,844,845,846,888,889,890,909,919,920,921,922,923,924,925,926,927,930,931,932,936,940,941,984,985,9999};
		for(int user: i) {
			user = user % 1024;
			String s = "INSERT INTO test_recommend.`user_knowlege_complete_" + user + "` (`user_id`, `knowledge_point_lvl_end_id`, `knowledge_point_lvl0_id`, `exam_subject_id`, `grasp_lvl`, `priority`, `recommend_times`, `last_update_time`) SELECT * FROM test_prosser.user_knowlege_complete_chukuai WHERE user_id=" + user + ";";
//			String s = "INSERT INTO test_recommend.recommend_user_question_" + user + " SELECT * FROM test_prosser.`recommend_user_question_chukuai` WHERE user_id=" + user + ";";
			wri.write(s + "\r\n");
		}
		wri.flush();
	}
	
	private static void oldMethod() {
		//		String x = "DROP TABLE IF EXISTS `user_knowlege_complete_%id%`;";
//		String x = "CREATE TABLE `user_knowlege_complete_%id%` (   `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',   `knowledge_point_lvl_end_id` bigint(20) unsigned NOT NULL COMMENT '末级知识点id',   `knowledge_point_lvl0_id` bigint(20) unsigned NOT NULL COMMENT '顶级知识点id', `exam_subject_id` SMALLINT(5) UNSIGNED NOT NULL default 0 COMMENT '考试科目id',  `grasp_lvl` double(9,8) NOT NULL COMMENT '掌握程度',   `priority` double(9,8) NOT NULL COMMENT '知识点优先级',  `recommend_times` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '推荐次数',  `last_update_time` bigint(16) unsigned NOT NULL COMMENT '上次回答问题后更新知识掌握度的时间',   PRIMARY KEY (`user_id`,`knowledge_point_lvl_end_id`, `exam_subject_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户知识掌握程度' ;";
//		String x = "CREATE TABLE `recommend_user_question_%id%` (   `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',   `question_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '问题id',   `recommend_times` INT(10) UNSIGNED NOT NULL COMMENT '推荐次数',   `recommend_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '推荐序号；时间序号,每个用户唯一',   PRIMARY KEY (`user_id`,`question_id`) ) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='推荐次数表';";
		try {
			FileReader reader = new FileReader("C:\\Users\\86156\\Desktop\\e.sql");
			BufferedReader read = new BufferedReader(reader);
			FileWriter wri = new FileWriter("D:\\test\\f.txt");
			
			
			String line = "";
			while((line = read.readLine()) != null) {
				int s = line.indexOf("'");
				int e = line.indexOf("',");
				String id = line.substring(s+1, e);
				int id_ = Integer.valueOf(id);
				String li = line.replace("user_knowlege_complete_chukuai", "user_knowlege_complete_" + (id_ % 1024));
				wri.write(li + "\r\n");
			}
			wri.flush();
//			for(int i = 0; i < 1024; i++) {
//				System.out.println(x.replace("%id%", i+""));
//				wri.write(x.replace("%id%", i+"") + "\r\n");
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
