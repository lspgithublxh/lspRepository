package com.construct.spark;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

public class SparkTask2 {

	public static void main(String[] args) {
		localHandle();
//		distributedMethod();
	}
	
	/**
	 * 一气呵成非常重要，隔了几天可能全部就忘干净了
	 *@author lishaoping
	 *BigData
	 *2017年11月4日
	 */
	private static void localHandle() {
		SparkConf conf = new SparkConf().setAppName("easy_task").setMaster("local[1]");
		JavaSparkContext context = new JavaSparkContext(conf);
		//1.创建RDD---内存方式 list 
		List<Integer> list = Arrays.asList(new Integer[] {1,2,3,4,5});
		JavaRDD<Integer> RDD1 = context.parallelize(list, 10);
		//2.创建RDD---文件方式-line of collection
		JavaRDD<String> lineRDD = context.textFile("D:\\tool\\words.txt");//不一定是单个文件，可以是多个文件，匹配到的文件
		//3.JavaRDD上进行的一系列集合操作：分布式集合的操作
		int textSize = lineRDD.map(s -> s.length()).reduce((a, b) -> a + b);
		System.out.println("fileSize:" + textSize);
		//4.创建特殊RDD,文件名-文件内容的pair ,对一个目录下的小文件
//		JavaPairRDD<String, String> pairs = context.wholeTextFiles("D:\\test\\text");
		//5.创建RDD，hadoop任务方式
//		context.hadoopRDD(conf, inputFormatClass, keyClass, valueClass)
//		context.newAPIHadoopRDD(conf, fClass, kClass, vClass)
		somKindsOfHandle(lineRDD);
	}
	
	private static void distributedMethod() {
		SparkConf conf = new SparkConf().setAppName("easy_task").setMaster("S1PA11");
		JavaSparkContext context = new JavaSparkContext(conf);
		//1.创建RDD---内存方式 list 
		List<Integer> list = Arrays.asList(new Integer[] {1,2,3,4,5});
		JavaRDD<Integer> RDD1 = context.parallelize(list, 10);
		//2.创建RDD---文件方式-line of collection
		JavaRDD<String> lineRDD = context.textFile("hdfs://li1028/partition.txt");//不一定是单个文件，可以是多个文件，匹配到的文件
		//3.JavaRDD上进行的一系列集合操作：分布式集合的操作
		int textSize = lineRDD.map(s -> s.length()).reduce((a, b) -> a + b);
		//4.创建特殊RDD,文件名-文件内容的pair ,对一个目录下的小文件
		JavaPairRDD<String, String> pairs = context.wholeTextFiles("hdfs://li1028");
		//5.创建RDD，hadoop任务方式
//		context.hadoopRDD(conf, inputFormatClass, keyClass, valueClass)
//		context.newAPIHadoopRDD(conf, fClass, kClass, vClass)
		
		
		somKindsOfHandle(lineRDD);
	}

	private static void somKindsOfHandle(JavaRDD<String> lineRDD) {
		//6.RDD操作 常见：transform 和action
		 JavaRDD<Integer> lineCount = lineRDD.map(s -> s.length());
		 lineCount.persist(StorageLevel.MEMORY_ONLY());
		 int size = lineCount.reduce((a, b) -> a + b);
		 System.out.println("fileSize2:" + size);
		 JavaRDD<Integer> counts = lineRDD.map(new Function<String, Integer>(){
			@Override
			public Integer call(String arg0) throws Exception {
				return arg0.length();
			}
		 });
		 int size2 = counts.reduce(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		 System.out.println("fileSize3:" + size);
		 //7.返回worker元素到driver
		 counts.take(100).forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
					System.out.println(t);
			}
		});
		 //8.map-to-pair:分组
		JavaPairRDD pairs2 = lineRDD.mapToPair(s -> new Tuple2<>(s, 1));
		JavaPairRDD<String, Integer> statistic = pairs2.reduceByKey(new Function2() {
			@Override
			public Object call(Object arg0, Object arg1) throws Exception {
				System.out.println("arg0:" + arg0);
				System.out.println("arg1:" + arg1);
//				return new Tuple2(((Integer)arg0), ((Integer)arg1));
				return new Tuple2((arg0), (arg1));
			}
			
		});
		statistic.take(100).forEach(new Consumer<Tuple2<String,Integer>>() {

			@Override
			public void accept(Tuple2<String, Integer> t) {
				System.out.println(t._1 + "-----------" + t._2);
			}

		});
		//9.排序
		JavaPairRDD<String, Integer> sorted = statistic.sortByKey(true);
		System.out.println(sorted.toDebugString());
		//10.收集返回的结果
		List<Tuple2<String, Integer>> collect = sorted.collect();
		for(Tuple2<String, Integer> t : collect) {
			System.out.println("-collect:--" + t._1 + "---" + t._2);
		}
		//11.异步操作：
//		statistic.foreachAsync(f)
	}
}
