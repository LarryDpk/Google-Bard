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
    public List<String> ask(String question) {
        String strSNlM0e = getSNlM0e();
        String response = ask(strSNlM0e, question);
        return processAskResult(response);
    }


    private List<String> processAskResult(String content) {
        JsonArray jsonArray = new Gson().fromJson(content, JsonArray.class);
        JsonElement object = ((JsonArray) jsonArray.get(0)).get(2);
        content = object.getAsString();

        jsonArray = new Gson().fromJson(content, JsonArray.class);

        List<String> results = new ArrayList<>();

        String rr = ((JsonArray) jsonArray.get(0)).get(0).getAsString();
        rr = resultRender(rr);
        results.add(rr);

        try {
            for (int i = 1; i < 3; i++) {
                String oneResult = ((JsonArray) ((JsonArray) jsonArray.get(4)).get(i)).get(1).getAsString();
                oneResult = resultRender(oneResult);
                results.add(oneResult);
            }
        } catch (Exception e) {
            System.out.println("No right answer...");
            results.add("No right answer...");
            results.add("No right answer...");
            results.add("No right answer...");
        }


        return results;
    }

    private static String resultRender(String content) {
        content = content.replace("\\\\n", "\n");
        content = content.replace("\\", "\"");
        return content;
    }

    private String ask(String strSNlM0e, String question) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(120, TimeUnit.SECONDS)
                .build();

        Request request = postRequestForAsk(strSNlM0e, question);

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            int statusCode = response.code();
            System.out.println("Response code: " + statusCode);
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
            System.out.println("Response code: " + response.code());

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
            System.out.println(result);
            result = result.substring(9, result.length() - 1);
            System.out.println(result);
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
