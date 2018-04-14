package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditCourse extends Course{

    private List<String> prerequisiteCourse = new ArrayList<>();
    private int creditHours;

    public CreditCourse(String courseCode, String courseName, int creditHours) {
        super(courseCode, courseName);
        this.creditHours = creditHours;
    }
    public CreditCourse(String courseCode, String courseName, int creditHours, List<String> prerequisiteCourse) {
        super(courseCode, courseName);
        this.creditHours = creditHours;
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public boolean addPrequisiteCourse(String courseCode) {
        prerequisiteCourse.add(courseCode);
        return false;
    }
}
