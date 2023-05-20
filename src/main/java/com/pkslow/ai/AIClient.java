package com.pkslow.ai;


import com.pkslow.ai.domain.Answer;

public interface AIClient {
    Answer ask(String question);
    void reset();
}
