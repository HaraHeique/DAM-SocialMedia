package com.example.socialmedia.Models;

import java.util.Date;

public class User {

    public String login; // PK
    public String name;
    public String password;
    public Date bornDate;
    public String city;
    public boolean following;
    public String avatar; // Imagem de perfil
    public String appToken;
    public String authToken;

    public User(String name, String login, boolean following, String avatar) {
        this.name = name;
        this.login = login;
        this.following = following;
        this.avatar = avatar;
    }

    public User(String name, String login, String password, Date bornDate, String city) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.bornDate = bornDate;
        this.city = city;
    }

    public User(String login, String password, String authToken) {
        this.login = login;
        this.password = password;
        this.authToken = authToken;
    }
}
