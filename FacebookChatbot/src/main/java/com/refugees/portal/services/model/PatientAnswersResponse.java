package com.refugees.portal.services.model;

import com.refugees.portal.db.health.model.Interview;

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
