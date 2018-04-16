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
    private double scale;

    private List<Long> studentList;

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = DEFAULT_CAPACITY;
        studentList = new ArrayList<>();
    }

    public Course(String courseCode, String courseName, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.capacity = capacity;
        studentList = new ArrayList<>();
    }

    // main method

    /**
     * Nhập điểm
     */
//    public boolean updateStudentResult(Long studentID, double midtermPoint, double finalPoint) {
//        if (midtermPoint > 10 || midtermPoint < 0 || finalPoint > 10 || finalPoint < 0) {
//            return false;
//        } else {
//            Student student = StudentRepository.getInstance().findById(String.valueOf(studentID));
//            if (studentResult.getCourseCode().equals(studentID)) {
//                studentResult.updateResult(midtermPoint, finalPoint);
//            }
//        }
//        return false;
//    }

    public boolean addStudent(Long studentID) {
        return studentList.add(studentID);
    }

    public boolean removeStudent(Long studentID) {
        for (Long id : studentList) {
            if (studentID.equals(id)) {
                return studentList.remove(id);
            }
        }
        return false;
    }

//    public StudentResult getResult(Long studentID) {
//        for (StudentResult studentResult : studentList) {
//            if (studentResult.getCourseCode().equals(studentID)) {
//                return studentResult;
//            }
//        }
//        return null;
//    }

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

    public double getScale() {
        return scale;
    }

    public List<Long> getStudentList() {
        return studentList;
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
