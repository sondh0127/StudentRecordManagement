package com.project.javafx.model;


import java.util.Hashtable;

public abstract class Course {
    private final String courseCode;
    private final String courseName;
//    private final String courseDescription = null;
    private final int DEFAULT_CAPACITY = 45;
    private int capacity;
    private int currentEnrollment;

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

    public abstract boolean addStudent(Student student);

    public abstract boolean removeStudent(Student student);

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

    public Hashtable<Student, Grade> getStudentGradeTable() {
        return studentGradeTable;
    }

    public void setStudentGradeTable(Hashtable<Student, Grade> studentGradeTable) {
        this.studentGradeTable = studentGradeTable;
    }
}
