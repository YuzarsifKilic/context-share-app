package com.yuzarsif.contextshare.userservice.service;

import com.yuzarsif.contextshare.userservice.kafka.UserVerification;
import com.yuzarsif.contextshare.userservice.kafka.UserVerificationProducer;
import com.yuzarsif.contextshare.userservice.model.Code;
import com.yuzarsif.contextshare.userservice.model.Purpose;
import com.yuzarsif.contextshare.userservice.model.User;
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

    public void createCode(User user, Purpose purpose) {
        Random random = new Random();
        Code code = Code
                .builder()
                .email(user.getEmail())
                .code(100000 + random.nextInt(900000))
                .purpose(purpose)
                .build();

        userVerificationProducer.produceUserVerification(new UserVerification(user.getEmail(), user.getFirstName(), user.getLastName(), code.getCode()));

        codeRepository.save(code);
    }

    protected Code findCode(String email) {
        return codeRepository
                .findByEmail(email)
                .orElse(null);
    }

    public void deleteCodes() {
        codeRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusHours(1));
    }
}
