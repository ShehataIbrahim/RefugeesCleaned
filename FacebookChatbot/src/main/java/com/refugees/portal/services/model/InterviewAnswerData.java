package com.refugees.portal.services.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InterviewAnswerData {
    private String interview_answer_type;
    private String interview_answer;
    private String interview_category_id;
    private String interview_id;
    private String interview_version;
    private Date ifa_date;
    private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public String getInterview_answer_type() {
        return interview_answer_type;
    }

    public void setInterview_answer_type(String interview_answer_type) {
        this.interview_answer_type = interview_answer_type;
    }

    public String getInterview_answer() {
        return interview_answer;
    }

    public void setInterview_answer(String interview_answer) {
        this.interview_answer = interview_answer;
    }

    public String getInterview_category_id() {
        return interview_category_id;
    }

    public void setInterview_category_id(String interview_category_id) {
        this.interview_category_id = interview_category_id;
    }

    public String getInterview_id() {
        return interview_id;
    }

    public void setInterview_id(String interview_id) {
        this.interview_id = interview_id;
    }

    public String getInterview_version() {
        return interview_version;
    }

    public void setInterview_version(String interview_version) {
        this.interview_version = interview_version;
    }

    public String getIfa_date() {
        return formatter.format(ifa_date);
    }

    public void setIfa_date(String ifa_date) {
    }
    public void setIfaDate(Date ifa_date) {
        this.ifa_date = ifa_date;
    }
}
