package com.yuzarsif.contextshare.notificationservice;

import com.yuzarsif.contextshare.notificationservice.email.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(EmailService emailService) {
		return args -> {
			emailService.sendSimpleMessage("yuzarsifkilic@gmail.com", "Yusuf", "Kilic");
			emailService.sendVerificationEmail("yuzarsifkilic@gmail.com", "Yusuf", "Kilic", 123456);
		};
	}

}
