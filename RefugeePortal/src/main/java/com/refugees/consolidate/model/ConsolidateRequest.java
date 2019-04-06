package com.refugees.consolidate.model;

import java.util.List;

public class ConsolidateRequest extends BaseRequest{

private String from_type;
private String to_type;
private List<ConsolidateData> data;
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

    public List<ConsolidateData> getData() {
        return data;
    }

    public void setData(List<ConsolidateData> data) {
        this.data = data;
    }
}
