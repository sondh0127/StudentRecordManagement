package com.project.javafx.model;

import java.util.HashMap;
import java.util.Map;

public interface Updatable {
    // For Annual Student: takenCourses is the list of course which student took on a appropriate year

    // For Credit Student: takenCourses is the list of course which student has been registered
    Map<String,StudentResult> takenCourses = new HashMap<>();

    boolean updateStudentResult(String courseCode, double midtermPoint, double finalPoint);

//    void updateStudentProfile(String phone, String email, String address);
}
