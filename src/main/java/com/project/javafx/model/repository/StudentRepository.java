package com.project.javafx.model.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.Student;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

public class StudentRepository extends AbstractRepository<Student> {

    private static StudentRepository instance = new StudentRepository();
    private final String path = "src/main/resources/public/students.json";
    public StudentRepository() {
        super.setFilepath(this.path);
    }

    public static StudentRepository getInstance() {
        return instance;
    }

    @Override
    public String converter(Student element) {
        return Integer.toString(element.getStudentID());
    }

    @Override
    public Type setDATA_TYPE() {
        return new TypeToken<Set<Student>>() {
        }.getType();
    }
}
