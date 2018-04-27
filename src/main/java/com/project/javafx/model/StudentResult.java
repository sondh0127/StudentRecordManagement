package com.project.javafx.model;

import java.math.BigDecimal;

public class StudentResult {

    private double midtermPoint;
    private double finalPoint;
    private double score;

    public StudentResult() {
        this.midtermPoint = -1;
        this.finalPoint = -1;
        this.score = -1;
    }

    public StudentResult(double midtermPoint, double finalPoint) {
        if (midtermPoint > 10 || midtermPoint < 0 || finalPoint > 10 || finalPoint < 0) {
            throw new IllegalArgumentException("Invalid Score ..."); // remove this to the controller
        } else {
            this.midtermPoint = midtermPoint;
            this.finalPoint = finalPoint;
            this.score = -1;
        }
    }

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


}
