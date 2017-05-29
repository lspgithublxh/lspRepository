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
 * 2017��5��29������9:33:38
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
		JavaRDD<String> lines = ctx.textFile(args[0], 1);//��С�ָ�1
		//ת��,������,������һ��JavaRDD��ÿһ�е�����..���������û�д�ľ����ת���ͷ����ķ������;����ģ�������spark �����ת���ͷ���������
		//ÿһ�б��iterater���ÿ��Ԫ��
		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>(){
			
			public Iterator<String> call(String arg0) throws Exception {
				System.out.println(arg0);
				return Arrays.asList(p.split(arg0)).iterator();
			}
			
		});
		//ÿһ�б��һ��Tuple2. map����Ҳ��
		JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
			
			public Tuple2<String, Integer> call(String arg0) throws Exception {
				return new Tuple2<String, Integer>(arg0, 1);
			}
			
		});
		//ֱ�Ӵ����û���������Ӵ�������û�������ֱ������û�����ֵ���У���Ӵ����Ľ������
		//��ת���������Ǽ�Ӵ��룬�ȱ���ȫ������Ԫ��ĵ�һ������Ϊkey��value�ŵ�collection�������γ�key-collection������collection��ÿ�δ�������Ԫ�ظ��û�������������ӣ��õ������
		JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		//�Ÿ���
		counts = counts.sortByKey();
		//ת��ΪԪ��
		List<Tuple2<String, Integer>> output = counts.collect(); 
		for(Tuple2<String, Integer> tu : output){
			System.out.println(tu._1() + " : " + tu._2());
		}
		//��һ�ֱ�����ʽ
//		counts.foreachPartition(new VoidFunction<Iterator<Tuple2<String,Integer>>>() {
//			
//			public void call(Iterator<Tuple2<String, Integer>> arg0) throws Exception {
//				
//				
//			}
//		});
		//���棬�Ƿֲ�ʽ�ļ���ʽ
	//	counts.saveAsTextFile("/home/lixiaohai/opt/");
		counts.saveAsTextFile("/home/lixiaohai/opt/output");//hdfs://cdh5/tmp/lxw1234.com/ Ҫ��Ŀ¼��û�е� file:///tmp/lxw1234.com
		ctx.stop();
	}
}
