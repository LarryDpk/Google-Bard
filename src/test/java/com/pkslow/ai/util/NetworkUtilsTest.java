package com.pkslow.ai.util;

import org.junit.Assert;
import org.junit.Test;

public class NetworkUtilsTest {
    @Test
    public void setProxy() {
        NetworkUtils.setUpProxy("www.pkslow.com", "8080");
        Assert.assertEquals(System.getProperty("https.proxyHost"), "www.pkslow.com");
        Assert.assertEquals(System.getProperty("https.proxyPort"), "8080");

    }
}
