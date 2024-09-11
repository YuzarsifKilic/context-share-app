package com.yuzarsif.contextshare.notificationservice.email;

import com.yuzarsif.contextshare.notificationservice.kafka.review.ReviewNotification;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendVerificationEmail(String email, String firstName, String lastName, Integer code) throws MessagingException, IOException {
        Map<String, Object> templateModel = Map.of(
                "user_name", firstName + " " + lastName,
                "code", code);

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlContent = templateEngine.process("user-verification", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Verify Account");
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    public void sendReviewNotification(ReviewNotification reviewNotification) throws MessagingException, IOException, TemplateException {
        Map<String, Object> templateModel = Map.of(
                "user_name", reviewNotification.firstName() + " " + reviewNotification.lastName(),
                "context_name", reviewNotification.contextName(),
                "reply", reviewNotification.review());

        Context context = new Context();
        context.setVariables(templateModel);

        String htmlContent = templateEngine.process("review-reply", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(reviewNotification.email());
        helper.setSubject("Reply Your Comment");
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
}
