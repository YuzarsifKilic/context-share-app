package com.yuzarsif.contextshare.notificationservice.kafka;

import com.yuzarsif.contextshare.notificationservice.kafka.user.UserVerification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceConsumer {

    @KafkaListener(topics = "user-verification", groupId = "user-service-consumer")
    public void consumeUserConfirmation(UserVerification userVerification) {
        log.info("Received user verification: {}", userVerification);
    }
}
