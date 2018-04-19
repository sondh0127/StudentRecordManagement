package com.project.javafx.model;

import java.time.LocalDate;

public class AnnualStudent extends Student implements Updatable {

    private AnnualClass annualClass;
    private YearOfStudy studyYear;
    private double avg;

    public AnnualStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, AnnualClass annualClass) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.ANNUAL);
        this.annualClass = annualClass;
        this.studyYear = YearOfStudy.FIRST_YEAR;
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
    public boolean registerCourse(Course course) {
        if (course instanceof CreditCourse) {
            return false;
        } else {
            if (!takenCourses.containsKey(course.getCourseCode())) {
                takenCourses.put(course.getCourseCode(), new StudentResult(course.getScale()));
                // call the course to add this Student to the student List ?? // TODO: 19/04/2018
                course.addStudent(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public StudentResult getScoreResult(Course course) {
        return takenCourses.get(course.getCourseCode());
    }

    @Override
    public boolean ableToGraduate() {
        return studyYear == YearOfStudy.GRADUATED;
    }

    @Override
    public boolean updateStudentResult(String courseCode, double midtermPoint, double finalPoint) {
        if (takenCourses.containsKey(courseCode)) {
            takenCourses.put(courseCode, new StudentResult(midtermPoint, finalPoint));
            return true;
        }
        return false;
    }

    // TODO: 19/04/2018 who call this
    public void updateStudyYear() {
        switch (studyYear) {
            case FIRST_YEAR:
                avg = calculateAVG();
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.SECOND_YEAR;
                takenCourses.clear();
                break;
            case SECOND_YEAR:
                avg = calculateAVG();
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.THIRD_YEAR;
                takenCourses.clear();
                break;
            case THIRD_YEAR:
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.FOURTH_YEAR;
                takenCourses.clear();
                break;
            case FOURTH_YEAR:
                if (passedAllCourseInYear() || avg > 5.0)
                    studyYear = YearOfStudy.GRADUATED;
                takenCourses.clear();
                break;
            default:
                break;
        }
    }

    private boolean passedAllCourseInYear() {
        for (StudentResult result : takenCourses.values()) {
            if (result.getScore() < 4) {
                return false;
            }
        }
        return true;
    }

    private double calculateAVG() {
        double sum = 0;
        for (StudentResult result : takenCourses.values()) {
            sum += result.getScore();
        }
        return Math.round(sum / takenCourses.size() * 10.0) / 10.0;
    }

//    public void regiterClass(AnnualClass annualClass) {
//        this.annualClass = annualClass;
//        this.studyYear = YearOfStudy.FIRST_YEAR;
//    }

    public enum YearOfStudy {
        FIRST_YEAR("First Year"),
        SECOND_YEAR("Second Year"),
        THIRD_YEAR("Third Year"),
        FOURTH_YEAR("Fourth Year"),
        GRADUATED("Graduated");

        private final String text;

        YearOfStudy(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
