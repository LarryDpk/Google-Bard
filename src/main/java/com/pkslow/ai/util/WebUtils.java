package com.pkslow.ai.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@Slf4j
public class WebUtils {
    @NotNull
    public static OkHttpClient okHttpClientWithTimeout(Duration timeout) {
        log.info("Creating OkHttpClient with timeout {}", timeout);
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(timeout)
                .readTimeout(timeout)
                .connectTimeout(timeout)
                .build();
        log.info("OkHttpClient created");
        return client;
    }
}
