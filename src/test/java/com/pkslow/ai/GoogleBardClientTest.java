package com.pkslow.ai;

import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

import static com.pkslow.ai.util.WebUtils.okHttpClientWithTimeout;

public class GoogleBardClientTest {
    @Test
    public void constructor() {
        AIClient client = new GoogleBardClient("token");
        Assert.assertNotNull(client);

        client = new GoogleBardClient("token", Duration.ofMinutes(10));
        Assert.assertNotNull(client);

        OkHttpClient httpClient = okHttpClientWithTimeout(Duration.ofMinutes(10));
        client = new GoogleBardClient("token", httpClient);
        Assert.assertNotNull(client);
    }

}
