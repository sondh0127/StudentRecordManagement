package com.project.javafx.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.project.javafx.model.*;
import com.project.javafx.ulti.DateUtil;
import com.project.javafx.ulti.StudentExclusionStrategy;
import com.project.javafx.ulti.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.Set;

public class StudentRepository extends AbstractRepository<Student> {
    private static final String path = "src/main/resources/public/Students.json";
    private static StudentRepository instance = new StudentRepository(path);

    public StudentRepository(String filepath) {
        super(filepath);
    }

    public static StudentRepository getInstance() {
        return instance;
    }

    public void initSomeStudent() {
        AnnualClass aClass = new AnnualClass("C01", "Class001");
        CreditMajor major = new CreditMajor("Major003", "Major003");
        Student student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", aClass);
        Student student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        Student student4 = new AnnualStudent(35463434, "Son3", "Do 23Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        Student student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", major);
        StudentRepository.getInstance().save(student);
        StudentRepository.getInstance().save(student2);
        StudentRepository.getInstance().save(student3);
        StudentRepository.getInstance().save(student4);
    }

    @Override
    public String converter(Student element) {
        return String.valueOf(element.getStudentID());
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

    @Override
    public void gSonLoad() {
        super.gSonLoad();
        // set major and class for student List
        for (Student student : objects) {
            if (student instanceof CreditStudent) {
                String majorCode = ((CreditStudent) student).getCreditMajor().getMajorCode();
                CreditMajor major = CreditMajorRepository.getInstance().findById(majorCode);
                ((CreditStudent) student).setCreditMajor(major);
            } else if (student instanceof AnnualStudent) {
                String classCode = ((AnnualStudent) student).getAnnualClass().getClassCode();
                AnnualClass aClass = AnnualClassRepository.getInstance().findById(classCode);
                ((AnnualStudent) student).setAnnualClass(aClass);
            }
        }
    }
}
