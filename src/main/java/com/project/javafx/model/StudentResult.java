package com.project.javafx.model;

public class StudentResult {

    private final double DEFAULT_POINT = -1;
    private Long studentID;
    private double midtermPoint;
    private double finalPoint;
    private double score;

    public StudentResult(Long studentID) {
        this.studentID = studentID;
        this.midtermPoint = DEFAULT_POINT;
        this.finalPoint = DEFAULT_POINT;
        this.score = DEFAULT_POINT;
    }

    private double calculateScore() {
        if (midtermPoint != DEFAULT_POINT && finalPoint != DEFAULT_POINT) {
            double result = midtermPoint * 0.3 + finalPoint * 0.7;
            return Math.round(result * 10.0) / 10.0;
        }
        return DEFAULT_POINT;
    }

    public void updateResult(double midtermPoint, double finalPoint) {
        this.midtermPoint = midtermPoint;
        this.finalPoint = finalPoint;
        this.score = calculateScore();
    }

    // getter and setter
    public Long getStudentID() {
        return studentID;
    }

    public double getMidtermPoint() {
        return midtermPoint;
    }

    public double getFinalPoint() {
        return finalPoint;
    }

    public double getScore() {
        return score;
    }

    public double getScoreTranfer() {
        if (5.5 < score && score <= 7.0) return (score + 0.5) / 3.0;
        else if (7.0 < score && score <= 8.0) return (score - 3.45) / 1.42;
        else if (8.0 < score && score <= 9.0) return (score / 2.5);
        else if (9.0 < score && score <= 10.0) return (score / 2.5);
        else return 1.9; // < 2.0 means false;
    }


}
