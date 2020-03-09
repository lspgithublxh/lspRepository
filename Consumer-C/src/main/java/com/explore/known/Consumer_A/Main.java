package com.explore.known.Consumer_A;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.explore.known.Consumer_A.interfaces")
public class Main 
{
    public static void main( String[] args )
    {
        SpringApplication.run(Main.class, args);
    }
}
