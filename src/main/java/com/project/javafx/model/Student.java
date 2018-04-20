package com.project.javafx.model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Student {

    private final long _id;
    private final String firstName;
    private final String lastName;
    private final String gender; // TODO: 19/04/2018 enum this
    private LocalDate birthday;
    protected String phone;
    protected String email;
    protected String address;

    private EduSystem educationSystem;

    public Student(long studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address, EduSystem eduSystem) {
        this._id = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.educationSystem = eduSystem;
    }

    // GETTER AND SETTER
    public long getStudentID() {
        return _id;
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

    @Override
    public String toString() {
        return "Student{" +
                _id +
                ", " + firstName +
                " " + lastName +
                '}';
    }

    // MAIN METHOD
    abstract boolean registerCourse(Course course);

    abstract public StudentResult getScoreResult(Course course);

    abstract public boolean ableToGraduate();

    public void updateStudentProfile(String phone, String email, String address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /**
     * Override equals to provide the comparison of two student
     */
    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Student)) {
            return false;
        }
        Student student = (Student) obj;
        return _id == student._id;
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
}
