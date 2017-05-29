/**
 * 
 */
package com.spark.test.main;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

/**
 * @author lishaoping
 * 2017年5月29日上午9:33:38
 * WordsCount
 */
public class WordsCount {
 
	private static final Pattern p = Pattern.compile(" ");
	
	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("error");
			System.exit(1);
		}
		SparkConf conf = new SparkConf().setAppName("javaSpark.com.spark.test.WordsCount");//.setMaster("");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		JavaRDD<String> lines = ctx.textFile(args[0], 1);//最小分割1
		//转换,匿名类,决定下一个JavaRDD中每一行的类型..但不是由用户写的具体的转换型方法的返回类型决定的，而是由spark 本身的转换型方法决定的
		//每一行变成iterater里的每个元素
		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>(){
			
			public Iterator<String> call(String arg0) throws Exception {
				System.out.println(arg0);
				return Arrays.asList(p.split(arg0)).iterator();
			}
			
		});
		//每一行变成一个Tuple2. map方法也行
		JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
			
			public Tuple2<String, Integer> call(String arg0) throws Exception {
				return new Tuple2<String, Integer>(arg0, 1);
			}
			
		});
		//直接传入用户函数，间接处理后传入用户函数；直接组合用户返回值到行，间接处理后的结果到行
		//本转换函数就是间接传入，先遍历全部，按元组的第一个参数为key，value放到collection，重新形成key-collection。遍历collection，每次传入两个元素给用户函数。两两相加，得到结果，
		JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		//排个序
		counts = counts.sortByKey();
		//转换为元组
		List<Tuple2<String, Integer>> output = counts.collect(); 
		for(Tuple2<String, Integer> tu : output){
			System.out.println(tu._1() + " : " + tu._2());
		}
		//另一种遍历方式
//		counts.foreachPartition(new VoidFunction<Iterator<Tuple2<String,Integer>>>() {
//			
//			public void call(Iterator<Tuple2<String, Integer>> arg0) throws Exception {
//				
//				
//			}
//		});
		//保存，非分布式文件格式
	//	counts.saveAsTextFile("/home/lixiaohai/opt/");
		counts.saveAsTextFile("/home/lixiaohai/opt/output");//hdfs://cdh5/tmp/lxw1234.com/ 要求目录是没有的 file:///tmp/lxw1234.com
		ctx.stop();
	}
}
