package com.yuzarsif.contextshare.reviewservice.kafka;

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
public class ReplyProducer {

    private final KafkaTemplate<String, ReviewNotification> kafkaTemplate;

    public void produceReviewNotification(ReviewNotification reviewNotification) {
        log.info("Sending review verification: {}", reviewNotification);
        Message<ReviewNotification> message = MessageBuilder
                .withPayload(reviewNotification)
                .setHeader(TOPIC, "review-notification")
                .build();
        kafkaTemplate.send(message);
    }
}
