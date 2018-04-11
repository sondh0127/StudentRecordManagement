package com.project.javafx.repository;

import com.project.javafx.model.*;
import com.project.javafx.ulti.DateUtil;

import java.lang.reflect.Type;

public class StudentRepository extends AbstractRepository<Student> {
    private static StudentRepository instance = new StudentRepository();

    public StudentRepository() {
    }

    public static StudentRepository getInstance() {
        return instance;
    }

    public void initSomeStudent() {
        AnnualClass aClass = new AnnualClass("Class001");
        CreditMajor major = new CreditMajor("Major003");
        Student student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", aClass);
        Student student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        Student student4 = new AnnualStudent(35463434, "Son3", "Do 23Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        Student student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", major);
        StudentRepository.getInstance().addElement(student);
        StudentRepository.getInstance().addElement(student2);
        StudentRepository.getInstance().addElement(student3);
        StudentRepository.getInstance().addElement(student4);
    }

    @Override
    public String converter(Student element) {
        return String.valueOf(element.getStudentID());
    }

    @Override
    public Type setListType() {
        return null;
    }

}
