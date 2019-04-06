package com.refugees.consolidate.model;

import java.util.List;

public class ResourceKeyResponse {
    private List<DataResponse> data;
    private String message_id;
    private String message;

    public List<DataResponse> getData() {
        return data;
    }

    public void setData(List<DataResponse> data) {
        this.data = data;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
