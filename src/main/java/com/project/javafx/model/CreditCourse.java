package com.project.javafx.model;

public class CreditCourse extends Course{

    private CreditCourse prerequisiteCourse;
    private int creditHours;

    public CreditCourse(String courseCode, String courseName, int creditHours) {
        super(courseCode, courseName);
        this.creditHours = creditHours;
    }

    @Override
    public boolean addStudent(Student student) {
        return false;
    }

    @Override
    public boolean removeStudent(Student student) {
        return false;
    }
}
