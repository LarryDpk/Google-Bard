package com.pkslow.ai;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pkslow.ai.Constants.*;

public class GoogleBardClient implements AIClient {
    private final String token;

    public GoogleBardClient(String token) {
        this.token = token;
    }

    @Override
    public Answer ask(String question) {
        Answer answer = null;
        try {
            String strSNlM0e = getSNlM0e();
            String response = ask(strSNlM0e, question);
            answer = processAskResult(response);
        } catch (Throwable e) {
            e.printStackTrace();
            Answer.AnswerBuilder builder = Answer.AnswerBuilder.anAnswer();
            return builder.status(AnswerStatus.ERROR).build();
        }

        return answer;
    }


    private Answer processAskResult(String content) {
        JsonArray jsonArray = new Gson().fromJson(content, JsonArray.class);

        JsonElement element3 = ((JsonArray) jsonArray.get(0)).get(2);
        String content3 = element3.getAsString();

        JsonArray jsonArray3 = new Gson().fromJson(content3, JsonArray.class);

        List<String> results = new ArrayList<>();

        String chosenAnswer = ((JsonArray) jsonArray3.get(0)).get(0).getAsString();
        chosenAnswer = resultRender(chosenAnswer);

        Answer.AnswerBuilder builder = Answer.AnswerBuilder.anAnswer();

        builder.chosenAnswer(chosenAnswer);

        try {
            for (int i = 0; i < 3; i++) {
                String oneDraftAnswer = ((JsonArray) ((JsonArray) jsonArray3.get(4)).get(i)).get(1).getAsString();
                oneDraftAnswer = resultRender(oneDraftAnswer);
                results.add(oneDraftAnswer);
            }
        } catch (Exception e) {
            System.out.println("No right answer...");
            builder.status(AnswerStatus.NO_ANSWER);
            return builder.build();

        }
        builder.draftAnswers(results);
        builder.status(AnswerStatus.OK);

        return builder.build();
    }

    private static String resultRender(String answerStr) {
        answerStr = answerStr.replace("\\\\n", "\n");
        answerStr = answerStr.replace("\\", "\"");
        return answerStr;
    }

    private String ask(String strSNlM0e, String question) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.MINUTES)
                .build();

        Request request = postRequestForAsk(strSNlM0e, question);

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            int statusCode = response.code();
            System.out.println("Ask Response code: " + statusCode);
            String responseString = Objects.requireNonNull(response.body()).string();
            if(statusCode != 200) {
                throw new IllegalStateException("Can't get the answer");
            }
            String result = responseString.split("\\n")[3];
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @NotNull
    private Request postRequestForAsk(String strSNlM0e, String question) {
        HttpUrl.Builder httpBuilder = httpBuilderForAsk();
        RequestBody body = requestBodyForAsk(strSNlM0e, question);
        Request.Builder headerBuilder = builderWithHeader();
        Request request = headerBuilder.url(httpBuilder.build())
                .method("POST", body)
                .build();
        return request;
    }

    @NotNull
    private static HttpUrl.Builder httpBuilderForAsk() {
        Map<String, String> params = paramsForAsk();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(BASE_URL + ASK_QUESTION_PATH).newBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(), param.getValue());
        }
        return httpBuilder;
    }

    @NotNull
    private static RequestBody requestBodyForAsk(String strSNlM0e, String question) {
        RequestBody body = new FormBody.Builder()
                .add("f.req", "[null,\"[[\\\"" + question + "\\\"],null,[\\\"\\\",\\\"\\\",\\\"\\\"]]\"]")
                .add("at", strSNlM0e)
                .build();
        return body;
    }

    @NotNull
    private static Map<String, String> paramsForAsk() {
        int randonNum = ThreadLocalRandom.current().nextInt(0, 10000);
        randonNum = randonNum + 100000;

        Map<String, String> params = new HashMap<>();
        params.put("bl", "boq_assistant-bard-web-server_20230315.04_p2");
        params.put("_reqid", String.valueOf(randonNum));
        params.put("rt", "c");
        return params;
    }


    private String getSNlM0e() {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(120, TimeUnit.SECONDS)
                .build();
        Call call = client.newCall(requestForSNlM0e());
        try {
            Response response = call.execute();
            System.out.println("getSNlM0e Response code: " + response.code());

            String responseString = Objects.requireNonNull(response.body()).string();
            return regexSNlM0e(responseString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Request requestForSNlM0e() {
        Request.Builder headerBuilder = builderWithHeader();
        return headerBuilder.url(BASE_URL)
                .build();
    }

    private static String regexSNlM0e(String input) {
        Pattern p = Pattern.compile("SNlM0e\":\"(.*?)\"");
        Matcher m = p.matcher(input);
        while (m.find()) {
            String result = m.group();
            result = result.substring(9, result.length() - 1);
            return result;
        }
        return null;
    }

    private Request.Builder builderWithHeader() {
        return new Request.Builder()
                .addHeader("Host", HOSTNAME)
                .addHeader("Content-Type", CONTENT_TYPE)
                .addHeader("X-Same-Domain", "1")
                .addHeader("User-Agent", USER_AGENT)
                .addHeader("Origin", BASE_URL)
                .addHeader("Referer", BASE_URL)
                .addHeader("Cookie", TOKEN_COOKIE_NAME + "=" + token);
    }
}
