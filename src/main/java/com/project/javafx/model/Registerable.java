package com.project.javafx.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface Registerable {
    Map<String,StudentResult> takenCourses = new HashMap<>();

    boolean registerCourse(String courseCode);

    boolean updateStudentResult(String courseCode, double midtermPoint, double finalPoint);
}
