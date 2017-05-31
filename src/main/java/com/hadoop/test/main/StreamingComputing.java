/**
 * 
 */
package com.hadoop.test.main;


import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function0;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;
/**
 * @author lishaoping
 * 2017��5��30������8:45:50
 * StreamingComputing
 */
public class StreamingComputing {

	//2����2�������߳�\ִ���߳�
	private static final SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");
	//���5sִ��,job����..5s���յ���������Ϊһ�����ݣ�����Ϊһ��RDD������
	private static final JavaStreamingContext jsct = new JavaStreamingContext(conf,Durations.seconds(5));
	
	public static void main(String[] args) throws InterruptedException {
		testStreamingComputing();
//		createStreamingFromFile();
	}
	
	//��hdfsҲ����
	private static void createStreamingFromJAVARDD(){
//		jsct.queueStream(new Queue)
		
	}
	
	private static void testKafka(){
//		JavaReceiverInputDStream<String> lines = jsct.socketTextStream("localhost", 9999,StorageLevel.MEMORY_AND_DISK());
//		KafkaUtils
	}
	
	private static void createStreamingFromFile() throws InterruptedException{
//		JavaStreamingContext cc = JavaStreamingContext.getOrCreate("D:\\log\\checkpoint_data", new Function0<JavaStreamingContext>() {
//			
//			public JavaStreamingContext call() throws Exception {
////				JavaStreamingContext jsct = new JavaStreamingContext(conf,Durations.seconds(5));
//				jsct.checkpoint("D:\\log\\checkpoint_data");
//				return jsct;
//			}
//		});
		jsct.checkpoint("D:\\log\\checkpoint_data");
		JavaDStream<String> jds = jsct.textFileStream("D:\\log\\data");
//		JavaDStream<String> jds = jsct.textFileStream("D:\\log\\error.log");
		JavaDStream<String> words = jds.flatMap(new FlatMapFunction<String, String>() {

			public Iterator<String> call(String arg0) throws Exception {
				System.out.println("flatMap:--" + arg0 + "--");
				return Arrays.asList(arg0.split(" ")).iterator();
			}
		});
		//�������ֱ任:����Ԫ�顪��>reduce
		JavaPairDStream<String, Integer> kv_wordsCount = nextTransform(words);
		kv_wordsCount.print();
		System.out.println(kv_wordsCount.count());;
		kv_wordsCount.foreachRDD(new VoidFunction<JavaPairRDD<String,Integer>>() {
			
			public void call(JavaPairRDD<String, Integer> arg0) throws Exception {
				System.out.println("foreachRDD:" + arg0.collect().toString());
			}
		});
		jsct.start();
		jsct.awaitTermination();
		jsct.stop();
	}
	
	/**
	 * ���任
	 * @author lishaoping
	 * 2017��5��30������9:36:09
	 *StreamingComputing.java
	 * @throws InterruptedException
	 */
	private static void testStreamingComputing() throws InterruptedException{
		//ת��lines��
		JavaReceiverInputDStream<String> lines = jsct.socketTextStream("localhost", 9999);
		System.out.println(" count: " + lines.toString() + lines.dstream().countByValue$default$1());
		//ת��Ϊwords��
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

			public Iterator<String> call(String arg0) throws Exception {
				System.out.println("liangliang :" + arg0 + "end");
				return Arrays.asList(arg0.split(" ")).iterator();
			}
		});
		JavaPairDStream<String, Integer> kv_wordsCount = nextTransform(words);
		kv_wordsCount.print();
		//����Ĳ�����֤��ת�������Ľ�������û�п�ʼ����
		jsct.start();
		//����ֱ�����
		jsct.awaitTermination();
//		jsct.stop();
		
	}

	/**
	 * @author lishaoping
	 * 2017��5��30������9:27:36
	 *StreamingComputing.java
	 * @param words
	 * @return
	 */
	private static JavaPairDStream<String, Integer> nextTransform(JavaDStream<String> words) {
		//ת��Ϊtuple2��
		JavaPairDStream<String, Integer> tuples = words.mapToPair(new PairFunction<String, String, Integer>() {

			public Tuple2<String, Integer> call(String arg0) throws Exception {
				return new Tuple2<String, Integer>(arg0, 1);
			}
		});
		//ͬ��combine,��������,���Ϊmap
		JavaPairDStream<String, Integer> kv_wordsCount = tuples.reduceByKey(new Function2<Integer, Integer, Integer>() {
			
			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
		return kv_wordsCount;
	}
}
