package com.construct.spark;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import scala.Tuple2;

/**
 * 弹性数据集为数据结构，而不是map-reduce那一套过程
 *map.maptopair ,reducebykey,sortedbykey,collect
 *@author lishaoping
 *BigData
 *2017年11月5日
 */
public class WordCount {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("easy_task").setMaster("local[1]");
		JavaSparkContext context = new JavaSparkContext(conf);
		List<String> yuan = Arrays.asList(new String[] {"ss","ss","ew"});
		JavaRDD<String> lineRDD = context.parallelize(yuan);
//		JavaRDD<String> lineRDD = context.textFile("D:\\tool\\words.txt");
		JavaRDD<String> text = lineRDD.flatMap(new FlatMapFunction<String, String>() {

			@Override
			public Iterator<String> call(String arg0) throws Exception {
				return Arrays.asList(arg0.split("\\s+")).iterator();
			}
		});
		
		JavaPairRDD<String, Integer> pair = text.mapToPair(new PairFunction<String, String, Integer>() {

			@Override
			public Tuple2<String, Integer> call(String arg0) throws Exception {
				return new Tuple2<String, Integer>(arg0, 1);
			}
		});
		
		//定义值的两两计算方法,如果是一个元素，根本不需要调用这个方法，也不会执行这个reduceBykey方法中注入的元素了，即直接输出了，直接就是结果了
		//同时也说明了：reduceByKey的传递函数的泛型参数是定了的
		JavaPairRDD<String, Integer> reduce = pair.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				System.out.println("how many times:" + arg0 + "," + arg1);
				
				return arg0 + arg1;
			}
		});
		
		JavaPairRDD<String, Integer> result = reduce.sortByKey(true);
		
		System.out.println("result is : " + reduce.collect());
		System.out.println("result is : " + result.collect());
		visitList(result);
		JavaRDD<Tuple2<Integer, String>> re2 = result.map(new Function<Tuple2<String,Integer>, Tuple2<Integer,String>>() {

			@Override
			public Tuple2<Integer,String> call(Tuple2<String, Integer> arg0) throws Exception {
				return new Tuple2<Integer, String>(arg0._2, arg0._1);
			}
		});
//		List<Tuple2<Integer, String>> list2 = re2.sortBy(new Function<Tuple2<Integer,String>, Tuple2<Integer,String>>() {
//
//			@Override
//			public Tuple2<Integer,String> call(Tuple2<Integer, String> arg0) throws Exception {
//				return arg0;//需要comparable的返回类型
//			}
//		}, true, 10).collect();
//		for(Tuple2<Integer, String> tu : list2) {
//			System.out.println(tu._2 + "-------count3---:" + tu._1);
//		}
		JavaPairRDD<Integer, String> pair2 = re2.mapToPair(new PairFunction<Tuple2<Integer,String>, Integer, String>() {

			@Override
			public Tuple2<Integer,String> call(Tuple2<Integer, String> arg0) throws Exception {
				// TODO Auto-generated method stub
				return arg0;
			}
		}).sortByKey(false);
		for(Tuple2<Integer, String> tu : pair2.collect()) {
			System.out.println(tu._2 + "----count3-----:" + tu._1);
		}
		reduce.persist(StorageLevel.MEMORY_AND_DISK());
		reduce.saveAsObjectFile("D:\\test\\" + System.currentTimeMillis());
//		reduce.saveAsTextFile("D:\\test\\" + System.currentTimeMillis());
		context.close();
	}

	private static void visitList(JavaPairRDD<String, Integer> result) {
		for(Tuple2<String , Integer> tu : result.collect()) {
			System.out.println(tu._1 + "-----:count:---" + tu._2);
		}
	}
}
