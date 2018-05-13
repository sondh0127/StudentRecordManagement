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
        double result = midtermPoint * scale + finalPoint * _scale;
        return Math.round(result * 10.0) / 10.0;
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
        return score;
    }

    public double getScoreTransfer() {
        if (score < 4.0) return 0;
        else if (score < 5.0) return 1.0D;
        else if (score < 5.5) return 1.5D;
        else if (score < 6.5) return 2.0D;
        else if (score < 7.0) return 2.5D;
        else if (score < 8.0) return 3.0D;
        else if (score < 8.5) return 3.5D;
        else return 4.0D;
    }

    public String getLetterScore() {
        if (score < 4.0) return "F";
        else if (score < 5.0) return "D";
        else if (score < 5.5) return "D+";
        else if (score < 6.5) return "C";
        else if (score < 7.0) return "C+";
        else if (score < 8.0) return "B";
        else if (score < 8.5) return "B+";
        else if (score < 10.0) return "A";
        else return "A+";
    }
}
