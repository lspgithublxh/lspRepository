package com.construct.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class RecommenderTest {

	public static void main(String[] args) throws IOException, TasteException {
		similarityRecommend();
	}

	/**
	 * 相似度计算和k临近推荐方法
	 *@author lishaoping
	 *BigData
	 *2017年10月23日
	 * @throws IOException
	 * @throws TasteException
	 */
	private static void similarityRecommend() throws IOException, TasteException {
//		RandomUtils.useTestSeed();
//		String file = "item.csv";
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
		System.out.println(RecommenderTest.class.getResource("").getPath());
		System.out.println(RecommenderTest.class.getClassLoader().getResource("").getPath());
		String file = RecommenderTest.class.getResource("").getPath().substring(1).replace("/", "\\");
		//基于文件得到数据结构，或者数据库，或者内存
		DataModel dataModel = new FileDataModel(new File(file + "item.csv"));
		//皮尔逊计算相似度，或者欧几里得距离，或者Tanimoto 系数计算
		UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
		//寻找k临近，或者门限比例，
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
		//基于用户的推荐引擎，或者基于内容的推荐引擎
		Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
		CachingRecommender ca = new CachingRecommender(recommender);
		for(int i = 0; i < 6; i++) {
			System.out.println("recommend for user" + i + ":");
			List<RecommendedItem> recommendList = recommender.recommend(i, 2);
			for(RecommendedItem item : recommendList) {
				System.out.print(item.toString());
			}
			System.out.println();
			System.out.println("-------------------next user----------");
		}
	}
}
