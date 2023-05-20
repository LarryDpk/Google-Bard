package com.pkslow.ai.domain;

import org.junit.Assert;
import org.junit.Test;

import static java.util.Arrays.asList;

public class AnswerTest {
    @Test
    public void testAnswer() {
        Answer.AnswerBuilder builder = Answer.AnswerBuilder.anAnswer();
        Answer answer = builder.status(AnswerStatus.OK)
                .chosenAnswer("Good")
                .draftAnswers(asList("answer1", "answer2", "answer3"))
                .build();

        Assert.assertNotNull(answer);
        Assert.assertEquals(AnswerStatus.OK, answer.status());
        Assert.assertEquals("Good", answer.chosenAnswer());
        Assert.assertTrue(answer.draftAnswers().contains("answer3"));

        Assert.assertTrue(answer.toString().contains("answer2"));

    }
}
