package com.project.javafx.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    Map<Course, StudentResult> takenCourses;

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
        takenCourses = new HashMap<>();
    }

    // GETTER AND SETTER
    public void setTakenCourses(Map<Course, StudentResult> takenCourses) {
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
        return takenCourses.get(course);
    }

    abstract public boolean ableToGraduate();

    public void updateStudentProfile(String phone, String email, String address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public boolean isTakingCourse(Course course) {
        return takenCourses.containsKey(course);
    }

    public boolean updateStudentResult(Course course, double midtermPoint, double finalPoint) {
        if (takenCourses.keySet().contains(course)) {
            takenCourses.put(course, new StudentResult());
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
