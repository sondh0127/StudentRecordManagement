package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;

public class CreditClass {

    private final String classCode;
    private final String className;

    private final int DEFAULT_CAPACITY = 40;
    private int capacity;
    private Course course;
    private List<CreditStudent> studentList = new ArrayList<>();

    public CreditClass(String classCode, String className, Course course) {
        this.classCode = classCode;
        this.className = className;
        this.course = course;
        this.capacity = DEFAULT_CAPACITY;
    }
    private List<CreditClass> classes = new ArrayList<>(); // add new class, drop class:
    // flow student register course (from course repo) if course inside the this of major => give a class of this course => add student in too the list class of this course.

    public void addStudent(CreditStudent student) {
        if (studentList.contains(student)) throw new IllegalArgumentException("Student is exists");
        else studentList.add(student);
    }

    public void removeStudent(CreditStudent student) {
        if (!studentList.contains(student)) throw new IllegalArgumentException("Student is not exists");
        else studentList.remove(student);
    }
}
