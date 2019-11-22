package com.ltd.e.recommend.config.Main;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RandomPortTest {
 
  @Autowired
  private TestRestTemplate restTemplate;
 
  public void testHello() {
    // 测试hello方法
    String result = restTemplate.getForObject("/hello", String.class);
    assertEquals("Hello World", result);
  }
}