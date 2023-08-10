package com.pkslow.ai.util;

import com.pkslow.ai.domain.Answer;
import com.pkslow.ai.domain.AnswerStatus;
import com.pkslow.ai.domain.BardRequest;
import com.pkslow.ai.domain.BardResponse;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static com.pkslow.ai.util.Constants.BARD_VERSION;
import static com.pkslow.ai.util.Constants.HOSTNAME;

public class BardUtilsTest {
    private final String token = "xxx.xxx.token1;xxx.xxx.token2";
    @Test
    public void createBuilderWithBardHeader() {
        Request.Builder builder = BardUtils.createBuilderWithBardHeader(token);
        Assert.assertNotNull(builder);
    }

    @Test
    public void createRequestForSNlM0e() {
        Request request = BardUtils.createRequestForSNlM0e(token);
        Assert.assertNotNull(request);
        Assert.assertEquals("GET", request.method());
        Assert.assertEquals(HOSTNAME, request.header("HOST"));
    }

    @Test
    public void fetchSNlM0eFromBody() {
        String str = "\"LoQv7e\":false,\"Lz8rbb\":true,\"MT7f9b\":[],\"MuJWjd\":false,\"QrtxK\":\"0\",\"S06Grb\":\"111226985821558295512\",\"SNlM0e\":\"AFuTz6vZM6ljFX4ZoCj7nXmwSlrx:1684550912484\",";
        String result = BardUtils.fetchSNlM0eFromBody(str);
        Assert.assertEquals("AFuTz6vZM6ljFX4ZoCj7nXmwSlrx:1684550912484", result);
    }

    @Test
    public void genQueryStringParamsForAsk() {
        Map<String, String> result = BardUtils.genQueryStringParamsForAsk();
        Assert.assertEquals(BARD_VERSION, result.get("bl"));
        Assert.assertEquals("c", result.get("rt"));
    }

    @Test
    public void createHttpBuilderForAsk() {
        HttpUrl.Builder builder = BardUtils.createHttpBuilderForAsk();
        Assert.assertNotNull(builder);
    }

    @Test
    public void removeBackslash() {
        String str = "It's www.pkslow.com\\\\n built by LarryDpk";
        String result = BardUtils.removeBackslash(str);
        Assert.assertEquals("It's www.pkslow.com\n built by LarryDpk", result);
    }

    @Test
    public void createPostRequestForAsk() {
        BardRequest bardRequest = BardRequest.newEmptyBardRequest();
        bardRequest.setStrSNlM0e("xxx1");
        Request request = BardUtils.createPostRequestForAsk(token, bardRequest);
        Assert.assertNotNull(request);
        Assert.assertEquals("POST", request.method());
    }

    @Test
    public void renderBardResponseFromResponse() {
        String content = "[[\"wrb.fr\",null,\"[[\\\"The weather in Hong Kong is currently 80°F and mostly cloudy. There is a 50% chance of rain showers. The humidity is 75%. The wind is blowing from the southeast at 5 to 10 mph.\\\\r\\\\n\\\\r\\\\nThe forecast for the rest of the day is for scattered showers and thunderstorms. The high temperature will be 84°F and the low temperature will be 79°F.\\\\r\\\\n\\\\r\\\\nHere is a more detailed forecast for the next few days:\\\\r\\\\n\\\\r\\\\n* Saturday, May 20: Scattered thunderstorms, especially during the afternoon hours. High temperature of 84°F and low temperature of 79°F.\\\\r\\\\n* Sunday, May 21: A t-storm around in the morning. High temperature of 89°F and low temperature of 78°F.\\\\r\\\\n* Monday, May 22: Mostly sunny. High temperature of 88°F and low temperature of 77°F.\\\\r\\\\n* Tuesday, May 23: A t-storm possible in the afternoon. High temperature of 87°F and low temperature of 76°F.\\\\r\\\\n* Wednesday, May 24: Mostly sunny. High temperature of 86°F and low temperature of 75°F.\\\\r\\\\n* Thursday, May 25: A t-storm possible in the afternoon. High temperature of 85°F and low temperature of 74°F.\\\\r\\\\n* Friday, May 26: Mostly sunny. High temperature of 84°F and low temperature of 73°F.\\\"],[\\\"c_16a1ebebd783b200\\\",\\\"r_16a1ebebd783bcab\\\"],[[\\\"weather Hong Kong\\\",1]],[],[[\\\"rc_16a1ebebd783bda3\\\",[\\\"The weather in Hong Kong is currently 80°F and mostly cloudy. There is a 50% chance of rain showers. The humidity is 75%. The wind is blowing from the southeast at 5 to 10 mph.\\\\r\\\\n\\\\r\\\\nThe forecast for the rest of the day is for scattered showers and thunderstorms. The high temperature will be 84°F and the low temperature will be 79°F.\\\\r\\\\n\\\\r\\\\nHere is a more detailed forecast for the next few days:\\\\r\\\\n\\\\r\\\\n* Saturday, May 20: Scattered thunderstorms, especially during the afternoon hours. High temperature of 84°F and low temperature of 79°F.\\\\r\\\\n* Sunday, May 21: A t-storm around in the morning. High temperature of 89°F and low temperature of 78°F.\\\\r\\\\n* Monday, May 22: Mostly sunny. High temperature of 88°F and low temperature of 77°F.\\\\r\\\\n* Tuesday, May 23: A t-storm possible in the afternoon. High temperature of 87°F and low temperature of 76°F.\\\\r\\\\n* Wednesday, May 24: Mostly sunny. High temperature of 86°F and low temperature of 75°F.\\\\r\\\\n* Thursday, May 25: A t-storm possible in the afternoon. High temperature of 85°F and low temperature of 74°F.\\\\r\\\\n* Friday, May 26: Mostly sunny. High temperature of 84°F and low temperature of 73°F.\\\"],[]],[\\\"rc_16a1ebebd783b184\\\",[\\\"The weather in Hong Kong is currently 80°F and mostly cloudy. There is a 50% chance of rain showers and thunderstorms. The humidity is 75%. The wind is blowing from the south-southeast at 5 to 10 miles per hour.\\\\r\\\\n\\\\r\\\\nThe forecast for the rest of the day is for mostly cloudy skies with a chance of rain showers and thunderstorms. The high temperature will be 84°F and the low temperature will be 79°F. The humidity will remain high, at around 75%. The wind will continue to blow from the south-southeast at 5 to 10 miles per hour.\\\\r\\\\n\\\\r\\\\nThe forecast for the next few days is for mostly cloudy skies with a chance of rain showers and thunderstorms. The high temperatures will be in the mid-80s and the low temperatures will be in the mid-70s. The humidity will remain high, at around 75%. The wind will continue to blow from the south-southeast at 5 to 10 miles per hour.\\\"],[]],[\\\"rc_16a1ebebd783b565\\\",[\\\"The weather in Hong Kong is currently 80°F and mostly cloudy. There is a 50% chance of rain showers. The humidity is 75%. The wind is blowing from the south-southeast at 5 to 10 mph.\\\\r\\\\n\\\\r\\\\nThe forecast for the next few days is as follows:\\\\r\\\\n\\\\r\\\\n* Saturday, May 20: A t-storm around in the morning, then mostly sunny in the afternoon. High near 84°F. Chance of precipitation is 40%.\\\\r\\\\n* Sunday, May 21: Sunny, with a high near 89°F.\\\\r\\\\n* Monday, May 22: Mostly sunny, with a high near 87°F.\\\\r\\\\n* Tuesday, May 23: Sunny, with a high near 86°F.\\\\r\\\\n* Wednesday, May 24: Mostly sunny, with a high near 85°F.\\\\r\\\\n* Thursday, May 25: Sunny, with a high near 84°F.\\\\r\\\\n* Friday, May 26: Mostly sunny, with a high near 83°F.\\\"],[]]]]\"]]";
        BardResponse response = BardUtils.renderBardResponseFromResponse(content);
        Assert.assertNotNull(response);
        Assert.assertEquals("c_16a1ebebd783b200", response.getConversationId());
        Assert.assertEquals("r_16a1ebebd783bcab", response.getResponseId());
        Assert.assertEquals("rc_16a1ebebd783bda3", response.getChoiceId());

        Answer answer = response.getAnswer();
        Assert.assertNotNull(answer);
        Assert.assertEquals(AnswerStatus.OK, answer.getStatus());
        Assert.assertTrue(answer.getChosenAnswer().contains("The weather in Hong Kong is currently 80°F and mostly cloudy."));
//        Assert.assertEquals(3, answer.draftAnswers().size());
//        Assert.assertEquals(answer.getChosenAnswer(), answer.getdraftAnswers().get(0));

    }

}
