package com.refugees.consolidate.model;

public class InterviewConsolidationData {
    private String method;
    private String resource_key;
    private InterviewAnswerData bind_parameters;

    public InterviewConsolidationData(String resource_key) {
        this.resource_key = resource_key;
    }

    public InterviewConsolidationData() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResource_key() {
        return resource_key;
    }

    public void setResource_key(String resource_key) {
        this.resource_key = resource_key;
    }

    public InterviewAnswerData getBind_parameters() {
        return bind_parameters;
    }

    public void setBind_parameters(InterviewAnswerData bind_parameters) {
        this.bind_parameters = bind_parameters;
    }
}
