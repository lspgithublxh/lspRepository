package com.scrapy.test;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

/**
 * 参考文档：https://www.cnblogs.com/sa-dan/p/6836055.html
 * @ClassName:MongodTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年11月5日
 * @Version V1.0
 * @Package com.scrapy.test
 */
public class MongodTest {

	private static MongoClient client = new MongoClient("localhost", 27017);

	
	public static MongoClient getClient() {
		Builder builder = MongoClientOptions.builder();
		builder.cursorFinalizerEnabled(true);
		builder.connectionsPerHost(300);
		builder.connectTimeout(3000);
		builder.maxWaitTime(3000);
		builder.socketTimeout(0);
		builder.threadsAllowedToBlockForConnectionMultiplier(5000);
		builder.writeConcern(WriteConcern.SAFE);
		builder.build();
		return client;
	}
	
	public static void main(String[] args) {
		test();
	}

	
	private static void test() {
		MongoClient client = new MongoClient("localhost", 27017);
		Builder builder = MongoClientOptions.builder();
		builder.cursorFinalizerEnabled(true);
		builder.connectionsPerHost(300);
		builder.connectTimeout(3000);
		builder.maxWaitTime(3000);
		builder.socketTimeout(0);
		builder.threadsAllowedToBlockForConnectionMultiplier(5000);
		builder.writeConcern(WriteConcern.SAFE);
		
		builder.build();
		
//		client.getDatabase("detail").createCollection("test");
		//
		MongoCollection<Document> col = client.getDatabase("detail").getCollection("test");
//		Document d = find(col, "5bdffc5e722b443040af1576");
//		System.out.println(d.toJson());
//		insert(col);
		//两个字段，可以有一个没有，也可以一个也没有：：纯粹就是查询全部，切返回两个字段而已
		FindIterable<Document> du = col.find().projection(Filters.and(Filters.eq("house", 1), Filters.eq("pics", 1)));
		MongoCursor<Document> it = du.iterator();
		int i = 0;
		while(it.hasNext()) {
			Document document = it.next();
			System.out.println(document.toJson());
			System.out.println(i++);
		}
	}
	
	private static void insert(MongoCollection<Document> col) {
		Document one = new Document();//可以map转换而来
		one.append("name", "lsp");
		col.insertOne(one);
	}


	private static Document find(MongoCollection<Document> col, String id) {
		ObjectId id_ = new ObjectId(id);
		FindIterable<Document> iterator = col.find(Filters.eq("_id", id_));
		Document d = iterator.first();
		//coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
		return d;
	}
	
	


	@Test
	public void junit() {
		
	}
}
