package com.refugees.portal.services.model;

public class ResourceKeyRequest extends BaseRequest{
    private PatientId bind_parameters;

    public ResourceKeyRequest(String id) {
        PatientId pId=new PatientId();
        pId.setPatient_id(id);
        bind_parameters=pId;
    }

    public PatientId getBind_parameters() {
        return bind_parameters;
    }

    public void setBind_parameters(PatientId bind_parameters) {
        this.bind_parameters = bind_parameters;
    }
}
