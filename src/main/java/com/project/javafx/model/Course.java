package com.project.javafx.model;

import java.util.Hashtable;
import java.util.Objects;

public class Course {
    private final String courseCode;
    private final String courseName;

    private final int DEFAULT_CAPACITY = 45;
    private int capacity;
    private int currentEnrollment;

    /**
     * A table store grade of each student in this course
     */
    private Hashtable<Long, Grade> studentGradeTable = new Hashtable<>();

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

    // main method
    public boolean addStudent(Student student) {
        return false;
    }

    public boolean removeStudent(Student student) {
        return false;
    }

    public void addCourseGrade(Student student, Grade grade) {

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
