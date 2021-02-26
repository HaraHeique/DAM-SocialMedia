package com.example.socialmedia.Models;

public class CurrentUser {

    public String login;
    public String name;
    public String password;
    public String pathImgProfile;
    public boolean isLogged;

    public CurrentUser(String login, String name, String password, String pathImgProfile) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.pathImgProfile = pathImgProfile;
    }
}
