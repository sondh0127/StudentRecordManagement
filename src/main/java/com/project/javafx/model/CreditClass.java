package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditClass {

    private final String classCode;
    private int capacity;
    private int enrolled;
    private CreditCourse course;
    private List<CreditStudent> studentList = new ArrayList<>();

    public CreditClass(String classCode, CreditCourse course, int capacity) {
        this.classCode = classCode;
        this.course = course;
        this.enrolled = 0;
        this.capacity = capacity;
    }
    // flow student register course (from course repo) if course inside the this of major => give a class of this course => add student in too the list class of this course.

    public boolean addStudent(CreditStudent student) {
        if (!studentList.contains(student) && capacity > enrolled) {
            studentList.add(student);
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean removeStudent(CreditStudent student) {
        return studentList.remove(student);
    }

    // GETTER AND SETTER
    public String getClassCode() {
        return classCode;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(CreditCourse course) {
        this.course = course;
    }

    public List<CreditStudent> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<CreditStudent> studentList) {
        this.studentList = studentList;
    }

    public void clearClassEnrollment() {
        enrolled = 0;
        studentList.clear();
    }

    @Override
    public String toString() {
        return "CreditClass{" +
                "classCode='" + classCode + '\'' +
                ", course=" + course +
                '}';
    }
}
