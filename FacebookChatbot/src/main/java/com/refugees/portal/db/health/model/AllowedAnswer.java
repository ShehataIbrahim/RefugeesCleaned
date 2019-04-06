package com.refugees.portal.db.health.model;

import com.google.common.base.MoreObjects;

public class AllowedAnswer {
    private int answerId;
    private String answer;
    private String translatedAnswer;
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTranslatedAnswer() {
        return translatedAnswer;
    }

    public void setTranslatedAnswer(String translatedAnswer) {
        this.translatedAnswer = translatedAnswer;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("answerId", answerId)
                .add("answer", answer)
                .add("translatedAnswer", translatedAnswer)
                .toString();
    }
}
