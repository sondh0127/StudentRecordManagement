package com.project.javafx.model;

public class Course {
    private String courseCode;
    private String courseName;
    private int credit;
    private double scale;

    public Course() {
    }

    public Course(String courseCode, String courseName, int credit, double scale) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.scale = scale;
    }

    // getter and setter
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                '}';
    }
}
