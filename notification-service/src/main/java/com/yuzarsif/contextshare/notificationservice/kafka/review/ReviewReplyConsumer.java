package com.yuzarsif.contextshare.notificationservice.kafka.review;

import com.yuzarsif.contextshare.notificationservice.email.EmailService;
import com.yuzarsif.contextshare.notificationservice.kafka.user.UserVerification;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewReplyConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "review-notification", groupId = "review-group")
    public void consumeReviewReply(ReviewNotification reviewNotification) throws MessagingException, IOException, TemplateException {
        log.info("Received review reply verification: {}", reviewNotification);
        emailService.sendReviewNotification(reviewNotification);
    }
}
