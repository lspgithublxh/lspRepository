package com.tools.spring_boot;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController  
//@SpringBootApplication  
//  
//public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{  
//  
//    @Override  
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
//        return builder.sources(Application.class);  
//    }  
//  
//    @RequestMapping("/")  
//    public String index() {  
//        return "Index Page";  
//    }  
//  
//    @RequestMapping("/hello")  
//    public String hello() {  
//  
//        return "Hello World!";  
//    }  
//  
//    public static void main(String[] args) {  
//        SpringApplication.run(Application.class, args);  
//    }  
//  
//    @Override  
//    public void customize(ConfigurableEmbeddedServletContainer container) {  
//        container.setPort(8080);  
//    }  
//}  