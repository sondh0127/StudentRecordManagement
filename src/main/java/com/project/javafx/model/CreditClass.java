package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditClass {

    private final String classCode;
    private int capacity;
    private CreditCourse course;
    private List<CreditStudent> studentList = new ArrayList<>();

    public CreditClass(String classCode, CreditCourse course, int capacity) {
        this.classCode = classCode;
        this.course = course;
        this.capacity = capacity;
    }
    // flow student register course (from course repo) if course inside the this of major => give a class of this course => add student in too the list class of this course.

    public boolean addStudent(CreditStudent student) {
        if (capacity > studentList.size()) {
            studentList.add(student);
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
        return studentList.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public CreditCourse getCourse() {
        return course;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
