package com.example.socialmedia.Models;

import java.util.Date;

public class Comment {

    public String id;
    public String description;
    public Date createDate;

    // Relations
    public User user;
    public Post post;

    public Comment(String id, String description, Date createDate, User user, Post post) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.user = user;
        this.post = post;
    }

    public Comment(String description, User user, Post post) {
        this.description = description;
        this.user = user;
        this.post = post;
    }
}
