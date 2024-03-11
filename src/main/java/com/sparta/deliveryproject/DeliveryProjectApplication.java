package com.sparta.deliveryproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DeliveryProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryProjectApplication.class, args);
    }

}