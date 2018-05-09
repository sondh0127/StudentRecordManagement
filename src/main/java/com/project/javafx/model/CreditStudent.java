package com.project.javafx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreditStudent extends Student {

    private List<StudentResult> passedCourses = new ArrayList<>();
    private CreditMajor creditMajor;
    private int registerCreditsLimit;
    private int passedMajorCredits;
    private int passedMinorCredits;
    private int registerCredits;
    private double CPA;
    private double GPA;

    public CreditStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
        this.creditMajor = creditMajor;
        int DEFAULT_CURRENT_CREDITS_LIMIT = 24;
        this.registerCreditsLimit = DEFAULT_CURRENT_CREDITS_LIMIT;
        this.registerCredits = 0;
        this.passedMajorCredits = 0;
        this.passedMinorCredits = 0;
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
        if (isTakingCourse(course))
            throw new IllegalArgumentException(this.getStudentID() + " already took " + course.getCourseCode() + " course !");
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


    public void updatePassedCourseAll() {
        if (takenCourses.isEmpty()) {
            return;
        }
        // update passedCredits
        for (StudentResult result : takenCourses) {
            if (result.isPassResult()) {
                passedCourses.add(result);
                List<CreditCourse> majorCatalog = creditMajor.getMajorCatalog();
                List<CreditCourse> minorCatalog = creditMajor.getMinorCatalog();
                Course course = result.getCourse();
                if (course instanceof CreditCourse) {
                    if (majorCatalog.contains(course)) {
                        passedMajorCredits += ((CreditCourse) course).getCreditHours();
                    } else if (minorCatalog.contains(course)) {
                        passedMinorCredits += ((CreditCourse) course).getCreditHours();
                    }
                }
            }
        }
        calculateGPA();
        calculateCPA();
        takenCourses.clear();
    }

    private void calculateGPA() {
        double sum = getSum(takenCourses);
        GPA =  sum / registerCredits;
    }

    private void calculateCPA() {
        double sum = getSum(passedCourses);
        CPA = sum / (passedMinorCredits + passedMajorCredits);
    }

    private double getSum(List<StudentResult> courseMap) {
        if (courseMap.isEmpty()) {
            return 0;
        }
        double sum = 0;
        List<Course> collect = courseMap.stream()
                .map(StudentResult::getCourse)
                .collect(Collectors.toList());
        for (Course course : collect) {
            double grade = getStudentResult(course).getScore();
            int credits = ((CreditCourse) course).getCreditHours();
            sum += grade * credits;
        }
        return sum;
    }
}
