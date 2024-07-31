package com.powernode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CustomerServiceApplication {
    //客户。消费者
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

}
