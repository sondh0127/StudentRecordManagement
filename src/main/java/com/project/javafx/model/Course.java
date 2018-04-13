package com.project.javafx.model;


import java.util.Hashtable;

public class Course {
    private final String courseCode;
    private final String courseName;

    private final int DEFAULT_CAPACITY = 45;
    private int capacity;
    private int currentEnrollment;

    /**
     * A table store grade of each student in this course
     */
    private Hashtable<Student,Grade> studentGradeTable;

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

    public boolean addStudent(Student student) {
        return false;
    }

    public boolean removeStudent(Student student) {
        return false;
    }

    public void addCourseGrade(Student student, Grade grade) {

    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(int currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

}
