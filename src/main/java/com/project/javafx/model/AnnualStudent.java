package com.project.javafx.model;

import java.time.LocalDate;
import java.util.List;

public class AnnualStudent extends Student {

    private YearOfStudy studyYear;
    private double avg;

    public AnnualStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.ANNUAL);
        this.studyYear = YearOfStudy.FIRST_YEAR;
    }

    // GETTER AND SETTER

    public YearOfStudy getStudyYear() {
        return studyYear;
    }

    public String getStudyYearStr() {
        return studyYear.toString();
    }

    public double getAvg() {
        return avg;
    }

    // METHOD

    @Override
    public boolean ableToGraduate() {
        return studyYear == YearOfStudy.GRADUATED;
    }

    public void registerYearList(List<Course> courses) {
        takenCourses.clear();
        courses.forEach(course -> takenCourses.add(new StudentResult(course)));
    }

    public boolean passedAllCourseInYear() {
        for (StudentResult result : takenCourses) {
            if (result.getScore() < 4) {
                return false;
            }
        }
        return true;
    }

    public double calculateAVG() {
        double sum = 0;
        for (StudentResult result : takenCourses) {
            sum += result.getScore();
        }
        avg =  Math.round(sum / takenCourses.size() * 10.0) / 10.0;
        return avg;
    }

    public void setStudyYear(YearOfStudy studyYear) {
        this.studyYear = studyYear;
    }
}
