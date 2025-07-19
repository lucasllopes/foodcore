package com.fiap.foodcore.domain;

import java.time.LocalDateTime;

public class UserType {

    private Long id;
    private String name;
    private LocalDateTime lastModified;

    private UserType(){

    }

    public static UserType create(String description){
        UserType userType = new UserType();
        userType.name = description;
        return userType;
    }

    public static UserType reconstruct(Long id, String description, LocalDateTime lastModified) {
        UserType userType = new UserType();
        userType.id = id;
        userType.name = description;
        userType.lastModified = lastModified;
        return userType;
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