package com.project.javafx.ulti;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    UNKNOWN("Unknown");

    private final String text;

    Gender(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
