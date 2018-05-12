package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditCourse extends Course{

    private List<CreditCourse> prerequisiteCourse = new ArrayList<>();

    public CreditCourse() {
    }

    public CreditCourse(String courseCode, String courseName, int credit, double scale) {
        super(courseCode, courseName, credit, scale);
    }

    public CreditCourse(String courseCode, String courseName, int credit, double scale, List<CreditCourse> prerequisiteCourse) {
        super(courseCode, courseName, credit, scale);
        this.prerequisiteCourse = prerequisiteCourse;
    }

    // GETTER AND SETTER
    public List<CreditCourse> getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public void setPrerequisiteCourse(List<CreditCourse> prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public boolean addPrerequisite(CreditCourse course) {
        List<CreditCourse> preOfPre = course.getPrerequisiteCourse();
        if (preOfPre.contains(this) || course.equals(this)) throw new IllegalArgumentException("Circular Prerequisite !");
        if (!prerequisiteCourse.contains(course)) {
            prerequisiteCourse.add(course);
            return true;
        }
        return false;
    }

    public boolean hasPrerequisites() {
        return !prerequisiteCourse.isEmpty();
    }
}
