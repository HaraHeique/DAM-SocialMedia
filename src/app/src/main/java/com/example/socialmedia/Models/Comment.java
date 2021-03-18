package com.example.socialmedia.Models;

import java.util.Date;

public class Comment {

    public String description;
    public Date createDate;

    // Relation
    public User user;

    public Comment(String description, User user) {
        this.description = description;
        this.createDate = new Date();
        this.user = user;
    }
}
