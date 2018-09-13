package com.forwf.center.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.forwf.center.base.controller.HttpRequestController;

/**
 * 即 在后台发送请求， 而不是在前端，并且可以自己验证结果是否正确   进行匹配
 * @ClassName:BaseApplicationTests
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月13日
 * @Version V1.0
 * @Package com.forwf.center.base
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseApplicationTests {

	private MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HttpRequestController()).build();
	}
	
	@Test
	public void contextLoads() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON_UTF8))//请求发送
					.andExpect(MockMvcResultMatchers.status().isOk())//期望 返回正确的
					.andDo(MockMvcResultHandlers.print())//获取到数据  的操作  是返回这个数据
					.andReturn();//返回
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
