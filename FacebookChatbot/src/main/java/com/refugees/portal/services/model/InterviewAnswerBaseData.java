package com.refugees.portal.services.model;

public class InterviewAnswerBaseData {
    private String answer_type;
    private String interview_version;
    private String interview_id;
    private String interview_category_id;
    private String interview_answer;

    public String getAnswer_type() {
        return answer_type;
    }

    public void setAnswer_type(String answer_type) {
        this.answer_type = answer_type;
    }

    public String getInterview_version() {
        return interview_version;
    }

    public void setInterview_version(String interview_version) {
        this.interview_version = interview_version;
    }

    public String getInterview_id() {
        return interview_id;
    }

    public void setInterview_id(String interview_id) {
        this.interview_id = interview_id;
    }

    public String getInterview_category_id() {
        return interview_category_id;
    }

    public void setInterview_category_id(String interview_category_id) {
        this.interview_category_id = interview_category_id;
    }

    public String getInterview_answer() {
        return interview_answer;
    }

    public void setInterview_answer(String interview_answer) {
        this.interview_answer = interview_answer;
    }
}
