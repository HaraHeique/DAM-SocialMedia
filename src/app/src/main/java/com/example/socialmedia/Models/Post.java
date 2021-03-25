package com.example.socialmedia.Models;

import com.example.socialmedia.Enums.PostType;

import java.util.Date;

public class Post {

    public String id;
    public String description;
    public Date createDate;
    public PostType type;
    public String image;

    // Relation
    public User user;

    public Post(String id) {
        this.id = id;
    }

    public Post(String description, User user) {
        this.description = description;
        this.createDate = new Date();
        this.user = user;
    }

    public Post(String id, String description, Date createDate, String image, User user) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.image = image;
        this.type = image.isEmpty() ? PostType.COMMENT : PostType.IMAGE;
        this.user = user;
    }
}
