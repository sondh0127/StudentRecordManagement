package com.project.javafx.model;

public class User {

    private final String username;
    private final String password;
    private final UserType userType;


    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(long numberUsername, long numberPassword, UserType userType) {
        this.username = String.valueOf(numberUsername);
        this.password = String.valueOf(numberPassword);
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
