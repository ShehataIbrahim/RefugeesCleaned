package com.refugees.consolidate.model;

import java.util.ArrayList;
import java.util.List;

public class ConsolidateInterviewRequest extends BaseRequest {
    private String from_type;
    private String to_type;
    List<InterviewConsolidationData> data;

    public ConsolidateInterviewRequest() {
        data=new ArrayList<>();
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getTo_type() {
        return to_type;
    }

    public void setTo_type(String to_type) {
        this.to_type = to_type;
    }

    public List<InterviewConsolidationData> getData() {
        return data;
    }

    public void setData(List<InterviewConsolidationData> data) {
        this.data = data;
    }
}
