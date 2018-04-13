package com.project.javafx.model;

public class AnnualClass {

    @Override
    public String toString() {
        return className;
    }

    private final String classCode;
    private final String className;
    private CoursesCatalog<Course> coursesCatalog;

    public AnnualClass(String classCode, String className) {
        this.classCode = classCode;
        this.className = className;
    }
    // main method
    public void createCatalog() {

    }

    // getter and setter

    public String getClassName() {
        return className;
    }

    public CoursesCatalog<Course> getCoursesCatalog() {
        return coursesCatalog;
    }

    public void setCoursesCatalog(CoursesCatalog<Course> coursesCatalog) {
        this.coursesCatalog = coursesCatalog;
    }

}
