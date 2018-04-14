package com.project.javafx.model;

public class AnnualClass {

    @Override
    public String toString() {
        return className;
    }

    private final String classCode;
    private final String className;
    private CoursesCatalog<Course> coursesCatalog1;
    private CoursesCatalog<Course> coursesCatalog2;
    private CoursesCatalog<Course> coursesCatalog3;
    private CoursesCatalog<Course> coursesCatalog4;

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
        return coursesCatalog1;
    }

    public void setCoursesCatalog(CoursesCatalog<Course> coursesCatalog) {
        this.coursesCatalog1 = coursesCatalog;
    }

}
