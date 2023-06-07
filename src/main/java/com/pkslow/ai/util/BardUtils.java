package com.pkslow.ai.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pkslow.ai.domain.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pkslow.ai.util.Constants.*;

@Slf4j
public class BardUtils {

    public static Request.Builder createBuilderWithBardHeader(String token) {
        return new Request.Builder()
                .addHeader("Host", HOSTNAME)
                .addHeader("Content-Type", CONTENT_TYPE)
                .addHeader("X-Same-Domain", "1")
                .addHeader("User-Agent", USER_AGENT)
                .addHeader("Origin", BASE_URL)
                .addHeader("Referer", BASE_URL)
                .addHeader("Cookie", TOKEN_COOKIE_NAME + "=" + token);
    }

    public static Request createRequestForSNlM0e(String token) {
        Request.Builder headerBuilder = createBuilderWithBardHeader(token);
        return headerBuilder.url(BASE_URL)
                .build();
    }

    public static String fetchSNlM0eFromBody(String input) {
        Pattern p = Pattern.compile("SNlM0e\":\"(.*?)\"");
        Matcher m = p.matcher(input);
        if (m.find()) {
            String result = m.group();
            result = result.substring(9, result.length() - 1);
            return result;
        }
        return null;
    }

    @NotNull
    public static Map<String, String> genQueryStringParamsForAsk() {
        int randonNum = ThreadLocalRandom.current().nextInt(0, 10000);
        randonNum = randonNum + 100000;

        Map<String, String> params = new HashMap<>();
        params.put("bl", BARD_VERSION);
        params.put("_reqid", String.valueOf(randonNum));
        params.put("rt", "c");
        return params;
    }

    @NotNull
    public static HttpUrl.Builder createHttpBuilderForAsk() {
        Map<String, String> params = genQueryStringParamsForAsk();
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL + ASK_QUESTION_PATH)).newBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(), param.getValue());
        }
        return httpBuilder;
    }

    /**
     * remove backslash \ in answer string
     */
    public static String removeBackslash(String answerStr) {
        answerStr = answerStr.replace("\\\\n", "\n");
        answerStr = answerStr.replace("\\", "\"");
        return answerStr;
    }

    @NotNull
    public static Request createPostRequestForAsk(String token, BardRequest bardRequest) {
        HttpUrl.Builder httpBuilder = createHttpBuilderForAsk();
        RequestBody body = buildRequestBodyForAsk(bardRequest);
        Request.Builder headerBuilder = createBuilderWithBardHeader(token);
        return headerBuilder.url(httpBuilder.build())
                .method("POST", body)
                .build();
    }


    @NotNull
    public static RequestBody buildRequestBodyForAsk(BardRequest bardRequest) {
        String question = bardRequest.getQuestion().replace("\"", "\\\\\\\"");
        return new FormBody.Builder()
                .add("f.req", String.format("[null,\"[[\\\"%s\\\"],null,[\\\"%s\\\",\\\"%s\\\",\\\"%s\\\"]]\"]",
                        question, bardRequest.getConversationId(), bardRequest.getResponseId(), bardRequest.getChoiceId()))
                .add("at", bardRequest.getStrSNlM0e())
                .build();
    }

    public static BardResponse renderBardResponseFromResponse(String content) {
        Answer.AnswerBuilder builder = Answer.builder();
        Answer answer;
        String conversationId = "";
        String responseId = "";
        String choiceId = "";

        try {
            JsonArray jsonArray = new Gson().fromJson(content, JsonArray.class);

            JsonElement element3 = ((JsonArray) jsonArray.get(0)).get(2);
            String content3 = element3.getAsString();

            JsonArray jsonArray3 = new Gson().fromJson(content3, JsonArray.class);

            conversationId = ((JsonArray) jsonArray3.get(1)).get(0).getAsString();
            responseId = ((JsonArray) jsonArray3.get(1)).get(1).getAsString();

            String chosenAnswer = ((JsonArray) jsonArray3.get(0)).get(0).getAsString();
            chosenAnswer = removeBackslash(chosenAnswer);

            builder.chosenAnswer(chosenAnswer);

            choiceId = ((JsonArray) ((JsonArray) jsonArray3.get(4)).get(0)).get(0).getAsString();

            List<Image> images = new ArrayList<>();


            try {
                JsonArray imagesJson = (JsonArray) (((JsonArray) ((JsonArray) jsonArray3.get(4)).get(0)).get(4));

                for (int i = 0; i < imagesJson.size(); i++) {
                    JsonArray imageJson = (JsonArray) imagesJson.get(i);
                    String url = ((JsonArray)((JsonArray) imageJson.get(0)).get(0)).get(0).getAsString();
                    String markdownLabel = imageJson.get(2).getAsString();
                    String articleURL =  ((JsonArray)((JsonArray) imageJson.get(1)).get(0)).get(0).getAsString();

                    Image image = new Image(url, markdownLabel, articleURL);
//                    log.debug("Received image: {}", image);
                    images.add(image);
                }

            } catch (Exception e) {
                log.info("No image");
            }


            builder.images(images);

        } catch (Exception e) {
            builder.status(AnswerStatus.NO_ANSWER);
            answer = builder.build();
            return new BardResponse(conversationId, responseId, choiceId, answer);
        }

        builder.status(AnswerStatus.OK);
        answer = builder.build();
        return new BardResponse(conversationId, responseId, choiceId, answer);
    }


    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


}
