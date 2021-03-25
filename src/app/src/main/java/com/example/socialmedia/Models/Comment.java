package com.example.socialmedia.Models;

import java.util.Date;

public class Comment {

    public String id;
    public String description;
    public Date createDate;

    // Relations
    public User user;
    public Post post;

    public Comment(String description, User user) {
        this.description = description;
        this.createDate = new Date();
        this.user = user;
    }
}
