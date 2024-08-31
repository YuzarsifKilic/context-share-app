package com.yuzarsif.contextshare.userservice.scheduler;

import com.yuzarsif.contextshare.userservice.service.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CodeScheduler {

    private final CodeService codeService;

    @Scheduled(fixedDelay = 600000)
    public void deleteCodes() {
        log.info("Deleting codes");
        codeService.deleteCodes();
        log.info("Codes deleted");
    }

}
