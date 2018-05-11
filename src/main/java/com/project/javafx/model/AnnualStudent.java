package com.project.javafx.model;

import java.time.LocalDate;
import java.util.List;

public class AnnualStudent extends Student {

    private YearOfStudy studyYear;
    private double avg;
    private transient AnnualClass annualClass;

    public AnnualStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address);
        this.studyYear = YearOfStudy.FIRST_YEAR;
    }

    // GETTER AND SETTER

    public YearOfStudy getStudyYear() {
        return studyYear;
    }

    public double getAvg() {
        return avg;
    }

    // METHOD

    @Override
    public boolean ableToGraduate() {
        return studyYear == YearOfStudy.GRADUATED;
    }

    public void addYearCourseList(List<Course> courses) {
        takenCourses.clear();
        courses.forEach(course -> takenCourses.add(new StudentResult(course)));
    }
    public void addYearCourse(Course courses) {
        takenCourses.add(new StudentResult(courses));
    }
    public void removeYearCourse(Course courses) {
        StudentResult result = getStudentResult(courses);
        takenCourses.remove(result);
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

    public AnnualClass getAnnualClass() {
        return annualClass;
    }

    public void setAnnualClass(AnnualClass annualClass) {
        this.annualClass = annualClass;
    }
}
