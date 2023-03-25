package com.pkslow.ai;


public class GoogleBardExample {
    public static void main(String[] args) {
        NetworkUtils.setUpProxy("localhost", "7890");
        String token = args[0];
        AIClient client = new GoogleBardClient(token);
        Answer answer = client.ask("how to be a good father?");

        if (answer.status() == AnswerStatus.OK) {
            System.out.println("### Chosen Answer");
            System.out.println(answer.chosenAnswer());
            for (int i = 0; i < answer.draftAnswers().size(); i++) {
                System.out.println("### Draft Answer " + i);
                System.out.println(answer.draftAnswers().get(i));
            }
        }


    }
}