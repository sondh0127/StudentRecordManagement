package com.project.javafx.model;

public class CreditCourse extends Course{

    private CreditCourse prerequisiteCourse;
    private int creditHours;

    public CreditCourse(String courseCode, String courseName) {
        super(courseCode, courseName);
    }

    public CreditCourse(String courseCode, String courseName, int capacity, CreditCourse prerequisiteCourse) {
        super(courseCode, courseName, capacity);
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public CreditCourse(String courseCode, String courseName, int capacity, int creditHours) {
        super(courseCode, courseName, capacity);
        this.creditHours = creditHours;
    }

    public CreditCourse(String courseCode, String courseName, CreditCourse prerequisiteCourse, int creditHours) {
        super(courseCode, courseName);
        this.prerequisiteCourse = prerequisiteCourse;
        this.creditHours = creditHours;
    }

    public CreditCourse(String courseCode, String courseName, int capacity, CreditCourse prerequisiteCourse, int creditHours) {
        super(courseCode, courseName, capacity);
        this.prerequisiteCourse = prerequisiteCourse;
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
