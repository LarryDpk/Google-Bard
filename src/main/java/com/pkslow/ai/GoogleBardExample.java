package com.pkslow.ai;

import java.util.List;

public class GoogleBardExample {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token);
        List<String> answers = client.ask("How to be a good father?");

        for (int i = 0; i < answers.size(); i++) {
            if (i == 0) {
                System.out.println("### Recommended Answer");
                System.out.println(answers.get(i));
            } else {
                System.out.println("### Answer " + i);
                System.out.println(answers.get(i));
            }
        }
    }
}