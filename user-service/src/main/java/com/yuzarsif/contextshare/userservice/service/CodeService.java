package com.yuzarsif.contextshare.userservice.service;

import com.yuzarsif.contextshare.userservice.kafka.UserVerification;
import com.yuzarsif.contextshare.userservice.kafka.UserVerificationProducer;
import com.yuzarsif.contextshare.userservice.model.Code;
import com.yuzarsif.contextshare.userservice.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;
    private final UserVerificationProducer userVerificationProducer;

    public void createCode(String email) {
        Random random = new Random();
        Code code = Code
                .builder()
                .email(email)
                .code(100000 + random.nextInt(900000))
                .build();

        userVerificationProducer.produceUserVerification(new UserVerification(email, code.getCode()));

        codeRepository.save(code);
    }

    public void deleteCodes() {
        codeRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusHours(1));
    }
}
