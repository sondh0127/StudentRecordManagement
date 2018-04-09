package com.project.javafx.model;

public class AnnualCourse extends Course {

    public AnnualCourse(String courseCode, String courseName) {
        super(courseCode, courseName);
    }

    public AnnualCourse(String courseCode, String courseName, int capacity) {
        super(courseCode, courseName, capacity);
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
