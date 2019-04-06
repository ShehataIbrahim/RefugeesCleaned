package com.refugees.consolidate.model;


import java.util.List;

public class PatientAnswersResponse {
    private List<InterviewAnswerBaseData> data;


    public List<InterviewAnswerBaseData> getData() {
        return data;
    }

    public void setData(List<InterviewAnswerBaseData> data) {
        this.data = data;
    }
}
