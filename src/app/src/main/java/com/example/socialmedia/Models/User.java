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

    public User(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public User(CurrentUser currentUser) {
        this.login = currentUser.login;
        this.authToken = currentUser.authToken;
        this.appToken = currentUser.appToken;
    }

    public User(String login, String name, Date bornDate, String city, boolean following, String avatar) {
        this.login = login;
        this.name = name;
        this.bornDate = bornDate;
        this.city = city;
        this.following = following;
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        User user = (User) o;

        return this.login.equals(user.login);
    }
}
