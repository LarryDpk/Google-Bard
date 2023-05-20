package com.pkslow.ai;

import com.google.common.io.Files;
import com.pkslow.ai.domain.Answer;
import com.pkslow.ai.domain.AnswerStatus;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;

import static com.pkslow.ai.util.WebUtils.okHttpClientWithTimeout;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void ask() throws IOException {
        OkHttpClient httpClient = mock(OkHttpClient.class);
        AIClient client = new GoogleBardClient("token", httpClient);

        ClassLoader classLoader = getClass().getClassLoader();
        File sNlM0eFile = new File(classLoader.getResource("sNlM0e-response-body.txt").getFile());
        File answerFile = new File(classLoader.getResource("answer-body.json").getFile());
        String sNlM0eBody = Files.toString(sNlM0eFile, Charset.defaultCharset());
        String answerBody = Files.toString(answerFile, Charset.defaultCharset());


        // sNlM0e part
        Call sNlM0eCall = mock(Call.class);
        Response sNlM0eResponse = mock(Response.class, RETURNS_DEEP_STUBS);
        when(sNlM0eCall.execute()).thenReturn(sNlM0eResponse);
        when(sNlM0eResponse.code()).thenReturn(200);
        when(sNlM0eResponse.body().string()).thenReturn(sNlM0eBody);


        // answer part
        Call answerCall = mock(Call.class);
        Response answerResponse = mock(Response.class, RETURNS_DEEP_STUBS);
        when(answerCall.execute()).thenReturn(answerResponse);
        when(answerResponse.code()).thenReturn(200);
        when(answerResponse.body().string()).thenReturn(answerBody);


        // mvn run test with keep return sNlM0eCall, no idea
//        when(httpClient.newCall(any(Request.class))).thenReturn(sNlM0eCall, answerCall);

        // https://stackoverflow.com/questions/8088179/using-mockito-with-multiple-calls-to-the-same-method-with-the-same-arguments
        when(httpClient.newCall(any(Request.class))).thenAnswer(new org.mockito.stubbing.Answer<Call>() {
            private int count = 0;

            public Call answer(InvocationOnMock invocation) {
                if (count++ == 1)
                    return sNlM0eCall;

                return answerCall;
            }
        });


        Answer answer = client.ask("what is the time now in Hong Kong?");
        Assert.assertEquals(AnswerStatus.OK, answer.status());
        Assert.assertTrue(answer.chosenAnswer().contains("The time in Hong Kong is currently 03:28 PM on Saturday"));
    }

}
