package com.construct.spark;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class DataFrameHandle {

	public static void main(String[] args) {
		handle1();
	}

	private static void handle1() {
		SparkConf conf = new SparkConf().setAppName("dataframes").setMaster("local[4]");
		JavaSparkContext context = new JavaSparkContext(conf);
		JavaRDD<String> rdd = context.textFile("D:\\tool\\words.txt");
		JavaRDD<Row> rowRdd = rdd.map(RowFactory::create);//java8 单一方法匿名类且输入参数平行移动完成方法调用
		//创造一列定义，列容器定义
		List<StructField> ls = Arrays.asList(DataTypes.createStructField("line", DataTypes.StringType, true));
		//结构类型定义
		StructType schema = DataTypes.createStructType(ls);
		//创建session,
		SparkSession  session = SparkSession.builder().appName("dataframes").config(conf).getOrCreate();
		Dataset<Row> set = session.createDataFrame(rowRdd, schema);
		set.show();
		Dataset<Row> set2 = set.filter(set.col("line").like("%#%"));
		System.out.println("result:" + set2.count());
		Dataset<Row> set3 = set2.filter(set.col("line").like("%a%"));
		System.out.println(set3.count());
		System.out.println(set3.collect());
		//数据库访问操作：
		databaseHandle(session);
	}

	private static void databaseHandle(SparkSession session) {
		Dataset<Row> rows = session.read().format("jdbc")
//				.jdbc(url, table, properties)
				.option("url", "jdbc:mysql://localhost:3306/mydatabases?user=root&password=lsp&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC")
				.option("dbtable", "user_roles")
				.load();
		rows.printSchema();
		Dataset<Row> couns = rows.groupBy("username").count();
		couns.show();
		couns.write().format("json").save("D:\\test\\" + System.currentTimeMillis() + "\\OK.txt");
	}
}
