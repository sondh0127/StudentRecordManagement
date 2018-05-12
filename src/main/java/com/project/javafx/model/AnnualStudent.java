package com.project.javafx.model;

import java.time.LocalDate;
import java.util.List;

import static com.project.javafx.model.YearOfStudy.*;

public class AnnualStudent extends Student {

    private YearOfStudy studyYear;
    private double avg;
    private transient AnnualClass annualClass;

    public AnnualStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address);
        this.studyYear = FIRST_YEAR;
    }

    // GETTER AND SETTER

    public YearOfStudy getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(YearOfStudy studyYear) {
        this.studyYear = studyYear;
    }

    // METHOD

    public double getAvg() {
        return avg;
    }

    @Override
    public boolean ableToGraduate() {
        return studyYear == GRADUATED;
    }

    public void addYearCourseList(List<Course> courses) {
        takenCourses.clear();
        courses.forEach(course -> takenCourses.add(new StudentResult(course)));
    }

    public void addYearCourse(Course courses) {
        takenCourses.add(new StudentResult(courses));
    }

    public void removeYearCourse(Course courses) {
        StudentResult result = getTakenResult(courses);
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
        avg = Math.round(sum / takenCourses.size() * 10.0) / 10.0;
        return avg;
    }

    public AnnualClass getAnnualClass() {
        return annualClass;
    }

    public void setAnnualClass(AnnualClass annualClass) {
        this.annualClass = annualClass;
    }

    public void updateStudyYear() {
        switch (studyYear) {
            case FIRST_YEAR:
                if (passedAllCourseInYear() && calculateAVG() > 5.0) setStudyYear(SECOND_YEAR);
                break;
            case SECOND_YEAR:
                if (passedAllCourseInYear() && calculateAVG() > 5.0) setStudyYear(THIRD_YEAR);
                break;
            case THIRD_YEAR:
                if (passedAllCourseInYear() && calculateAVG() > 5.0) setStudyYear(FOURTH_YEAR);
                break;
            case FOURTH_YEAR:
                if (passedAllCourseInYear() && calculateAVG() > 5.0) setStudyYear(GRADUATED);
                break;
            default:
                break;
        }
        addYearCourseList(annualClass.getCoursesCatalog(studyYear));
    }

}
