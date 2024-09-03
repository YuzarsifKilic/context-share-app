package com.yuzarsif.contextshare.userservice;

import com.yuzarsif.contextshare.userservice.kafka.UserVerification;
import com.yuzarsif.contextshare.userservice.kafka.UserVerificationProducer;
import com.yuzarsif.contextshare.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
