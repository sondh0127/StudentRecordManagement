package com.project.javafx.model;

public class Course {
    private final String courseCode;
    private final String courseName;
    private final double scale;

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

    @Override
    public String toString() {
        return courseCode;
    }

}
