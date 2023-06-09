package com.example.pzusersecurityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PzUserSecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzUserSecurityServiceApplication.class, args);
    }

}
