package com.construct.spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.construct.model.MyAggregate;
import com.construct.model.Person;

public class DatasetCreateAndSql {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("class").setMaster("local[1]");
		SparkSession session = SparkSession.builder().config(conf).getOrCreate();
//		createFromRDDndClass(session);
//		createFromRDDndStructType(session);
//		createFromJSONthenStatistic(session);
		//3.TypedColumn实例省略，类似上
		//数据格式的转换
		createFromParquet(session);
	}

	private static void createFromParquet(SparkSession session) {
		Dataset<Row> rows = session.read().parquet("D:\\test\\text\\users.parquet");
		rows.show();
		rows.select("name","favorite_color").write().save("D:\\test\\text\\3.parquet");
		rows.select("name").write().format("json").save("D:\\test\\text\\3.json");
		Dataset<Row> d = session.read().parquet("D:\\test\\text\\2.parquet");
		System.out.println("--------show--------------");
		d.show();
	}

	private static void createFromJSONthenStatistic(SparkSession session) {
		Dataset<Row> row = session.read().json("D:\\test\\text\\emp.json");
		row.createOrReplaceTempView("people");
		Dataset<Row> rows = session.sql("select * from people");
		rows.show();
		session.udf().register("myaggregete", new MyAggregate());
		Dataset<Row> rows2 = session.sql("select myaggregete(salary) from people");
		rows2.show();
	}

	private static void createFromRDDndStructType(SparkSession session) {
		JavaRDD<String>  rdd1 = session.read().textFile("D:\\test\\text\\people.txt").toJavaRDD();
		String cols = "name age";
		List<StructField> list = new ArrayList<>();
		for(String col : cols.split("\\s+")) {
			StructField field = DataTypes.createStructField(col, DataTypes.StringType, true);
			list.add(field);
		}
		StructType type = DataTypes.createStructType(list);
		JavaRDD<Row> rows = rdd1.map((Function<String, Row>) s -> {
			String[] str = s.split(",");
			return RowFactory.create(str[1], str[0]);
		});
		Dataset<Row> dset = session.createDataFrame(rows, type);
		dset.show();
		dset.createOrReplaceTempView("people");
		Dataset<Row> res = session.sql("select * from people  where age > 40");
		res.show();
		//再从结果row set结果集中取数据
		Dataset<String> res2 = res.map((MapFunction<Row, String>) row -> row.getAs("name"), Encoders.STRING());
		res2.show();
	}

	private static void createFromRDDndClass(SparkSession session) {
		JavaRDD<Person> rdd = session.read().textFile("D:\\test\\text\\people.txt")
				.javaRDD().map(s ->{
					Person p = new Person();
					String[] arr = s.split(",");
					p.setAge(Integer.valueOf(arr[0]));
					p.setName(arr[1]);
					return p;
				});
		Dataset<Row> rows = session.createDataFrame(rdd, Person.class);
		rows.show();
		rows.createOrReplaceTempView("person");
		Dataset<Row> rest = session.sql("select name from person where age < 40");
		rest.show();
		//2.从表格中取出行
		Encoder<String> encoder = Encoders.STRING();
		//dataset的map方法,表格的map-reduce
		Dataset<String> strSet = rest.map((MapFunction<Row, String>)row -> row.getString(0), encoder);
		strSet.show();
		strSet = rest.map((MapFunction<Row, String>) row -> row.getAs("name"), encoder);
		strSet.show();
	}
}
