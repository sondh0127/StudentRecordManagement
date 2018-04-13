package com.project.javafx.model;

import java.time.LocalDate;

public class CreditStudent extends Student {

    private CreditMajor creditMajor;
    private int currentCredits;
    private double GPA;

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
    public void registerCourse(Course course) {
    }

    @Override
    protected Grade getGradeResult(Course course) {
        return null;
    }

    @Override
    protected void checkGraduationRequirements() {

    }

    public void updateCreditTaken() {

    }

    public void updateGPA() {

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
