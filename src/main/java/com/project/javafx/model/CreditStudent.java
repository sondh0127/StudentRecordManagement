package com.project.javafx.model;

import com.project.javafx.repository.CourseRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditStudent extends Student implements Updatable {

    private Map<String, StudentResult> passedCourses = new HashMap<>();
    private CreditMajor creditMajor;
    private int registerCreditsLimit;
    private int passedMajorCredits;
    private int passedMinorCredits;
    private int registerCredits;
    private double CPA;
    private double GPA;

    public CreditStudent(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, CreditMajor creditMajor) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address, EduSystem.CREDIT);
        this.creditMajor = creditMajor;
        int DEFAULT_CURRENT_CREDITS_LIMIT = 24;
        this.registerCreditsLimit = DEFAULT_CURRENT_CREDITS_LIMIT;
        this.registerCredits = 0;
        this.passedMajorCredits = 0;
        this.passedMinorCredits = 0;
    }

    // GETTER AND SETTER
    public CreditMajor getCreditMajor() {
        return creditMajor;
    }

    public void setCreditMajor(CreditMajor creditMajor) {
        this.creditMajor = creditMajor;
    }

    public int getPassedMajorCredits() {
        return passedMajorCredits;
    }

    public double getGPA() {
        return GPA;
    }

    public double getCPA() {
        return CPA;
    }


    // Behavior
    @Override
    public boolean registerCourse(Course course) {
        if (course instanceof CreditCourse) {
            if (takenCourses.containsKey(course.getCourseCode()))
                throw new IllegalArgumentException(this.getStudentID() + " already took " + course.getCourseCode() + " course !");
            else {
                int addedCredit = ((CreditCourse) course).getCreditHours();
                List<String> prerequisiteList = ((CreditCourse) course).getPrerequisiteCourse();
                if (registerCredits + addedCredit > registerCreditsLimit) {
                    throw new IllegalArgumentException("Exceed the number of register credit: " + registerCredits + "/" + this.registerCreditsLimit);
                }
                if (prerequisiteList.isEmpty() || passedCourses.keySet().containsAll(prerequisiteList)) {
                    takenCourses.put(course.getCourseCode(), new StudentResult(course.getScale()));
                    registerCredits += addedCredit;
                    course.addStudent(this);
                    return true;
                } else
                    throw new IllegalArgumentException(course.getCourseCode() + " needs Prerequisite Course " + prerequisiteList.toString());
            }
        }
        return false;
    }

    // TODO: 19/04/2018 enough ??
    public void dropCourse(Course course) {
        if (takenCourses.containsKey(course.getCourseCode())) {
            takenCourses.remove(course.getCourseCode());
            registerCredits -= ((CreditCourse) course).getCreditHours();
            course.removeStudent(this);
        }
    }

    @Override
    public StudentResult getScoreResult(Course course) {
        return takenCourses.get(course.getCourseCode());
    }

    @Override
    // TODO: 14/04/2018 minor add pls
    public boolean ableToGraduate() {
        int majorCreditsRequired = creditMajor.getMajorCreditsRequired();
        int minorCreditsRequired = creditMajor.getMinorCreditsRequired();
        if (passedMajorCredits >= majorCreditsRequired
                && passedMinorCredits >= minorCreditsRequired
                && calculateCPA() >= 2.0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStudentResult(String courseCode, double midtermPoint, double finalPoint) {
        if (takenCourses.containsKey(courseCode)) {
            takenCourses.put(courseCode, new StudentResult(midtermPoint, finalPoint));
            return true;
        }
        return false;
    }

    public void updatePassedCourseAll() {
        for (String courseCode : takenCourses.keySet()) {
            Course course = CourseRepository.getInstance().findById(courseCode);
            updatePassedCourse(course);

        }
    }

    private void updatePassedCourse(Course course) {
        String courseCode = course.getCourseCode();
        if (checkPassedCourse(courseCode)) {
            // update passedCredits
            List<CreditCourse> majorCatalog = creditMajor.getMajorCatalog();
            List<CreditCourse> minorCatalog = creditMajor.getMinorCatalog();
            if (!passedCourses.containsKey(courseCode)) {
                if (majorCatalog.contains(course)) {
                    passedMajorCredits += ((CreditCourse) course).getCreditHours();
                } else if (minorCatalog.contains(course)) {
                    passedMinorCredits += ((CreditCourse) course).getCreditHours();
                }
            }
            // if passed course add to list passedCourse
            passedCourses.put(courseCode, takenCourses.remove(courseCode));
        }
    }

    private boolean checkPassedCourse(String courseCode) {
        StudentResult result = takenCourses.get(courseCode);
        return result.getScore() > 3.0 && result.getMidtermPoint() > 3.0 && result.getFinalPoint() > 3.0;
    }

    private double calculateGPA() {
        double sum = getSum(takenCourses);
        return sum / registerCredits;
    }

    public double calculateCPA() {
        updatePassedCourseAll();                                                            // update takenCourses passed
        double sum = getSum(passedCourses);
        return sum / (passedMinorCredits + passedMajorCredits);                             // divide by total passed credits
    }

    private double getSum(Map<String, StudentResult> courseMap) {
        double sum = 0;
        for (String courseCode : courseMap.keySet()) {                                  // for each courses in passedCourses
            Course course = CourseRepository.getInstance().findById(courseCode);            // get object Course
            double grade = this.getScoreResult(course).getScore();
            int credits = ((CreditCourse) course).getCreditHours();
            sum += grade * credits;
        }
        return sum;
    }

//    public void registerMajor(CreditMajor creditMajor) {
//        this.creditMajor = creditMajor;
//        this.passedMajorCredits = 0;
//    }
}
