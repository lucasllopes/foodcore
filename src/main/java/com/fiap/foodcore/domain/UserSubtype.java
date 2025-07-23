package com.fiap.foodcore.domain;

import java.time.LocalDateTime;

public class UserSubtype {

    private Long id;
    private String name;
    private LocalDateTime lastModified;

    private UserSubtype(){

    }

    public static UserSubtype create(String description){
        UserSubtype userSubtype = new UserSubtype();
        userSubtype.name = description;
        return userSubtype;
    }

    public static UserSubtype reconstruct(Long id, String description, LocalDateTime lastModified) {
        UserSubtype userSubtype = new UserSubtype();
        userSubtype.id = id;
        userSubtype.name = description;
        userSubtype.lastModified = lastModified;
        return userSubtype;
    }


    public void update(String description){
        this.name = description;
        this.lastModified = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
}