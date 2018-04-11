package com.project.javafx.model;

public class AnnualClass {

    @Override
    public String toString() {
        return className;
    }

    private final String className;
    private CoursesCatalog<AnnualCourse> coursesCatalog;

    public AnnualClass(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public CoursesCatalog<AnnualCourse> getCoursesCatalog() {
        return coursesCatalog;
    }

    public void setCoursesCatalog(CoursesCatalog<AnnualCourse> coursesCatalog) {
        this.coursesCatalog = coursesCatalog;
    }

    public void createCatalog() {

    }
}
