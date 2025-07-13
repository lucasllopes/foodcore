package com.fiap.foodcore.domain;

public class UserType {

    private Long id;
    private String name;

    private UserType(){

    }

    public static UserType create(String description){
        UserType userType = new UserType();
        userType.name = description;
        return userType;
    }

    public static UserType reconstruct(Long id, String description) {
        UserType userType = new UserType();
        userType.id = id;
        userType.name = description;
        return userType;
    }

    public void update(String description){
        this.name = description;
    }

    public String getName() {
        return name;
    }


    public Long getId() {
        return id;
    }
}