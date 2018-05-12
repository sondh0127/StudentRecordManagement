package com.project.javafx.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreditStudent extends Student {

    private CreditMajor creditMajor;
    private int registerCreditsLimit;
    private int passedMajorCredits;
    private int passedMinorCredits;
    private int registerCredits;
    private double CPA;
    private double GPA;

    public CreditStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address);
        this.creditMajor = creditMajor;
        int DEFAULT_CURRENT_CREDITS_LIMIT = 24;
        this.registerCreditsLimit = DEFAULT_CURRENT_CREDITS_LIMIT;
        this.registerCredits = 0;
        this.passedMajorCredits = 0;
        this.passedMinorCredits = 0;
        CPA = 0;
        GPA = 0;
    }

    // GETTER AND SETTER
    public CreditMajor getCreditMajor() {
        return creditMajor;
    }

    public void setCreditMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
    }

    public int getPassedMajorCredits() {
        return passedMajorCredits;
    }

    public int getPassedMinorCredits() {
        return passedMinorCredits;
    }

    public double getGPA() {
        return GPA;
    }

    public double getCPA() {
        return CPA;
    }

    // Main Method
    public boolean isPassedCourse(Course course) {
        return passedCourses.stream()
                .anyMatch(studentResult -> studentResult.getCourse().equals(course));
    }

    public boolean registerCourse(CreditCourse course) throws IllegalArgumentException {
        if (!isMajorCourse(course))
            throw new IllegalArgumentException(course.getCourseCode() + " course isn't in Major Catalog!");
        else {
            int addedCredit = course.getCreditHours();
            List<CreditCourse> prerequisiteList = course.getPrerequisiteCourse();
            if (registerCredits + addedCredit > registerCreditsLimit) {
                throw new IllegalArgumentException("Exceed the number of register credit: " + registerCredits + "/" + this.registerCreditsLimit);
            }
            //get passCourseList
            List<Course> listOfCourse = this.passedCourses.stream()
                    .map(StudentResult::getCourse)
                    .collect(Collectors.toList());
            if (prerequisiteList.isEmpty() || listOfCourse.containsAll(prerequisiteList)) {
                takenCourses.add(new StudentResult(course));
                registerCredits += addedCredit;
                return true;
            } else
                throw new IllegalArgumentException(course.getCourseCode() + " needs Prerequisite Course " + prerequisiteList.toString());
        }
    }

    private boolean isMajorCourse(CreditCourse course) {
        return creditMajor.getMajorCatalog().contains(course)
                || creditMajor.getMinorCatalog().contains(course);
    }

    public void dropCourse(CreditCourse course) {
        takenCourses.stream()
                .filter(result -> result.getCourse().equals(course))
                .findFirst()
                .ifPresent(takenCourses::remove);
        registerCredits -= course.getCreditHours();
    }

    @Override
    public boolean ableToGraduate() {
        int majorCreditsRequired = creditMajor.getMajorCreditsRequired();
        int minorCreditsRequired = creditMajor.getMinorCreditsRequired();
        return passedMajorCredits >= majorCreditsRequired
                && passedMinorCredits >= minorCreditsRequired
                && getCPA() >= 2.0;
    }


    public boolean isPassResult(StudentResult result) {
        return result.getScoreTransfer() > 1.0;
    }

    public void updatePassedCourseAll() {
        if (takenCourses.isEmpty()) {
            return;
        }
        // update passedCredits
        for (StudentResult result : takenCourses) {
            if (isPassResult(result)) {
                Course course = result.getCourse();
                if (isPassedCourse(course)) {
                    // check is better result and replace
                    StudentResult studentResult = passedCourses.stream()
                            .filter(passedResult -> passedResult.getCourse().equals(course))
                            .findFirst()
                            .orElse(null);
                    if (studentResult != null) {
                        if (result.getScore() > studentResult.getScore()) {
                            passedCourses.remove(studentResult);
                            passedCourses.add(result);
                            // credit number not change
                        }
                    }
                } else {
                    passedCourses.add(result);
                    List<CreditCourse> majorCatalog = creditMajor.getMajorCatalog();
                    List<CreditCourse> minorCatalog = creditMajor.getMinorCatalog();
                    if (course instanceof CreditCourse) {
                        if (majorCatalog.contains(course)) {
                            passedMajorCredits += ((CreditCourse) course).getCreditHours();
                        } else if (minorCatalog.contains(course)) {
                            passedMinorCredits += ((CreditCourse) course).getCreditHours();
                        }
                    }
                }
            }
        }
        calculateGPA();
        calculateCPA();
        takenCourses.clear();
        registerCredits = 0;
    }

    private void calculateGPA() {
        double sum = getSum(takenCourses);
        GPA = Math.round(sum / registerCredits * 10.0) / 10.0;
    }

    private void calculateCPA() {
        double sum = getSum(passedCourses);
        CPA = Math.round(sum / (passedMinorCredits + passedMajorCredits) * 10.0) / 10.0;
    }

    private double getSum(List<StudentResult> courseMap) {
        double sum = 0;
        for (StudentResult result : courseMap) {
            double scoreTransfer = result.getScoreTransfer();
            int creditHours = ((CreditCourse) result.getCourse()).getCreditHours();
            sum += scoreTransfer * creditHours;
        }
        return sum;
    }
}
