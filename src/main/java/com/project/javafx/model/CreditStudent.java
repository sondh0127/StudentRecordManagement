package com.project.javafx.model;

import com.project.javafx.repository.CourseRepository;

import java.time.LocalDate;
import java.util.Set;

public class CreditStudent extends Student implements Registerable {

    private CreditMajor creditMajor;
    private int takenCredits;
    protected Set<String> passedCourses;
    private double GPA;

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
    }

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
        this.creditMajor = creditMajor;
        this.takenCredits = 0;
    }

    // Behavior
    @Override
    public boolean registerCourse(Course course) {
        if (course instanceof CreditCourse) {
            if (!takenCourses.contains(course)) {
                return takenCourses.add(course.getCourseCode());
            }
        }
        return false;
    }

    @Override
    protected StudentResult getGradeResult(Course course) {
        if (course instanceof CreditCourse) {
            return course.getResult(getStudentID());
        }
        return null;
    }

    @Override
    // TODO: 14/04/2018 minor add pls
    protected boolean checkAbleToGraduated() {
        int majorCreditsRequired = getCreditMajor().getMajorCreditsRequired();
        if (majorCreditsRequired == takenCredits || calculateGPA() >= 2.0) {
            return true;
        }
        return false;
    }


    public void updatePassedCourse() {
        for (String courseCode : takenCourses) {
            Course course = CourseRepository.getInstance().findById(courseCode);
            if (course instanceof CreditCourse) {
                StudentResult result = course.getResult(getStudentID());
                if (result.getScoreTranfer() >= 2.0) {
                    passedCourses.add(courseCode);
                    takenCredits += ((CreditCourse) course).getCreditHours();
                }
            } else {
                System.out.println("Debugging!");
            }
        }
    }

    private double calculateGPA() {
        double sum = 0;
        for (String courseCode : passedCourses) {
            Course course = CourseRepository.getInstance().findById(courseCode);
            if (course instanceof CreditCourse) {
                double grade = course.getResult(getStudentID()).getScoreTranfer();
                int credits = ((CreditCourse) course).getCreditHours();
                sum += grade * credits;
            }
        }

        // TODO: 14/04/2018 fix this ... void or double ?
        GPA = sum/takenCredits;
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
