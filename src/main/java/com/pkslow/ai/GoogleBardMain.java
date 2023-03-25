package com.pkslow.ai;

import java.util.List;

public class GoogleBardMain {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = "UgiXYPjpaIYuE9K_3BSqCWnT2WIqxxxxxxxx.";
        AIClient client = new GoogleBardClient(token);
        List<String> answers = client.ask("what is pkslow.com?");

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