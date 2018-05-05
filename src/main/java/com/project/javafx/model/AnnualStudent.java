package com.project.javafx.model;

import java.time.LocalDate;

public class AnnualStudent extends Student {

    private AnnualClass annualClass;
    private YearOfStudy studyYear;
    private double avg;

    public AnnualStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address, AnnualClass annualClass) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.ANNUAL);
        this.annualClass = annualClass;
        this.studyYear = YearOfStudy.FIRST_YEAR;
        registerYearList();
    }

    // GETTER AND SETTER
    public AnnualClass getAnnualClass() {
        return annualClass;
    }

    public void setAnnualClass(AnnualClass annualClass) {
        this.annualClass = annualClass;
    }

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

    // TODO: 19/04/2018 who call this
    // TODO: 05/05/2018 check this
    public void updateStudyYear() {
        switch (studyYear) {
            case FIRST_YEAR:
                avg = calculateAVG();
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.SECOND_YEAR;
                registerYearList();
                break;
            case SECOND_YEAR:
                avg = calculateAVG();
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.THIRD_YEAR;
                registerYearList();
                break;
            case THIRD_YEAR:
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.FOURTH_YEAR;
                registerYearList();
                break;
            case FOURTH_YEAR:
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.GRADUATED;
                break;
            default:
                break;
        }
    }

    public void registerYearList() {
        takenCourses.clear();
        annualClass.getCoursesCatalog(this.studyYear)
                .forEach(course -> takenCourses.add(new StudentResult(course)));
    }

    private boolean passedAllCourseInYear() {
        for (StudentResult result : takenCourses) {
            if (result.getScore() < 4) {
                return false;
            }
        }
        return true;
    }

    private double calculateAVG() {
        double sum = 0;
        for (StudentResult result : takenCourses) {
            sum += result.getScore();
        }
        return Math.round(sum / takenCourses.size() * 10.0) / 10.0;
    }

}
