package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditCourse extends Course {

    private List<CreditCourse> prerequisiteCourse = new ArrayList<>();
    private int creditHours;

    public CreditCourse(String courseCode, String courseName, int creditHours, double scale) {
        super(courseCode, courseName, scale);
        this.creditHours = creditHours;
    }

    public CreditCourse(String courseCode, String courseName, int creditHours, List<CreditCourse> prerequisiteCourse, double scale) {
        super(courseCode, courseName, scale);
        this.creditHours = creditHours;
        this.prerequisiteCourse = prerequisiteCourse;
    }



    // GETTER AND SETTER
    public List<CreditCourse> getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public boolean addPrequisite(CreditCourse course) {
        prerequisiteCourse.add(course);
        return false;
    }

    public boolean hasPrerequisites() {
        return !prerequisiteCourse.isEmpty();
    }
}
