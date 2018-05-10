package com.project.javafx.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditCourse extends Course  implements Serializable {

    private List<CreditCourse> prerequisiteCourse = new ArrayList<>();
    private int creditHours;

    public CreditCourse() {
    }

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

    public void setPrerequisiteCourse(List<CreditCourse> prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public boolean addPrequisite(CreditCourse course) {
        if (course != this) {
            prerequisiteCourse.add(course);
            // TODO: 10/05/2018 alert
            return false;
        }
        List<CreditCourse> prerequisiteCourse = course.getPrerequisiteCourse();
        if (prerequisiteCourse.contains(this)) {
            return false;
        }
        return true;
    }

    public boolean hasPrerequisites() {
        return !prerequisiteCourse.isEmpty();
    }
}
