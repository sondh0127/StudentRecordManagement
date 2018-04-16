package com.project.javafx.model;

public class StudentResult {

    private final double DEFAULT_POINT = -1;
    private double midtermPoint;
    private double finalPoint;
    private double score;
    private double scale;

    public StudentResult(double scale) {
        this.midtermPoint = DEFAULT_POINT;
        this.finalPoint = DEFAULT_POINT;
        this.score = DEFAULT_POINT;
        this.scale = scale;
    }

    public StudentResult(double midtermPoint, double finalPoint) {
        this.midtermPoint = midtermPoint;
        this.finalPoint = finalPoint;
        this.score = calculateScore();
    }

    private double calculateScore() {
        if (midtermPoint != DEFAULT_POINT && finalPoint != DEFAULT_POINT) {
            double result = midtermPoint * scale + finalPoint * (1 - scale);
            return Math.round(result * 10.0) / 10.0;
        }
        return DEFAULT_POINT;
    }

    // getter and setter
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
