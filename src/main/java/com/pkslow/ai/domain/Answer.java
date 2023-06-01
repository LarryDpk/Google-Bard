package com.pkslow.ai.domain;


import com.pkslow.ai.util.BardUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Answer {
    private final AnswerStatus status;

    private final String chosenAnswer;

    private final String imageURL;

    private final String articleURL;

    public String markdown() {
        StringBuilder sb = new StringBuilder();
        sb.append(chosenAnswer);
        if (!BardUtils.isEmpty(imageURL)) {
            sb.append("\n\n");
            sb.append("[![](");
            sb.append(imageURL);
            sb.append(")](");
            sb.append(articleURL);
            sb.append(")");
        }

        return sb.toString();
    }


}
