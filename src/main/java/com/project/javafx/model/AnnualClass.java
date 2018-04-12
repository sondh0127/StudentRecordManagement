package com.project.javafx.model;

public class AnnualClass {

    @Override
    public String toString() {
        return className;
    }

    private final String className;
    private CoursesCatalog<Course> coursesCatalog;

    public AnnualClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public CoursesCatalog<Course> getCoursesCatalog() {
        return coursesCatalog;
    }

    public void setCoursesCatalog(CoursesCatalog<Course> coursesCatalog) {
        this.coursesCatalog = coursesCatalog;
    }

    public void createCatalog() {

    }
}
