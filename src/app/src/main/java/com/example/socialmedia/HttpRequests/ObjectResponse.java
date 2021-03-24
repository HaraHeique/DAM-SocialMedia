package com.example.socialmedia.HttpRequests;

import org.json.JSONObject;

public class ObjectResponse<TObject> {

    public TObject data;
    public JSONObject jsonObject;
    public boolean success;
    public String message;

    public ObjectResponse(String message) {
        this.success = false;
        this.message = message;
    }

    public ObjectResponse(Exception exception) {
        this.success = false;
        this.message = exception.getMessage();
    }

    public ObjectResponse(TObject data, JSONObject jsonObject, String message) {
        this.data = data;
        this.jsonObject = jsonObject;
        this.message = message;
        this.success = true;
    }

    public ObjectResponse(TObject data, String message) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public ObjectResponse(JSONObject jsonObject, boolean success, String message) {
        this.jsonObject = jsonObject;
        this.success = success;
        this.message = message;
    }

    public void setData(TObject data) {
        this.data = data;
    }
}
