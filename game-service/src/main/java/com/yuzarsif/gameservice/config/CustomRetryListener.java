package com.yuzarsif.gameservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

@Slf4j
public class CustomRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        // Retry başlamadan önce
        log.info("Retry open attempt: " + context.getRetryCount());
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // Retry tamamlandığında
        log.info("Retry close attempt: " + context.getRetryCount());
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        // Her retry hatası olduğunda
        log.info("Retry error attempt: " + context.getRetryCount());
    }
}
