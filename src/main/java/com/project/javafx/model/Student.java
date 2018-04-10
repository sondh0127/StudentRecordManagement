package com.project.javafx.model;

import java.time.LocalDate;
import java.util.Set;

public class Student{

    private int studentID;
    private String firstName;
    private String lastName;

    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String address;

//    private final StringProperty educationSystem = new SimpleStringProperty("");
    private Set<Course> takenCourses;

    public Student() {
    }

    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int studentID, String firstName, String lastName, String gender, LocalDate birthday, String phone, String email, String address ) {
        this(studentID, firstName, lastName);
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public void addCourse(Course course) {

    }

    public void dropCourse(Course course) {

    }

    public Grade getGrade(Course course) {
        Set<Student> students = course.getStudentGradeTable().keySet();
        for (Student student : students) {
            if (student.equals(this)) {
                return course.getStudentGradeTable().get(student);
            }
        }
        return null;
    }

    public int getStudentID() {
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

    public Set<Course> getTakenCourses() {
        return takenCourses;
    }

    public void setTakenCourses(Set<Course> takenCourses) {
        this.takenCourses = takenCourses;
    }
}
