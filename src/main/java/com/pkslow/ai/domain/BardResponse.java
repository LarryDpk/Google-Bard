package com.pkslow.ai.domain;

public class BardResponse {
    private final String conversationId;
    private final String responseId;
    private final String choiceId;
    private final Answer answer;

    public BardResponse(String conversationId, String responseId, String choiceId, Answer answer) {
        this.conversationId = conversationId;
        this.responseId = responseId;
        this.choiceId = choiceId;
        this.answer = answer;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getResponseId() {
        return responseId;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public Answer getAnswer() {
        return answer;
    }
}
