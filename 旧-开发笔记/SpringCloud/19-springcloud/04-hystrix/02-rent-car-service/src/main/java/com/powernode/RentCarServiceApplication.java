package com.powernode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RentCarServiceApplication {

    //提供租车服务。生产者
    public static void main(String[] args) {
        SpringApplication.run(RentCarServiceApplication.class, args);
    }

}
