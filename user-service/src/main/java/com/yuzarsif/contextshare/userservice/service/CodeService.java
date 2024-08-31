package com.yuzarsif.contextshare.userservice.service;

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

    public void createCode(String userId) {
        Random random = new Random();
        Code code = Code
                .builder()
                .userId(userId)
                .code(100000 + random.nextInt(900000))
                .build();

        // TODO: send email

        codeRepository.save(code);
    }

    public void deleteCodes() {
        codeRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusHours(1));
    }
}
