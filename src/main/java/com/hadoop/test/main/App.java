package com.hadoop.test.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

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
 * Hello world!
 *
 */
public class App 
{
	
	private static final SparkConf conf = new SparkConf().setMaster("local").setAppName("T");
	private static final JavaSparkContext sc = new JavaSparkContext(conf);
	private static Pattern pa = Pattern.compile(" ");
	
    public static void main( String[] args )
    {
    	
//    	noInputFile(args);
    	hasInputFile(args);
    }

    private static void hasInputFile(String[] args){
    	JavaRDD<String> lines = sc.textFile("D:\\log\\error.log");
    	lines.cache();
    	JavaRDD<String> itemList = lines.flatMap(new FlatMapFunction<String, String>() {

			public Iterator<String> call(String arg0) throws Exception {
				return Arrays.asList(pa.split(arg0)).iterator();
			}
		});
    	JavaPairRDD<String, Integer> tupleList = itemList.mapToPair(new PairFunction<String, String, Integer>() {

			public Tuple2<String, Integer> call(String arg0) throws Exception {
				return new Tuple2<String, Integer>(arg0, 1);
			}
			
		});
    	JavaPairRDD<String, Integer> keyValueList = tupleList.reduceByKey(new Function2<Integer, Integer, Integer>() {

			public Integer call(Integer arg0, Integer arg1) throws Exception {
				return arg0 + arg1;
			}
		});
    	System.out.println("------------------first output form ----------------");
    	keyValueList.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			
			public void call(Tuple2<String, Integer> arg0) throws Exception {
				System.out.println(arg0._1 + " : " + arg0._2);
			}
		});
    	System.out.println("------------------second output form ----------------");
    	keyValueList.foreachPartition(new VoidFunction<Iterator<Tuple2<String,Integer>>>() {

			public void call(Iterator<Tuple2<String, Integer>> arg0) throws Exception {
				while(arg0.hasNext()){
					System.out.println(arg0.next().toString());
				}
			}
		});
    	System.out.println("------------------thrid output form ----------------");
    	for(Tuple2<String, Integer> tuple : keyValueList.collect()){
    		System.out.println(tuple.toString());
    	}
    	System.out.println("------------------fourth output form ----------------");
    	keyValueList = keyValueList.sortByKey();
    	keyValueList.saveAsTextFile(args[0]);
    }
    
	/**
	 * @author lishaoping
	 * 2017年5月29日下午4:43:14
	 *App.java
	 * @param args
	 */
	private static void noInputFile(String[] args) {
		String outputDir = args[0];
        List<String> list = new ArrayList<String>();
        list.add("1 1 2 a b");
        list.add("a b 1 2 3");//为一行
        JavaRDD<String> RddList = sc.parallelize(list);
        JavaRDD<String> flatMapRdd = RddList.flatMap(new FlatMapFunction<String, String>() {

			public Iterator<String> call(String arg0) throws Exception {
				return Arrays.asList(arg0.split(" ")).iterator();
			}
          
        });
        JavaPairRDD<String, Integer> pairRdd = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });
        JavaPairRDD<String, Integer> countRdd = pairRdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });
        System.out.println("结果："+countRdd.collect());
        countRdd.saveAsTextFile(outputDir);
        sc.close();
	}
}
