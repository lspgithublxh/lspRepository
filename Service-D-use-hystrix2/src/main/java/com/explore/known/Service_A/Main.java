package com.explore.known.Service_A;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient 
@EnableHystrix
@EnableHystrixDashboard
public class Main 
{
    public static void main( String[] args )
    {
        SpringApplication.run(Main.class, args);
    }
}
