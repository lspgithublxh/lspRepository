package com.construct.spark;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class DataFrameHandle {

	public static void main(String[] args) throws AnalysisException {
		handle1();
	}

	private static void handle1() throws AnalysisException {
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
		//机器学习操作：
//		mlLibAlgorithm(rowRdd, session, context);
	}

	private static void databaseHandle(SparkSession session) throws AnalysisException {
		Dataset<Row> rows = session.read().format("jdbc")
//				.jdbc(url, table, properties)
				.option("url", "jdbc:mysql://localhost:3306/mydatabases?user=root&password=lsp&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC")
				.option("dbtable", "user_roles")
				.load();
		rows.printSchema();
		rows.groupBy("username").count().show();
		rows.show();
		rows.select(rows.col("username").substr(0, 4)).show();
		rows.filter(rows.col("username").like("%admin%")).groupBy(rows.col("username")).count();
//		session.sql("select count(1) from user_roles").show();
		rows.createOrReplaceTempView("user_ros");
		session.sql("select count(1) from user_ros").show();
		rows.createGlobalTempView("user_so");
		session.sql("select count(1) from global_temp.user_so ").show();
//		couns.write().format("json").save("D:\\test\\" + System.currentTimeMillis() + "\\OK.txt");
	}
	
	/**
	 * 暂时不碰机器学习
	 *@author lishaoping
	 *BigData
	 *2017年11月5日
	 * @param rowRdd
	 * @param session
	 * @param context
	 */
	private static void mlLibAlgorithm(JavaRDD<Row> rowRdd, SparkSession session, JavaSparkContext context) {
		List<Double> list = Arrays.asList(new Double[] {2.11,2.232,6.6565,23.78,2323.2322,23.454,3434.12,2323.2322,23.454,3434.12,2323.2322,23.454,3434.12,2323.2322,23.454,3434.12});
		rowRdd = context.parallelize(list).map(RowFactory::create);
		StructType type = new StructType(new StructField[] {new StructField("label", DataTypes.DoubleType, true, Metadata.empty()),
														new StructField("features",new VectorUDT(), true, Metadata.empty())});
		Dataset<Row> rows = session.createDataFrame(rowRdd, type);
		LogisticRegression regression = new LogisticRegression().setMaxIter(10);
		LogisticRegressionModel model = regression.fit(rows);
		Vector v1 = model.coefficients();
		Vector v2 = model.interceptVector();
		System.out.println("v1:" + v1.toString());
		System.out.println("v2:" + v2.toString());
		model.transform(rows).show();
		
	}
}
