package com.project.javafx.model.repository;

import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.ulti.DateUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

public class StudentRepository extends AbstractRepository<Student> {

    private static StudentRepository instance = new StudentRepository();
    private final String path = "src/main/resources/public/students.json";
    public StudentRepository() {
        super.setFilepath(this.path);
    }

    public void initSomeStudent() {
        AnnualClass aClass = new AnnualClass("Class001");
        CreditMajor major = new CreditMajor("Major003");
        Student student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", "123423432421!", aClass);
        Student student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", "sdfgsdfg234!", aClass);
        Student student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", "123423432421!", major);
        StudentRepository.getInstance().addUnit(student);
        StudentRepository.getInstance().addUnit(student2);
        StudentRepository.getInstance().addUnit(student3);
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
