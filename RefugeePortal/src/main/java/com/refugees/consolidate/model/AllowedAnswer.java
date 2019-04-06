package com.refugees.consolidate.model;

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
}
