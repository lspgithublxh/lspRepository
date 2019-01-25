package com.bj58.fang.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitTest {

	public static void main(String[] args) {
		get();
		
	}

	private static void get() {
		Retrofit fit = new Retrofit.Builder().client(new OkHttpClient())
		.baseUrl("http://10.8.9.202:8001")//注解会追加
		.build();
		HttpAPI api = fit.create(HttpAPI.class);
		Call<ResponseBody> body = api.getBody();
		try {
			ResponseBody b = body.execute().body();
			System.out.println(b.string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
