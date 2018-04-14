package com.project.javafx.model;

import java.time.LocalDate;
import java.util.*;

public abstract class Student{

    private long studentID;
    private String firstName;
    private String lastName;

    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String address;

    private EduSystem educationSystem;

    public Student(long studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, EduSystem eduSystem) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.educationSystem = eduSystem;
    }

    // MAIN BEHAVIOR
    public boolean updateProfile() {

        return false;
    }

    abstract protected StudentResult getGradeResult(Course course);

    abstract protected boolean checkAbleToGraduated();

    // GETTER
    public long getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
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

    public String getEducationString() {
        return educationSystem.toString();
    }

    /**
     * Education System distinction
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

    /**
     * Override equals to provide: a comparison of 2 student
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

    @Override
    public String toString() {
        return "Student{" +
                studentID +
                ", " + firstName +lastName +
                '}';
    }
}
