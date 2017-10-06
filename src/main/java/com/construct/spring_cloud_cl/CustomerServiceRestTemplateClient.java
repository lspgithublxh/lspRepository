package com.construct.spring_cloud_cl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.construct.spring_cloud_cl.domain.Message;

@Component
public class CustomerServiceRestTemplateClient {

    
    private RestTemplate restTemplate = new RestTemplate();

    public String getCustomer(int id) {
    	Message customer = restTemplate.exchange(
                "http://customer-service/customer/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Message>() {
                },
                id).getBody();
    	return "success";
//        return new MessageWrapper<>(customer, "server called using eureka with rest template");

    }

}
