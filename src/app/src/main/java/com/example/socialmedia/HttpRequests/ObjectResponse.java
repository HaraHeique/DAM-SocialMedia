package com.example.socialmedia.HttpRequests;

import org.json.JSONObject;

public class ObjectResponse<TObject> {

    public TObject data;
    public JSONObject jsonObject;
    public boolean success;
    public String message;
}
