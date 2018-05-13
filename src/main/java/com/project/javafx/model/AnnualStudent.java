package com.project.javafx.model;

import java.time.LocalDate;
import java.util.List;

import static com.project.javafx.model.StudyLevel.*;

public class AnnualStudent extends Student {

    private StudyLevel studyLevel;
    private double AVG;
    private transient AnnualClass annualClass;

    public AnnualStudent(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address) {
        super(studentID, firstName, lastName, gender, birthday, phone, email, address);
        this.studyLevel = FIRST_YEAR;
    }

    // GETTER AND SETTER

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    // METHOD

    public double getAVG() {
        return AVG;
    }

    @Override
    public StringBuilder checkGraduated() {
        StringBuilder sb;
        sb = super.checkGraduated();
        if (studyLevel!= GRADUATED)
            sb.append("Study level not enough: ").append(studyLevel.toString());
        return sb;
    }

    public void addYearCourseList(List<Course> courses) {
        takenCourses.clear();
        courses.forEach(course -> takenCourses.add(new StudentResult(course)));
    }

    public void addYearCourse(Course courses) {
        takenCourses.add(new StudentResult(courses));
    }

    public void removeYearCourse(Course courses) {
        StudentResult result = getTakenResult(courses);
        takenCourses.remove(result);
    }

    @Override
    boolean isPassResult(StudentResult result) {
        return result.getScore() >= 4.0;
    }

    public boolean passedAllCourseInYear() {
        return takenCourses.stream()
                .allMatch(this::isPassResult);
    }

    private void calculateTotalAVG() {
        double sum = 0;
        int credits = 0;
        for (StudentResult result : passedCourses) {
            double score = result.getScore();
            int credit = result.getCourse().getCredit();
            credits += credit;
            sum += score * credit;
        }
        AVG = Math.round(sum / credits * 10.0) /10.0;
    }

    private double calculateAVG() {
        double sum = 0;
        int credits = 0;
        for (StudentResult result : takenCourses) {
            double score = result.getScore();
            int credit = result.getCourse().getCredit();
            credits += credit;
            sum += score * credit;
        }
        return Math.round(sum / credits * 10.0) /10.0;
    }

    public AnnualClass getAnnualClass() {
        return annualClass;
    }

    public void setAnnualClass(AnnualClass annualClass) {
        this.annualClass = annualClass;
    }

    public void updateStudyYear() {
        super.updateStudyYear();
        updatePassedCourse();
        calculateTotalAVG();
    }

    private void updatePassedCourse() {
        boolean canUpdateLevel = passedAllCourseInYear() && calculateAVG() >= 5.0;
        if (canUpdateLevel) {
            if (studyLevel == StudyLevel.FIRST_YEAR) {
                setStudyLevel(SECOND_YEAR);
            } else if (studyLevel == StudyLevel.SECOND_YEAR) {
                setStudyLevel(THIRD_YEAR);
            } else if (studyLevel == StudyLevel.THIRD_YEAR) {
                setStudyLevel(FOURTH_YEAR);
            } else if (studyLevel == StudyLevel.FOURTH_YEAR) {
                setStudyLevel(GRADUATED);
            }
            passedCourses.addAll(takenCourses);
        }
        addYearCourseList(annualClass.getCoursesCatalog(this.studyLevel));
    }

}
