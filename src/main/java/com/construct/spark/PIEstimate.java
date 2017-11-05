package com.construct.spark;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class PIEstimate {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("pi estimate").setMaster("local[1]");
		JavaSparkContext context = new JavaSparkContext(conf);
		long count = 1000000;
		long cou2 = 0;
		for(int i = 0 ; i < count; i++) {
			double x = Math.random();
			double y = Math.random();
			if(x * x + y * y <= 1) {
				cou2++;
			}
		}
		System.out.println("old:pi:" + 4 * (double)cou2 / (double)count );
		List<Long> ls = Arrays.asList(new Long[1000000]);
		long count3 = context.parallelize(ls).filter(s -> {
			double x = Math.random();
			double y = Math.random();
			return x * x + y * y < 1;
		}).count();
		System.out.println("new : pi:" + 4 * (double)count3 / (double)count);
	}
}
