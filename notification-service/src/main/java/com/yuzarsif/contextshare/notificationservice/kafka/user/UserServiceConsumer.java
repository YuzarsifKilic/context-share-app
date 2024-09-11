package com.yuzarsif.contextshare.notificationservice.kafka.user;

import com.yuzarsif.contextshare.notificationservice.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "user-verification", groupId = "user-service-consumer")
    public void consumeUserConfirmation(UserVerification userVerification) throws MessagingException, IOException {
        log.info("Received user verification: {}", userVerification);
        emailService.sendVerificationEmail(userVerification.email(), userVerification.firstName(), userVerification.lastName(), userVerification.code());
    }
}
