package com.project.javafx.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditStudent extends Student {

    private Map<Course, StudentResult> passedCourses = new HashMap<>();
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
//        creditMajor.addStudent(this);
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
    // TODO: 24/04/2018 check throw
    public boolean registerCourse(CreditCourse course) {
        if (takenCourses.containsKey(course))
            throw new IllegalArgumentException(this.getStudentID() + " already took " + course.getCourseCode() + " course !");
        else {
            int addedCredit = course.getCreditHours();
            List<CreditCourse> prerequisiteList = course.getPrerequisiteCourse();
            if (registerCredits + addedCredit > registerCreditsLimit) {
                throw new IllegalArgumentException("Exceed the number of register credit: " + registerCredits + "/" + this.registerCreditsLimit);
            }
            if (prerequisiteList.isEmpty() || passedCourses.keySet().containsAll(prerequisiteList)) {
                takenCourses.put(course, new StudentResult());
                registerCredits += addedCredit;
                return true;
            } else
                throw new IllegalArgumentException(course.getCourseCode() + " needs Prerequisite Course " + prerequisiteList.toString());
        }
    }

    public void dropCourse(CreditCourse course) {
        if (takenCourses.containsKey(course)) {
            takenCourses.remove(course);
            registerCredits -= course.getCreditHours();
        }
    }

    @Override
    // TODO: 14/04/2018 minor add pls
    public boolean ableToGraduate() {
        int majorCreditsRequired = creditMajor.getMajorCreditsRequired();
        int minorCreditsRequired = creditMajor.getMinorCreditsRequired();
        return passedMajorCredits >= majorCreditsRequired
                && passedMinorCredits >= minorCreditsRequired
                && calculateCPA() >= 2.0;
    }


    public void updatePassedCourseAll() {
//        for (String courseCode : takenCourses.keySet()) {
//            Course course = CourseRepository.getInstance().findById(courseCode);
//            updatePassedCourse(course);
//
//        }
    }

    private void updatePassedCourse(CreditCourse course) {
        if (isPassCourse(course)) {
            // if passed course add to list passedCourse
            passedCourses.put(course, takenCourses.remove(course));
            // update passedCredits
            List<CreditCourse> majorCatalog = creditMajor.getMajorCatalog();
            List<CreditCourse> minorCatalog = creditMajor.getMinorCatalog();
            if (majorCatalog.contains(course)) {
                passedMajorCredits += course.getCreditHours();
            } else if (minorCatalog.contains(course)) {
                passedMinorCredits += course.getCreditHours();
            }
        }
    }

    private boolean isPassCourse(CreditCourse course) {
        StudentResult result = takenCourses.get(course);
        return result.getScore() > 3.0 && result.getMidtermPoint() > 3.0 && result.getFinalPoint() > 3.0;
    }

    private double calculateGPA() {
        double sum = getSum(takenCourses);
        return sum / registerCredits;
    }

    public double calculateCPA() {
        updatePassedCourseAll();
        double sum = getSum(passedCourses);
        return sum / (passedMinorCredits + passedMajorCredits);
    }

    private double getSum(Map<Course, StudentResult> courseMap) {
        double sum = 0;
        for (Course course : courseMap.keySet()) {
            double grade = getScoreResult(course).getScore();
            int credits = ((CreditCourse) course).getCreditHours();
            sum += grade * credits;
        }
        return sum;
    }

    public void registerMajor(CreditMajor creditMajor) {
        if (this.creditMajor == null) {
            this.creditMajor = creditMajor;
//            creditMajor.addStudent(this);
            this.passedMajorCredits = 0;
            this.passedMinorCredits = 0;
        }
    }
}
