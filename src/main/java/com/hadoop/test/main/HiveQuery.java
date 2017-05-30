/**
 * 
 */
package com.hadoop.test.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import com.hadoop.test.bean.Person;

/**
 * @author lishaoping
 * 2017年5月29日下午10:22:51
 * HiveQuery
 */
public class HiveQuery {

	private static final SparkConf conf = new SparkConf().setMaster("local").setAppName("Test");
	private static final JavaSparkContext sc = new JavaSparkContext(conf);
	
	private static final SparkSession sparkSession = SparkSession.builder().master("local").appName("Test").getOrCreate();
	private static final SparkSession sparkSession2 = SparkSession.builder().appName("local").config("spark.sql.warehouse.dir", "E:\\spark-warehouse").enableHiveSupport().getOrCreate();
	public static void main(String[] args) {
		
//		testSparkSession();
		
//		testQueryJSON();
		
		testJDBC();
	}
	
	private static void testJDBC(){
		Dataset<Row> table = sparkSession.read().format("jdbc")
//					.jdbc(url, table, properties)
					.option("url", "jdbc:mysql://localhost:3306/mydatabases?user=root&password=lsp&serverTimezone=GMT&useUnicode=true&characterEncoding=utf8")//useSSL=false
					.option("dbtable", "exam_goods")
					.load();
		table.select("GOODS_ID", "GOODS_NAME", "GOODS_PRICE", "GOODS_NUM", "GOODS_MADE", "GOODS_SUPPLY", "GOODS_DESC", "GOODS_TYPE")
				.write().format("json")
				.save("E:\\tool\\test" + System.currentTimeMillis() + "\\");//此路径不能存在
		//存储为一张虚拟表
		table.select("GOODS_ID", "GOODS_NAME", "GOODS_PRICE", "GOODS_NUM", "GOODS_MADE", "GOODS_SUPPLY", "GOODS_DESC", "GOODS_TYPE")
				.write().mode("overwrite").saveAsTable("goods");
		//查表并存储数据
		Dataset<Row> result = sparkSession.sql("select * from goods");
		result.show();
		result.write().format("json").save("E:\\tool\\test" + + System.currentTimeMillis() + "\\");
		
	}
	
	private static void testHive(){
		//运行参数
		sparkSession2.conf().set("spark.sql.shuffle.partitions", 6);
		sparkSession2.conf().set("spark.excutor.memory", "1g");
		System.out.println(sparkSession2.conf().getAll());
		//读取元数据 dataset
		sparkSession2.catalog().listDatabases().show(false);
		sparkSession2.catalog().listTables().show(false);
		//sparksession---dataset---
		//DataSet
		Dataset<Long> set = sparkSession2.range(5, 100, 5);
		set.orderBy(new Column("id")).show(5);
		set.describe("id").show();
		//建立表
		List<Person> list = new ArrayList<Person>();
		list.add(new Person("lixiaohai", 25));
		list.add(new Person("lishaoping", 25));
		list.add(new Person("lilili", 25));
		list.add(new Person("xiaoxiaoxiao", 25));
		Dataset<Row> table = sparkSession2.createDataFrame(list, Person.class);
		table = table.withColumnRenamed("_1", "name").withColumnRenamed("_2", "age");
		table.orderBy("name", "age").show(5);
		
	}
	
	private static void testSparkSession(){
		Dataset<Row> ds = sparkSession.read().json("E:\\tool\\test\\a.json");
		ds.show();
	}
	
	private static void testQueryJSON(){
		SQLContext sqlCtx = new SQLContext(sc);
		Dataset<Row> ds = sqlCtx.read().json("E:\\tool\\test\\a.json");
		ds.show();
//		DataFrameReader reader = new DataFrameReader(sparkSession)
	}
}
