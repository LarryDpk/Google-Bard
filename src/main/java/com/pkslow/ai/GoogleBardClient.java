package com.pkslow.ai;

import com.pkslow.ai.domain.Answer;
import com.pkslow.ai.domain.AnswerStatus;
import com.pkslow.ai.domain.BardRequest;
import com.pkslow.ai.domain.BardResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static com.pkslow.ai.domain.BardRequest.DEFAULT_REQUEST;
import static com.pkslow.ai.util.BardUtils.*;
import static com.pkslow.ai.util.WebUtils.okHttpClientWithTimeout;

@Slf4j
public class GoogleBardClient implements AIClient {
    private final String token;

    private final OkHttpClient httpClient;

    private final BardRequest bardRequest = DEFAULT_REQUEST;


    public GoogleBardClient(String token) {
        this(token, Duration.ofMinutes(5));
    }

    public GoogleBardClient(String token, Duration timeout) {
        this.token = token;
        this.httpClient = okHttpClientWithTimeout(timeout);
    }

    public GoogleBardClient(String token, OkHttpClient httpClient) {
        this.token = token;
        this.httpClient = httpClient;
    }


    @Override
    public Answer ask(String question) {
        Answer answer;
        try {

            if (isEmpty(bardRequest.getStrSNlM0e())) {
                bardRequest.setStrSNlM0e(callBardToGetSNlM0e());
            }

            bardRequest.setQuestion(question);

            String response = callBardToAsk(bardRequest);
            answer = processAskResult(response);
        } catch (Throwable e) {
            e.printStackTrace();
            Answer.AnswerBuilder builder = Answer.AnswerBuilder.anAnswer();
            return builder.status(AnswerStatus.ERROR).build();
        }

        return answer;
    }

    private String callBardToGetSNlM0e() {
        Call call = this.httpClient.newCall(createRequestForSNlM0e(token));
        try {
            try (Response response = call.execute()) {
                log.info("getSNlM0e Response code: " + response.code());
                String responseString = Objects.requireNonNull(response.body()).string();
                return fetchSNlM0eFromBody(responseString);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String callBardToAsk(BardRequest bardRequest) {
        log.info("calling Bard with request {}", bardRequest);
        Request request = createPostRequestForAsk(token, bardRequest);

        Call call = this.httpClient.newCall(request);
        try {
            try (Response response = call.execute()) {
                int statusCode = response.code();
                log.info("Ask Response code: " + statusCode);
                String responseString = Objects.requireNonNull(response.body()).string();
                if (statusCode != 200) {
                    throw new IllegalStateException("Can't get the answer");
                }
                String result = responseString.split("\\n")[3];
                log.debug("Raw answers length: {}", result.length());
//            log.debug("Result from Bard: {}", result);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Answer processAskResult(String content) {
        BardResponse bardResponse = renderBardResponseFromResponse(content);

        bardRequest.setConversationId(bardResponse.getConversationId());
        bardRequest.setResponseId(bardResponse.getResponseId());
        bardRequest.setChoiceId(bardResponse.getChoiceId());

        return bardResponse.getAnswer();
    }


    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
