package com.refugees.portal.db.health.model;

public class QuestionDependency {
    String interviewId;
    AllowedAnswer validAnswer;

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public AllowedAnswer getValidAnswer() {
        return validAnswer;
    }

    public void setValidAnswer(AllowedAnswer validAnswer) {
        this.validAnswer = validAnswer;
    }
}
