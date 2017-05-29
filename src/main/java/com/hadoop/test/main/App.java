package com.hadoop.test.main;

import java.util.ArrayList;
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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	SparkConf conf = new SparkConf().setMaster("local").setAppName("wordCountTest");
    	String outputDir = args[0];
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<String> list=new ArrayList<String>();
        list.add("1 1 2 a b");
        list.add("a b 1 2 3");//为一行
        JavaRDD<String> RddList=sc.parallelize(list);
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
