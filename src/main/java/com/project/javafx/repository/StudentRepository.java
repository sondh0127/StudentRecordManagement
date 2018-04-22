package com.project.javafx.repository;

import com.project.javafx.model.*;
import com.project.javafx.ulti.DateUtil;
import com.project.javafx.ulti.gsonUtil.RuntimeTypeAdapterFactory;
import com.project.javafx.ulti.mongoDBUtil.MongoDBHandler;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;

public class StudentRepository extends AbstractRepository<Student, Long> {

    private static StudentRepository instance = null;

    private StudentRepository() {
        super(Student.class, MongoDBHandler.STUDENT_COLL);
    }

    public static StudentRepository getInstance() {
        if (instance == null) synchronized (AnnualClassRepository.class) {
            if (instance == null) instance = new StudentRepository();
        }
        return instance;
    }

    public void initSomeStudent() {
        AnnualClass aClass = new AnnualClass("C01", "Class001");
        CreditMajor major = new CreditMajor("Major003", "Major003");
        AnnualStudent student = new AnnualStudent(20145454, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", aClass);
        AnnualStudent student2 = new AnnualStudent(354634, "Son2", "Do 2Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        AnnualStudent student4 = new AnnualStudent(35463434, "Son3", "Do 23Hong", "Male", DateUtil.parse("27/12/1996"), "567567", "245345343@gmail.com", "VietNam2", aClass);
        CreditStudent student3 = new CreditStudent(2131232, "Son", "Do Hong", "Male", DateUtil.parse("27/10/1996"), "34534543534", "243@gmail.com", "VietNam", major);
    }

    @Override
    public Set<Student> getObjectCollection() {
        Set<Student> students = new HashSet<>();
        Document query1 = new Document("educationSystem", "ANNUAL");
        Document query2 = new Document("educationSystem", "CREDIT");
        Set<Student> students1 = getObjectCollection(query1, AnnualStudent.class);
        Set<Student> students2 = getObjectCollection(query2, CreditStudent.class);
        students.addAll(students1);
        students.addAll(students2);
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

//    @Override
//    protected Gson gsonCreator() {
//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .registerTypeAdapterFactory(setAdapter())
//                .serializeNulls()
//                .setExclusionStrategies(new StudentExclusionStrategy())
//                .create();
//        return gson;
//    }

}
