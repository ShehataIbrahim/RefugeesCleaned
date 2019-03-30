package com.refugees.portal.services.model;

public class ConsolidateData {
    private String method;
    private String resource_key;
    private Patient bind_parameters;

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

    public Patient getBind_parameters() {
        return bind_parameters;
    }

    public void setBind_parameters(Patient bind_parameters) {
        this.bind_parameters = bind_parameters;
    }
}
