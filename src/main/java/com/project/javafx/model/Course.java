package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String courseCode;
    private final String courseName;

    private final int DEFAULT_CAPACITY = 45;
    private int capacity;
    private int currentEnrollment;
    private double scale;

    private List<Student> studentRoster = new ArrayList<>();

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = DEFAULT_CAPACITY;
    }

    public Course(String courseCode, String courseName, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = capacity;
    }

    // getter and setter
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getScale() {
        return scale;
    }

    public List<Student> getStudentRoster() {
        return studentRoster;
    }

    @Override
    public String toString() {
        return courseCode;
    }

    // main method
    public boolean addStudent(Student student) {
        return studentRoster.add(student);
    }

    public boolean removeStudent(Student student) {
        return studentRoster.remove(student);
    }

    /**
     * Override equals to provide: a comparison of 2 course
     */
    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Course)) {
            return false;
        }
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode);
    }

}
