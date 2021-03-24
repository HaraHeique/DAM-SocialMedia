package com.example.socialmedia.Models;

public class CurrentUser {

    public String login;
    public String authToken;
    public String appToken;
    public boolean isLogged;

    public CurrentUser(String login, String authToken) {
        this.login = login;
        this.authToken = authToken;
        this.appToken = "";
    }

    public CurrentUser(String login, String authToken, String appToken) {
        this.login = login;
        this.authToken = authToken;
        this.appToken = appToken;
    }
}
