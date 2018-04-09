package com.project.javafx.model;

public class CreditStudent extends Student {

    private CreditMajor creditMajor;
    private int currentCredits;
    private double GPA;

    public CreditStudent(int studentID, String firstName, String lastName, CreditMajor creditMajor) {
        super(studentID, firstName, lastName);
        this.creditMajor = creditMajor;
        this.currentCredits = 0;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void dropCourse(Course course) {

    }

    public CreditMajor getCreditMajor() {
        return creditMajor;
    }

    public void setCreditMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public void setCurrentCredits(int currentCredits) {
        this.currentCredits = currentCredits;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
