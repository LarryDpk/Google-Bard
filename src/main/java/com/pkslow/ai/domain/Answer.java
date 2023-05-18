package com.pkslow.ai.domain;

import java.util.List;

public class Answer {
    private final AnswerStatus status;
    private final String chosenAnswer;
    private final List<String> draftAnswers;


    public Answer(AnswerStatus status, String chosenAnswer, List<String> draftAnswers) {
        this.status = status;
        this.chosenAnswer = chosenAnswer;
        this.draftAnswers = draftAnswers;
    }

    public AnswerStatus status() {
        return status;
    }

    public String chosenAnswer() {
        return chosenAnswer;
    }

    public List<String> draftAnswers() {
        return draftAnswers;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "status=" + status +
                ", chosenAnswer='" + chosenAnswer + '\'' +
                ", draftAnswers=" + draftAnswers +
                '}';
    }


    public static final class AnswerBuilder {
        private AnswerStatus status;
        private String chosenAnswer;
        private List<String> draftAnswers;

        private AnswerBuilder() {
        }

        public static AnswerBuilder anAnswer() {
            return new AnswerBuilder();
        }

        public AnswerBuilder status(AnswerStatus status) {
            this.status = status;
            return this;
        }

        public AnswerBuilder chosenAnswer(String chosenAnswer) {
            this.chosenAnswer = chosenAnswer;
            return this;
        }

        public AnswerBuilder draftAnswers(List<String> draftAnswers) {
            this.draftAnswers = draftAnswers;
            return this;
        }

        public Answer build() {
            return new Answer(status, chosenAnswer, draftAnswers);
        }
    }
}
