package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditCourse extends Course{

    private List<CreditCourse> prerequisiteCourse = new ArrayList<>();
    private int creditHours;

    public CreditCourse(String courseCode, String courseName, int creditHours) {
        super(courseCode, courseName);
        this.creditHours = creditHours;
    }
    public CreditCourse(String courseCode, String courseName, int creditHours, List<CreditCourse> prerequisiteCourse) {
        super(courseCode, courseName);
        this.creditHours = creditHours;
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public boolean addPrequisiteCourse(CreditCourse course) {

        return false;
    }
    public boolean addPrequisiteCourse(List<CreditCourse> prerequisiteCourse) {

        return false;
    }


}
