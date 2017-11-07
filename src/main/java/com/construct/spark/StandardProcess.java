package com.construct.spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkJobInfo;
import org.apache.spark.SparkStageInfo;
import org.apache.spark.api.java.JavaFutureAction;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import com.google.common.collect.Iterables;

import scala.Tuple2;
/**
 * 无状态可取
 *  pageRank是一个重要性排序、依赖程度排序，引用频率排序
 *@author lishaoping
 *BigData
 *2017年11月5日
 * @param args
 * @throws InterruptedException
 * @throws ExecutionException
 */
public class StandardProcess {

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SparkConf conf = new SparkConf().setMaster("local[4]");
		SparkSession session = SparkSession.builder().appName("test").config(conf).getOrCreate();
		JavaSparkContext context = new JavaSparkContext(session.sparkContext());
		getStageAndJobStatus(context);
//		session.read().text("").javaRDD();
		pageRank(context);
	}

	private static Pattern p = Pattern.compile("\\s+");
	
	private static void pageRank(JavaSparkContext context) {
		JavaRDD<String> text = context.textFile("D:\\test\\text\\page.txt");
		JavaPairRDD<String, Iterable<String>> pairs = text.mapToPair(s -> {
			String[] pair = p.split(s);
			return new Tuple2<>(pair[0], pair[1]);
		}).distinct().groupByKey().cache();
		//构造新的pairs,同键不同值.作为最初的每个url的分值--即重要程度
		JavaPairRDD<String, Double> pairs2 = pairs.mapValues(s -> 1.0);
		for(int i = 0; i < 20; i++) {
			//生成迭代pair像pair一样，再用它来构造出pairs2一样的分值pair
//			JavaRDD<Tuple2<Iterator<Double>, Double>> complex = pairs.join(pairs2).values().map(tu -> {
//				List<Double> ls = new ArrayList<>();
//				int size = Iterables.size(ls);
//				//从pairs2中获取到同一个键的score非常重要
//				for(String url : tu._1()) {
//					ls.add(tu._2() / size);
//				}
//				return new Tuple2<>(ls.iterator(), tu._2);
//			});
			JavaRDD<Tuple2<String, Double>> flatMap = pairs.join(pairs2).values().flatMap(tu -> {
				List<Tuple2<String,Double>> ls = new ArrayList<>();
				int size = Iterables.size(tu._1);
				System.out.println("size : " + size);
				//从pairs2中获取到同一个键的score非常重要
				for(String url : tu._1()) {
					ls.add(new Tuple2<>(url, tu._2() / size));
				}
				return ls.iterator();
			});
			pairs2 = flatMap.mapToPair(s -> s).reduceByKey((s1, s2) -> s1 + s2);
//			
		}
		pairs2 = pairs2.sortByKey(true);
		for(Tuple2<String , Double> tu : pairs2.collect()) {
			System.out.println("--url: " + tu._1 + ", score:" + tu._2());
		}
		JavaPairRDD<Double, String> pairs3 = pairs2.mapToPair(s -> {
			return new Tuple2<>(s._2, s._1);
		}).sortByKey(false);
		for(Tuple2<Double , String> tu : pairs3.collect()) {
			System.out.println("--url: " + tu._2 + ", score:" + tu._1());
		}
		context.stop();
	}

	private static void getStageAndJobStatus(JavaSparkContext context) throws InterruptedException, ExecutionException {
		JavaRDD<Integer> rdd = context.parallelize(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9}));
		rdd = rdd.map(s -> {Thread.sleep(2000);return s + 1;});
		JavaFutureAction<List<Integer>> tasks = rdd.collectAsync();
		while(!tasks.isDone()) {
			if(tasks.get().isEmpty()) {
				continue;
			}
			int id = tasks.get().get(tasks.get().size() - 1);
			SparkJobInfo  lastJob = context.statusTracker().getJobInfo(id);
			if(null == lastJob || lastJob.stageIds() == null || lastJob.stageIds().length == 0) {
				continue;
			}
			SparkStageInfo stage = context.statusTracker().getStageInfo(lastJob.stageIds()[0]);
			System.out.println("stage: tasks:" + stage.numTasks() + ", active:" + stage.numActiveTasks() + ", failed:" + stage.numFailedTasks() + ", compled:" + stage.numCompletedTasks());
		}
		System.out.println("run stop:result is :" + tasks.get());
//		context.stop();
	}
}
