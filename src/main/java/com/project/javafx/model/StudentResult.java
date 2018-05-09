package com.project.javafx.model;

import java.math.BigDecimal;

public class StudentResult {

    private Course course;
    private double midtermPoint;
    private double finalPoint;
    private double score;

    // Step 1:
    public StudentResult(Course course) {
        this.course = course;
        this.midtermPoint = -1;
        this.finalPoint = -1;
        this.score = -1;
    }

    // Step 2:
    public void setPoint(double midtermPoint, double finalPoint) {
        this.midtermPoint = midtermPoint;
        this.finalPoint = finalPoint;
        this.score = calculateScore(course.getScale());
    }

    // Step 3
    private double calculateScore(double scale) {
        BigDecimal scaleBD = BigDecimal.valueOf(scale);
        double _scale = BigDecimal.ONE.subtract(scaleBD).doubleValue();
        if (midtermPoint != -1 && finalPoint != -1) {
            double result = midtermPoint * scale + finalPoint * _scale; // subtraction problem here btw ; fixed(doesnt test)
            return Math.round(result * 10.0) / 10.0;
        }
        return -1;
    }

    // getter and setter
    public Course getCourse() {
        return course;
    }

    public double getMidtermPoint() {
        return midtermPoint;
    }

    public double getFinalPoint() {
        return finalPoint;
    }

    public double getScore() {
//        calculateScore();
        return score;
    }

    public double getScoreTranfer() { // subtraction probtw
        if (5.5 < score && score <= 7.0) return (score + 0.5) / 3.0;
        else if (7.0 < score && score <= 8.0) return (score - 3.45) / 1.42;
        else if (8.0 < score && score <= 9.0) return (score / 2.5);
        else if (9.0 < score && score <= 10.0) return (score / 2.5);
        else return 1.9; // < 2.0 means false;
    }

    public boolean isPassResult() {
        return getScore() >= 3.0 && getMidtermPoint() >= 3.0 && getFinalPoint() >= 3.0;
    }
}
