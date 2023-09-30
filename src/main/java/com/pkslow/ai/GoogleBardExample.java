package com.pkslow.ai;

import com.pkslow.ai.domain.Answer;
import com.pkslow.ai.domain.AnswerStatus;
import com.pkslow.ai.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class GoogleBardExample {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token, Duration.ofMinutes(10));

        printChosenAnswer(client.ask("Can you show me a picture of Pumpkin?\n How about cat?"));
    }

    private static void printChosenAnswer(Answer answer) {
        if (answer.getStatus() == AnswerStatus.OK) {
            log.info("Markdown Output: \n {}", answer.markdown());
        } else {
            log.info("No Answer: {}", answer);
        }
    }
}