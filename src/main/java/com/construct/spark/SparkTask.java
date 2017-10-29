package com.construct.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class SparkTask {

	public static void main(String[] args) {
		String file = "/home/lixiaohai/opt/spark-2.1.1-bin-hadoop2.7/README.md";
		SparkSession session = SparkSession.builder().appName("spark_task").getOrCreate();
		Dataset<String> dataSet = session.read().textFile(file).cache();
		
		long num = dataSet.filter(s -> s.contains("a")).count();
		long num2 = dataSet.filter(s -> s.contains("b")).count();
		
		System.out.println("line contains a : " + num + ", line contains b : " + num2);
		session.stop();
	}
}
