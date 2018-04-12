package com.project.javafx.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public abstract class Student{

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                '}';
    }

    private long studentID;
    private String firstName;
    private String lastName;

    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String address;

    private EduSystem educationSystem;
    private Set<Course> passedCourses;
    private Set<Course> takenCourses;

    public Student() {
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

    public abstract void addCourse(Course course);

    public abstract void dropCourse(Course course);

    // TODO: 12/04/2018 fix this
    public Grade getGradeResult(Course course) {
//        Set<Student> students = course.getStudentGradeTable().keySet();
//        for (Student student : students) {
//            if (student.equals(this)) {
//                return course.getStudentGradeTable().get(student);
//            }
//        }
        return null;
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
    public static enum EduSystem {
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
}
