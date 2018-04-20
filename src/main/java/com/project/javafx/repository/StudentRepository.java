package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.ulti.DateUtil;
import com.project.javafx.ulti.gsonUtil.RuntimeTypeAdapterFactory;
import com.project.javafx.ulti.gsonUtil.StudentExclusionStrategy;
import com.project.javafx.ulti.mongoDBUtil.MongoUtils;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class StudentRepository extends AbstractRepository<Student, Long> {
    private static final String path = "src/main/resources/public/Students.json";
    private static StudentRepository instance = new StudentRepository(path);

    public StudentRepository(String filepath) {
        super(Student.class, filepath, MongoUtils.ANUAL_STUDENT_COLL);
    }

    public static StudentRepository getInstance() {
        return instance;
    }

    public void initSomeStudent() {
        AnnualClass aClass = new AnnualClass("C01", "Class001");
        CreditMajor major = new CreditMajor("Major003", "Major003");
        AnnualStudent student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", aClass);
        AnnualStudent student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        AnnualStudent student4 = new AnnualStudent(35463434, "Son3", "Do 23Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        CreditStudent student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", major);
        AnnualStudentRepository.getInstance().save(student);
        AnnualStudentRepository.getInstance().save(student2);
        CreditStudentRepository.getInstance().save(student3);
        AnnualStudentRepository.getInstance().save(student4);
    }

    @Override
    public Set<Student> findAll() {
        Set<Student> students = new HashSet<>();
        students.addAll(CreditStudentRepository.getInstance().findAll());
        students.addAll(AnnualStudentRepository.getInstance().findAll());
        return students;
    }

    @Override
    public Long getID(Student element) {
        return element.getStudentID();
    }

    private RuntimeTypeAdapterFactory<Student> setAdapter() {
        RuntimeTypeAdapterFactory<Student> adapter =
                RuntimeTypeAdapterFactory
                        .of(Student.class, "type")
                        .registerSubtype(CreditStudent.class, CreditStudent.class.getSimpleName())
                        .registerSubtype(AnnualStudent.class, AnnualStudent.class.getSimpleName());
        return adapter;
    }

    @Override
    protected Gson gsonCreator() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(setAdapter())
                .serializeNulls()
                .setExclusionStrategies(new StudentExclusionStrategy())
                .create();
        return gson;
    }

    @Override
    protected Type setToken() {
        return new TypeToken<Set<Student>>() {
        }.getType();
    }


}
