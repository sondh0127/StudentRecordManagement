package com.project.javafx.model;

public class Grade {

    private double midtermGrade;
    private double finalGrade;
    private double score;

    public Grade() {
    }

    public double getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(double midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

    public double calculateScore() {
        if (midtermGrade != 0 && finalGrade != 0) {
            score = (midtermGrade*30 + finalGrade*70)/100;
        }
        return score;
    }
}
