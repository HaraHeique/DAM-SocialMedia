package com.example.socialmedia.Models;

import android.graphics.drawable.Drawable;

import com.example.socialmedia.Enums.PostType;

import java.util.Date;

public class Post {

    public String title;
    public String description;
    public Drawable image;
    public Date createDate;
    public PostType type;

    // Relation
    public User user;


    public Post(String title, String description, Drawable image, PostType type, User user) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.createDate = new Date();
        this.type = type;
        this.user = user;
    }
}
