package com.osunkwo.williams.codefellowship;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String body;
    java.sql.Timestamp createdAt;

    @ManyToOne
    AppUser creator;

    public Post (){};

    public Post(String body){
        this.body = body;
        Calendar calendar = Calendar.getInstance();
        createdAt = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public AppUser getCreator() {
        return creator;
    }
}
