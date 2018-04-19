package com.project.javafx.model;

import com.project.javafx.repository.CourseRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class CreditStudent extends Student implements Registerable {

    private CreditMajor creditMajor;
    private int takenCredits;
    protected Map<String, StudentResult> passedCourses;
    private double GPA;

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
        this.creditMajor = creditMajor;
        this.takenCredits = 0;
    }

    // Behavior
    @Override
    public boolean registerCourse(String courseCode) {
        Course course = CourseRepository.getInstance().findById(courseCode);
        if (course instanceof CreditCourse) {
            if (!takenCourses.containsKey(courseCode)) {
                takenCourses.put(courseCode, new StudentResult(course.getScale()));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateStudentResult(String courseCode, double midtermPoint, double finalPoint) {
        if (midtermPoint > 10 || midtermPoint < 0 || finalPoint > 10 || finalPoint < 0) {
            return false;
        } else if (takenCourses.containsKey(courseCode)){
            takenCourses.put(courseCode, new StudentResult(midtermPoint, finalPoint));
            return true;
        }
        return false;
    }

    @Override
    public StudentResult getGradeResult(String courseCode) {
        return takenCourses.get(courseCode);
    }

    @Override
    // TODO: 14/04/2018 minor add pls
    public boolean checkAbleToGraduated() {
        int majorCreditsRequired = getCreditMajor().getMajorCreditsRequired();
        if (majorCreditsRequired == takenCredits || calculateGPA() >= 2.0) {
            return true;
        }
        return false;
    }


    public void updatePassedCourse() {
        for (String courseCode : takenCourses.keySet()) {
            Course course = CourseRepository.getInstance().findById(courseCode);
            if (course instanceof CreditCourse) {
                StudentResult result = takenCourses.get(courseCode);
                if (result.getScoreTranfer() >= 2.0) {
                    passedCourses.put(courseCode,takenCourses.remove(courseCode));
                    takenCredits += ((CreditCourse) course).getCreditHours();
                }
            } else {
                System.out.println("Debugging!");
            }
        }
    }

    private double calculateGPA() {
//        double sum = 0;
//        for (String courseCode : passedCourses) {
//            Course course = CourseRepository.getInstance().findById(courseCode);
//            if (course instanceof CreditCourse) {
//                double grade = course.getResult(getStudentID()).getScoreTranfer();
//                int credits = ((CreditCourse) course).getCreditHours();
//                sum += grade * credits;
//            }
//        }
//
//        // TODO: 14/04/2018 fix this ... void or double ?
//        GPA = sum/takenCredits;
        return GPA;
    }

    // getter and setter
    public CreditMajor getCreditMajor() {
        return creditMajor;
    }

    public void setCreditMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
    }

    public int getTakenCredits() {
        return takenCredits;
    }

    public double getGPA() {
        return GPA;
    }

    public void registerMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
        this.takenCredits = 0;
    }
}
