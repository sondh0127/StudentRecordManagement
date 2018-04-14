package com.project.javafx.model;

import java.util.HashSet;
import java.util.Set;

public interface Registerable {
    Set<String> takenCourses = new HashSet<>();

    boolean registerCourse(Course course);

}
