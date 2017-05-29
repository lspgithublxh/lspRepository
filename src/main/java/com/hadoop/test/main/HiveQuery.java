/**
 * 
 */
package com.hadoop.test.main;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

/**
 * @author lishaoping
 * 2017年5月29日下午10:22:51
 * HiveQuery
 */
public class HiveQuery {

	private static final SparkConf conf = new SparkConf().setMaster("local").setAppName("T");
	private static final JavaSparkContext sc = new JavaSparkContext(conf);
	
	public static void main(String[] args) {
		SQLContext sqlCtx = new SQLContext(sc);
		testQueryJSON(sqlCtx);
	}
	
	private static void testQueryJSON(SQLContext sqlCtx){
		Dataset<Row> ds = sqlCtx.read().json("E:\\tool\\test\\a.json");
		ds.show();
//		DataFrameReader reader = new DataFrameReader(sparkSession)
	}
}
