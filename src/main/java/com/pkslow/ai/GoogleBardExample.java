package com.pkslow.ai;


import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class GoogleBardExample {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token, Duration.ofMinutes(10));

        Answer answer = client.ask("how to be a good father?");

        StringBuilder sb = new StringBuilder();

        if (answer.status() == AnswerStatus.OK) {
            sb.append("\n### Chosen Answer\n");
            sb.append(answer.chosenAnswer());
            for (int i = 0; i < answer.draftAnswers().size(); i++) {
                sb.append("\n### Draft Answer ").append(i).append("\n");
                sb.append(answer.draftAnswers().get(i));
            }
            log.info("Output: \n {}", sb);
        }

    }
}