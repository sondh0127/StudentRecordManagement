package com.project.javafx.model;

import java.time.LocalDate;
import java.util.*;

public abstract class Student {

    private final long studentID;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private String phone;
    private String email;
    private String address;
    private LocalDate birthday;
    private EduSystem educationSystem;

    List<StudentResult> takenCourses; // can not do that omg

    public Student(long studentID, String firstName, String lastName, Gender gender, LocalDate birthday, String phone, String email, String address, EduSystem eduSystem) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.educationSystem = eduSystem;
        takenCourses = new ArrayList<>();
    }

    // GETTER AND SETTER
    public List<StudentResult> getTakenResult() {
        return takenCourses;
    }

    public void setTakenCourses(List<StudentResult> takenCourses) {
        this.takenCourses = takenCourses;
    }

    public long getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public EduSystem getEducationSystem() {
        return educationSystem;
    }

    @Override
    public String toString() {
        return "Student{" +
                studentID +
                ", " + firstName +
                " " + lastName +
                '}';
    }

    // MAIN METHOD

    public StudentResult getScoreResult(Course course) {
//        return takenCourses.get(course);
        return takenCourses.stream()
                .filter(studentResult -> studentResult.getCourse().equals(course))
                .findFirst()
                .orElse(null);
    }

    abstract public boolean ableToGraduate();

    public void updateStudentProfile(String phone, String email, String address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public boolean isTakingCourse(Course course) {
//        return takenCourses.containsKey(course);
        return takenCourses.stream()
                .anyMatch(studentResult -> studentResult.getCourse().equals(course));
    }

    public boolean updateStudentResult(Course course, double midtermPoint, double finalPoint) {
        if (isTakingCourse(course)) {
            takenCourses.stream()
                    .filter(studentResult -> studentResult.getCourse().equals(course))
                    .findFirst()
                    .ifPresent(result -> result.setPoint(midtermPoint, finalPoint));
            return true;
        }
        return false;
    }

    /**
     * Override equals to provide the comparison of two student
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentID);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Student)) {
            return false;
        }
        Student student = (Student) obj;
        return studentID == student.studentID;
    }

    /**
     * Education System
     */
    public enum EduSystem {
        CREDIT("Academic Credit"),
        ANNUAL("Annual Curriculum");

        private final String text;

        EduSystem(String text) {
            this.text = text;
        }


        @Override
        public String toString() {
            return text;
        }
    }

    public enum Gender {
        MALE("Male"),
        FEMALE("Female");

        private final String text;

        Gender(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
