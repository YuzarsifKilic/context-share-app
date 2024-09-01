package com.yuzarsif.contextshare.userservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserVerificationProducer {

    private final KafkaTemplate<String, UserVerification> kafkaTemplate;

    public void produceUserVerification(UserVerification userVerification) {
        log.info("Sending user verification: {}", userVerification);
        Message<UserVerification> message = MessageBuilder
                .withPayload(userVerification)
                .setHeader(TOPIC, "user-verification")
                .build();
        kafkaTemplate.send(message);
    }
}
