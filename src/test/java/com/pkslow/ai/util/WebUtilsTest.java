package com.pkslow.ai.util;

import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class WebUtilsTest {
    @Test
    public void createOKHttpClient() {
        OkHttpClient okHttpClient = WebUtils.okHttpClientWithTimeout(Duration.ofMinutes(10));
        Assert.assertNotNull(okHttpClient);
        int timeout = okHttpClient.callTimeoutMillis();
        Assert.assertEquals(10 * 60 * 1000, timeout);
    }

}
