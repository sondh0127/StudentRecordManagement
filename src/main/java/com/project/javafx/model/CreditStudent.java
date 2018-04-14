package com.project.javafx.model;

import java.time.LocalDate;
import java.util.Set;

public class CreditStudent extends Student implements Registerable{

    private CreditMajor creditMajor;
    private int currentCredits;
    private double GPA;

    protected Set<String> passedCourses;

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
    }

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
        this.creditMajor = creditMajor;
        this.currentCredits = 0;
    }

    // Behavior
    @Override
    public boolean registerCourse(Course course) {
        return false;
    }

    @Override
    protected StudentResult getGradeResult(Course course) {
        return null;
    }

    @Override
    protected boolean checkAbleToGraduated() {
        return false;
    }

    public void updateCreditTaken() {

    }

    public void updateGPA() {

    }

    // TODO: 13/04/2018
    public boolean checkPassedCourse(Course course) {

        return false;
    }

    // getter and setter
    public CreditMajor getCreditMajor() {
        return creditMajor;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public void setCreditMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
    }

    public double getGPA() {
        return GPA;
    }

    public void registerMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
        this.currentCredits = 0;
    }
}
