package com.project.javafx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String courseCode;
    private final String courseName;

    private final int DEFAULT_CAPACITY = 45;
    private int capacity;
    private int currentEnrollment;

    private List<StudentResult> studentResultList;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = DEFAULT_CAPACITY;
        studentResultList = new ArrayList<>();
    }

    public Course(String courseCode, String courseName, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = capacity;
        studentResultList = new ArrayList<>();
    }

    // main method

    /**
     * Nhập điểm
     */
    public boolean updateStudentResult(Long studentID, double midtermPoint, double finalPoint) {
        if (midtermPoint > 10 || midtermPoint < 0 || finalPoint > 10 || finalPoint < 0) {
            return false;
        }
        for (StudentResult studentResult : studentResultList) {
            if (studentResult.getStudentID().equals(studentID)) {
                studentResult.updateResult(midtermPoint, finalPoint);
            }
        }
        return false;
    }

    public boolean addStudent(Student student) {
        return studentResultList.add(new StudentResult(student.getStudentID()));
    }

    public boolean removeStudent(Student student) {
        for (StudentResult studentResult : studentResultList) {
            if (studentResult.getStudentID() == student.getStudentID()) {
                return studentResultList.remove(studentResult);
            }
        }
        return false;
    }

    public StudentResult getResult(Long studentID) {
        for (StudentResult studentResult : studentResultList) {
            if (studentResult.getStudentID().equals(studentID)) {
                return studentResult;
            }
        }
        return null;
    }
    // getter and setter
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<StudentResult> getStudentResultList() {
        return studentResultList;
    }

    /**
     * Override equals to provide: a comparison of 2 course
     */
    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Course)) {
            return false;
        }
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode);
    }

}
