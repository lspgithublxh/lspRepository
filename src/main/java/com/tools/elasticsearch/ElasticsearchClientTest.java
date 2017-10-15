package com.tools.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 客户端
 *
 *@author lishaoping
 *ToolsTest
 *2017年10月15日
 */
public class ElasticsearchClientTest {

	static TransportClient client;
	
	public static void main(String[] args) throws UnknownHostException {
		client();
		putSource();
		getSource();
		querySource();
	}
	
	
	public static void client() throws UnknownHostException {
//		RestHighLevelClient 
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch")
						  .put("client.transport.sniff", true).build();
		client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		
	}
	
	public static void putSource() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "lishaoping");
		map.put("title", "lishaoping is a happy boy");
		map.put("age", 25);
		IndexResponse response = client.prepareIndex("company", "dept", "1").setSource(map).execute().actionGet();
		System.out.println(response.getResult().toString());
		System.out.println(response.toString());
	}
	
	public static void getSource() {
		GetResponse response = client.prepareGet("company", "dept", "1").execute().actionGet();
		System.out.println(response.toString());
	}

	public static void querySource() {
//		QueryBuilder queryBuilder = QueryBuilders.termQuery("age", 25);
//		QueryBuilder queryBuilder = QueryBuilders.rangeQuery("age").gt(21);
//		QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("age", 25);
		
//		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("lishaoping", "name");
		SearchResponse response = client.prepareSearch("company").setTypes("dept").setQuery(queryBuilder)
//									   .addSort("name", SortOrder.ASC)
//									   .setSize(20)
									   .execute()
									   .actionGet();
		SearchHits hits = response.getHits();
		for(SearchHit hit : hits) {
			System.out.println(hit.getSource());
		}
		
	}
}
