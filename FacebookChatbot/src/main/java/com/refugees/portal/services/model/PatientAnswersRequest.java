package com.refugees.portal.services.model;

public class PatientAnswersRequest extends BaseRequest{
    private ResourceKey bind_parameters;

    public PatientAnswersRequest(String resourceKey) {
        ResourceKey resKey=new ResourceKey(resourceKey);
        bind_parameters=resKey;
    }

    public ResourceKey getBind_parameters() {
        return bind_parameters;
    }

    public void setBind_parameters(ResourceKey bind_parameters) {
        this.bind_parameters = bind_parameters;
    }
}
