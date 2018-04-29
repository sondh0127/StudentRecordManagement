package com.project.javafx.model;

import java.util.Objects;

public class Course {
    private final String courseCode;
    private final String courseName;
    private final double scale;

    public Course(String courseCode, String courseName, double scale) {
        this.courseCode = courseCode.toUpperCase();
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

    // main method

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
