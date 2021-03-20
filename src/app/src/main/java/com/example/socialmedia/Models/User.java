package com.example.socialmedia.Models;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class User {

    public String name;
    public String login;
    public String password;
    public Date bornDate;
    public String city;
    public boolean following; // Atributo para facilitar nos mocks (usuário corrente segue este usuário)
    public Drawable avatar;
    public String appToken;
    public String authToken;

    public User(String name, String login, Date bornDate, String city, boolean following, Drawable avatar) {
        this.name = name;
        this.login = login;
        this.bornDate = bornDate;
        this.city = city;
        this.following = following;
        this.avatar = avatar;
    }

    public User(String name, String login, String password, Date bornDate, String city, String appToken, String authToken) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.bornDate = bornDate;
        this.city = city;
        this.appToken = appToken;
        this.authToken = authToken;
    }
}
