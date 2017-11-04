package com.construct.spark;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class WordCount {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("easy_task").setMaster("local[1]");
		JavaSparkContext context = new JavaSparkContext(conf);
		JavaRDD<String> lineRDD = context.textFile("D:\\tool\\words.txt");
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
		
		JavaPairRDD<String, Integer> reduce = pair.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		System.out.println("result is : " + reduce.collect());
		reduce.saveAsTextFile("D:\\test\\result115.txt");
		context.close();
	}
}
