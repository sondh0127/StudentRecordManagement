package com.project.javafx.model;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseCode;
    private String courseName;
    private double scale;

    public Course() {
    }

    public Course(String courseCode, String courseName, double scale) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.scale = scale;
    }

    // getter and setter
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getScale() {
        return scale;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return courseCode;
    }

}
