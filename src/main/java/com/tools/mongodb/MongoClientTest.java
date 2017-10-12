package com.tools.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoClientTest {

	MongoClient client = new MongoClient("localhost", 27017);
	
	public static void main(String[] args) {
//		new MongoClientTest().connect();
		new MongoClientTest().query();
	}
	
	/**
	 * ���ӺͲ����ĵ�
	 *@author lishaoping
	 *ToolsTest
	 *2017��10��12��
	 */
	public void connect() {
		
		//mongooptions��ʽ
//		MongoClient cl = new MongoClient(host, options)
		MongoDatabase database = client.getDatabase("business");
		database.createCollection("product");
		MongoCollection<Document> col = database.getCollection("product");
		Document doc = new Document("name", "book");
		doc.append("price", 23.23);
		doc.append("author", "lishaoping");
		doc.append("title", "��������");
		List<Document> list = new ArrayList<>();
		list.add(doc);
		col.insertMany(list);
	}
	
	public void query() {
		MongoDatabase database = client.getDatabase("business");
		MongoCollection<Document> col = database.getCollection("product");
		System.out.println(col.count());
//		DBObject obj = new BasicDBObject();
		Document doc = new Document("name", "book");
		
		FindIterable<Document> docs = col.find(doc);
		for(Document d : docs) {
			System.out.println(d);
		}
	}
}
