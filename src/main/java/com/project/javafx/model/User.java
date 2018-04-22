package com.project.javafx.model;

public class User {

    private final String username;
    private final String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long numberUsername, long numberPassword) {
        this.username = String.valueOf(numberUsername);
        this.password = String.valueOf(numberPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
