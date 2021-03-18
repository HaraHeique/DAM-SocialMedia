package com.example.socialmedia.Models;

public class CurrentUser {

    public String login;
    public String name;
    public String bornDate;
    public String city;
    public String password;
    public String pathImgProfile;
    public boolean isLogged;

    public CurrentUser(String login, String name, String bornDate, String city, String password, String pathImgProfile) {
        this.login = login;
        this.name = name;
        this.bornDate = bornDate;
        this.city = city;
        this.password = password;
        this.pathImgProfile = pathImgProfile;
    }
}
