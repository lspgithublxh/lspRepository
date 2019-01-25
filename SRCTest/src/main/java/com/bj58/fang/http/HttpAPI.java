package com.bj58.fang.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HttpAPI {

	@GET("/api/infodetail?infoid=36360440111364&openid=oIArb4jVPt_DapBBzxXxH5l1YE70")
	public Call<ResponseBody> getBody();
}
